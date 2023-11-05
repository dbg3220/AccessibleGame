import javafx.application.Application;

import java.io.IOException;
import java.util.Scanner;

public class PTUI {

    private GameModel gameModel;

    public PTUI(String a, String b) throws IOException {
        this.gameModel = new GameModel(a, b);
    }

    public void choice1(){
        System.out.print(gameModel.getOption1());
    }

    public void choice2(){
        System.out.print(gameModel.getOption2());
    }

    public void display(int i){
        System.out.print(gameModel.getDialogue(i));
    }

    public void next(){
        boolean x =this.gameModel.getChoice();
        if(!x){
            ///
        }
    }
    /** if choice is true*/
    public boolean choice(){
        return this.gameModel.getChoice();
    }

    public boolean state(){
        return this.gameModel.getGameState();
    }

    public void setNextOP(int i){
        gameModel.setOptions(i);
    }
}
