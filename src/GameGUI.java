
import java.awt.Font;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Screen;

public class GameGUI extends Application {

    /**
     * Creates the GUI for the game
     */
    public void start(Stage primaryStage) {
        Label l = new Label("Welcome to our accessible game");
        // l.setFont(new Font(30.0));
        HBox hbox = new HBox();
        hbox.getChildren().add(l);
        hbox.setAlignment(Pos.CENTER);
        BorderPane bp = new BorderPane();
        bp.setTop(hbox);
        primaryStage.setTitle("Accessible Game");

        // setup scene
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double width = bounds.getWidth();
        double height = bounds.getHeight();
        primaryStage.setScene(new Scene(bp, width, height));
        primaryStage.show();
    }
}
