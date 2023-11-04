import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class GameModel {

    private String file;

    private TreeMap<Integer, ArrayList<String>> lineMap;

    private HashMap<String, Integer> characters;

    private Integer dialogueID;

    private Integer lineID;

    private String speaker;

    private String textbox_line;
    public GameModel(String filename) throws IOException{
        try(BufferedReader in = new BufferedReader(new FileReader(filename))){
            String line = in.readLine();
            String[] fields = line.split("\\s+");
            while(in.ready()){
                for(int i = 1; i < fields.length ; i ++){

                }
             //   lineMap.put(Integer.parseInt(fields[0]), )
            }

        }
    }

    String getDialogue(int choice) {
        String current[];
        // update the dialogue ID number
        if (choice == 1 || choice == 2) {
            String s = String.valueOf(dialogueID);
            s = s + String.valueOf(choice);
            dialogueID = Integer.parseInt(s);
            lineID = 0;
        }
        // split the current line of dialogue
        current = lineMap.get(dialogueID).get(lineID).split(" ");
        // set the character name
        setSpeaker(current[0]);
        textbox_line = "";
        // loop through to make one string of dialogue
        for (int i = 1; i < current.length; i ++){
            textbox_line += current[i] + " ";
        }
        // return the string line
        return textbox_line;

    }


    void setSpeaker(String name){
        speaker = name;
    }

    String getSpeaker(){
        return speaker;
    }
}
