
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.application.Platform;

public class GameGUI extends Application {

    /** Dimensions of the player's screen */
    private double width, height;

    /** The determined fontsize to increase accessibility */
    private double fontSize;

    /** The buttons for the user to make choices regarding dialogue */
    private Button c1Button, c2Button;

    /** The next button if no choice is required */
    private Button nextButton;

    /** The horizontal box that contains the buttons for making a choice */
    private HBox hboxC;

    /** The horizontal box that contains the next button for no choice */
    private HBox hboxNC;

    /** The border pane that is used throughout the program */
    private BorderPane bp;

    /** The game model that tracks the logic of the game */
    private GameModel gameModel;

    /** The label to communicate the dialogue to the player */
    private Label textBox;

    /** Buttons to control game flow */
    private Button reset, exit;

    /** A speech generator to speak given text aloud for greater accessibility */
    private SpeechGen gen;

    private Image image = new Image("C:\\Users\\jessi\\IdeaProjects\\AccessibleGameF\\Images\\Image.jpg");

    private ComboBox<String> colorBox = new ComboBox<>();
    /**
     * Initialize all fields declared in class. {see above}
     */
    @Override
    public void init() {
        // Grab information on the screen
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        width = bounds.getWidth();
        height = bounds.getHeight();

        fontSize = -1;// checked value

        colorBox.getItems().addAll("Red", "Blue", "Gray", "Green", "White");
        colorBox.setOnAction(e -> changeColor());


        c1Button = new Button();
        c1Button.setText("1");
        c1Button.setOnAction(e -> makeChoice1());
        c1Button.setStyle("-fx-background-color: Green;");
        setShadowEffect(c1Button);
        c1Button.setDisable(true);

        c2Button = new Button();
        c2Button.setText("2");
        c2Button.setOnAction(e -> makeChoice2());
        c2Button.setStyle("-fx-background-color: Red;");
        setShadowEffect(c2Button);
        c2Button.setDisable(true);

        nextButton = new Button();
        nextButton.setText("NEXT");
        nextButton.setOnAction(e -> selectNext());
        nextButton.setStyle("-fx-background-color: Blue;");
        setShadowEffect(nextButton);

        reset = new Button("RESET");
        reset.setOnAction(e -> selectReset());
        setShadowEffect(reset);
        exit = new Button("EXIT");
        exit.setOnAction(e -> Platform.exit());
        setShadowEffect(exit);

        hboxC = new HBox();
        hboxC.getChildren().addAll(reset,colorBox , c1Button, c2Button, exit, nextButton);
        hboxC.setAlignment(Pos.CENTER);
        HBox.setMargin(c1Button, new Insets(0, 50, height * 0.2, 0));
        HBox.setMargin(c2Button, new Insets(0, 50, height * 0.2, 30));

        hboxNC = new HBox();
      //  hboxNC.getChildren().add(nextButton);
        hboxNC.setAlignment(Pos.CENTER);
        HBox.setMargin(nextButton, new Insets(0, 0, height * 0.2, 0));

        bp = new BorderPane();

        // When game model is fully implemented change this line
        List<String> args = getParameters().getRaw();
        try {
            gameModel = new GameModel(args.get(0), args.get(1));
        } catch (IOException e){
            System.out.println("Unable to create GameModel object from files");
            e.printStackTrace();
            System.exit(1);
        }

        textBox = new Label();
        textBox.setWrapText(true);

        gen = new SpeechGen();

    }

    /**
     * Creates the GUI for the game
     */
    @Override
    public void start(Stage primaryStage) {
        // set up starting message
        String message = "Welcome to our accessible game. To start off select a fontsize.\n"
                + "If you don't then we'll use a default one that you might not like!!\n"
                + "(P.S. please keep the size reasonable)";
        Label l = new Label(message);
        l.setFont(new Font(30));
        l.setAlignment(Pos.CENTER);

        // Create field for numbers
        TextField fontInput = new TextField();
        fontInput.setFont(new Font(40));

        GridPane gp = new GridPane();
        gp.add(l, 0, 0);
        gp.add(fontInput, 0, 1);
        gp.setAlignment(Pos.CENTER);
        bp.setCenter(gp);

        // create start button
        Button startButton = new Button("START");
        startButton.setOnAction(e -> {
            try{
                String text = fontInput.getText();
                if (text.length() > 0) {
                    changeAllFont(Integer.parseInt(text));
                }
            } catch (Exception x){}
            gameStart();
            textBox.setText(gameModel.getDialogue(0));
        });
        startButton.setMinWidth(width * 0.1);
        startButton.setMaxWidth(width * 0.1);
        startButton.setMinHeight(height * 0.1);
        startButton.setMaxHeight(height * 0.1);
        startButton.setFont(new Font(height / 40));// a good default font size
        startButton.setStyle("-fx-background-color: Blue; -fx-color: Black");

        // place start button
        HBox startButtonBox = new HBox(startButton);
        HBox.setMargin(startButton, new Insets(0, 0, height * 0.2, 0));
        startButtonBox.setAlignment(Pos.CENTER);
        bp.setBottom(startButtonBox);

        bp.setLeft(reset);
        bp.setRight(exit);
        BorderPane.setMargin(reset, new Insets(height * 0.01, 0, 0, width * 0.01));
        BorderPane.setMargin(exit, new Insets(height * 0.01, width * 0.01, 0, 0));

        // setup scene
        primaryStage.setMinWidth(0.33 * width);
        primaryStage.setMinHeight(0.33 * height);
        primaryStage.setScene(new Scene(bp));
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("DIALOGUE BONANZA");
        primaryStage.show();

        gen.speak(message + "Select blue to start the game");
    }

    /**
     * Cleans up all assets that must be closed
     */
    @Override
    public void stop() throws Exception {
        gen.close();
    }

    ///
    /// ACTION METHODS
    ///

    /**
     * Starts the dialogue in the game
     * 
     * @param fontInput A text field that might contain a font size
     */
    private void gameStart() {
        if (fontSize == -1) {
            changeAllFont(height / 50);
        }
        // get dialogue from the game
        // determine if the dialogue requests a choice
        // build the screen accordingly

        // for now assume there is a choice
        bp.setCenter(textBox);
        bp.setBottom(hboxC);
        ImageView imageView = new ImageView(image);
        textBox.setGraphic(imageView);
    }

    /**
     * Selects the first choice
     */
    private void makeChoice1() {
        if(gameModel.getGameState() == true){
            c1Button.setDisable(true);
            c2Button.setDisable(true);
        }
        if(gameModel.getChoice() == true){
            String str = gameModel.getDialogue(1);
            this.gen.speak(str);
            textBox.setText(str);
            gameModel.setOptions(1);
            c2Button.setText(gameModel.getOption1());

        }
        if(gameModel.getChoice() == false){
            c1Button.setDisable(true);
            c2Button.setDisable(true);
            nextButton.setDisable(false);
        }
    }

    /**
     * Selects the second choice
     */
    private void makeChoice2() {
        // TODO
        if(gameModel.getGameState() == true){
            c1Button.setDisable(true);
            c2Button.setDisable(true);
        }
        if(gameModel.getChoice() == true){
            String str = gameModel.getDialogue(2);
            this.gen.speak(str);
            textBox.setText(str);
            gameModel.setOptions(2);
            c2Button.setText(gameModel.getOption2());


        }
        if(gameModel.getChoice() == false){
            c1Button.setDisable(true);
            c2Button.setDisable(true);
            nextButton.setDisable(false);
        }
    }

    /**
     * Selects the next option
     */
    private void selectNext() {
        // TODO
        if(gameModel.getChoice() == false){
            String str = gameModel.getDialogue(0);
            textBox.setText(str);

        }
        if(gameModel.getChoice() == true){
            nextButton.setDisable(true);
            c1Button.setText(gameModel.getOption1());
            c2Button.setText(gameModel.getOption2());
            c1Button.setDisable(false);
            c2Button.setDisable(false);
        }
        gen.speak(textBox.getText());
    }

    /**
     * Action to reset the game when the reset button is selected
     */
    private void selectReset(){
        System.out.println("NEED TO IMPLEMENT!!!");
        gameModel.reset();
        textBox.setText("");
        c1Button.setDisable(true);
        c2Button.setDisable(true);
        nextButton.setDisable(false);
    }

    ///
    ///
    /**
     * select the background color
     */
    private void changeColor(){
        String selected = colorBox.getValue();
        //"Red", "Blue", "Gray", "Green"
        javafx.scene.paint.Color color = javafx.scene.paint.Color.WHITE;
        if(selected == "White" ){
            color = javafx.scene.paint.Color.WHITE;
        } else if (selected == "Blue") {
            color = javafx.scene.paint.Color.BLUE;
        } else if (selected == "Gray") {
            color = javafx.scene.paint.Color.GRAY;
        } else if (selected =="Green") {
            color = Color.GREEN;
        } else if (selected == "Red") {
            color = Color.RED;
        }
        BackgroundFill backgroundFill = new BackgroundFill(color, null, null);
        Background background = new Background(backgroundFill);
        bp.setBackground(background);


    }

    ///

    /**
     * Takes a value as the new font size and resets all text components to that
     * size
     * 
     * @param value The size of the new font as an integer
     */
    private void changeAllFont(double value) {
        fontSize = value;
        Font thisFont = new Font(fontSize);
        c1Button.setFont(thisFont);
        c2Button.setFont(thisFont);
        nextButton.setFont(thisFont);
        textBox.setFont(thisFont);
        reset.setFont(thisFont);
        exit.setFont(thisFont);
    }

    /**
     * Sets a shadow effect on the button its given
     * @param button The button to change
     */
    private void setShadowEffect(Button button){
        DropShadow d = new DropShadow();
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, 
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e){
                    button.setEffect(new DropShadow());
                }
            }
        );
        button.addEventHandler(MouseEvent.MOUSE_EXITED, 
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e){
                    button.setEffect(null);
                }
            }
        );
    }
}
