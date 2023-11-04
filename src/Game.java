import javafx.application.Application;

public class Game {

    /**
     * Entry point for our accessible game
     * 
     * @param args The files to read in for the dialogue
     */
    public static void main(String[] args) {
        Application.launch(GameGUI.class, args[0], args[1]);
    }
}
