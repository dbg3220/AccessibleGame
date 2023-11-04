import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class GameModel {

  //  private String file;

    private TreeMap<Integer, ArrayList<String>> lineMap;

    private HashMap<String, Integer> characters;

    private Integer dialogueID;

    private Integer lineID;

    private String speaker;

    private String textbox_line;
    public GameModel(String filename) throws IOException{
        try(BufferedReader in = new BufferedReader(new FileReader(filename))){
            String str = "";
            String line = in.readLine();
            String[] fields = line.split("\\s+");
            this.lineMap = new TreeMap<Integer, ArrayList<String>>();
            this.characters = new HashMap<String, Integer>();
            int char_count = 0;
            while(in.ready()){
                // make an array list
                ArrayList<String> list = new ArrayList<>();
                // loop through to add strings with 100 words or less to list
                for(int i = 1; i < fields.length ; i ++){
                    str = str + fields[i] + " ";
                    if(i % 100 == 0){
                        list.add(str);
                    }
                }
                if(!characters.containsKey(fields[1])){
                    char_count ++;
                    characters.put(fields[1],char_count);
                }
                // add the last string
                list.add(str);
                // add this dialogue to lineMap
                lineMap.put(Integer.parseInt(fields[0]),list);
            }

        }
        this.dialogueID = 0;
        this.lineID = 0;
        this.speaker = "";
        this.textbox_line = "";
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
