import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public class GameModel {

  //  private String file;
    private boolean choice;

    private int options;

    private static int fontSize = 12;
    private TreeMap<Integer, ArrayList<String>> lineMap;

    private TreeMap<Integer, String[]> optionMap;

    private HashMap<Integer, String> endings;

    private Integer dialogueID;

    private Integer lineID;

    private String speaker;

    private String textbox_line;

    private boolean end;
    public GameModel(String filename, String file2) throws IOException{
        try(BufferedReader in = new BufferedReader(new FileReader(filename))){
            String str = "";
            String line = in.readLine();
            String[] fields = line.split("\\s+");
            this.lineMap = new TreeMap<Integer, ArrayList<String>>();
            int count = 0;
            while(in.ready()){
                if(count % 2 == 1){
                    in.readLine();
                    continue;
                }
                // make an array list
                ArrayList<String> list = new ArrayList<>();
                // loop through to add strings with 100 words or less to list
                for(int i = 1; i < fields.length ; i ++){
                    str = str + fields[i] + " ";
                    if(i % 100 == 0){
                        list.add(str);
                    }
                }
                // add the last string
                list.add(str);
                // add this dialogue to lineMap
                lineMap.put(Integer.parseInt(fields[0]),list);
                fields = in.readLine().split("\\s+");
                count ++;

            }

        }
        try(BufferedReader in2 = new BufferedReader(new FileReader(file2))){
            // set up tree map
            this.optionMap = new TreeMap<Integer, String[]>();
            //split line
            String[] f = in2.readLine().split("\\s+");
            int count = 0;
            int line_count = 0;
            while(in2.ready()){
                if(count%2 == 1){
                    in2.readLine();
                    continue;
                }
                int num = 0;
                String Op1 = "";
                String Op2 = "";
                String[] optList = new String[2];

                int first = Integer.parseInt(f[0]);
                // loop to assign ID, and strings
                if(optionMap.containsKey(first)){
                    endings.put(Integer.parseInt(f[0]), f[1]);
                    continue;
                }
                for (int i = 1; i < f.length; i ++){
                    // check which string to append to
                    if(f[i].matches("-?\\d+")){
                        num ++;
                    }
                    if(num == 1){
                        Op1 += f[i] + " ";
                    }else {
                        Op2 += f[i] + " ";
                    }
                }
                optList[0] = Op1;
                optList[1] = Op2;
                optionMap.put(Integer.parseInt(f[0]),optList);
                f = in2.readLine().split("\\s+");
                count ++;
                line_count++;
                System.out.print(Op1 + Op2);
            }
        }
        this.dialogueID = 0;
        this.lineID = 0;
        this.speaker = "";
        this.textbox_line = "";
        this.choice = false;
        this.end = false;
    }

    String setDialogue(int choice) {
        String current[];
        // update the dialogue ID number
        if (choice == 1 || choice == 2) {
            String s = String.valueOf(dialogueID);
            s = s + String.valueOf(choice);
            dialogueID = Integer.parseInt(s);
            lineID = 0;
            setOptions(choice);
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
        //add the next line
        lineID ++;
        setChoice();
        // return the string line
        return textbox_line;
    }




    void setSpeaker(String name){
        speaker = name;
    }

    String getSpeaker(){
        return speaker;
    }

    void setChoice(){
        if(lineID > lineMap.get(dialogueID).size()){
            this.choice = true;
        }else {
            this.choice = false;
        }
        if(endings.containsKey(options)){
            this.end = true;
        };

    }

    boolean getChoice(){
        return choice;
    }

    void setFontSize(String str){
        fontSize = Integer.parseInt(str);
    }

    Integer getFontSize(){
        return fontSize;
    }

    void setOptions(int num){
        if(this.options == 0){
            this.options = num;
        }else {
            String str = String.valueOf(options);
            str = str + String.valueOf(num);
            this.options = Integer.parseInt(str);
        }
    }

    boolean isEnd(){
        return this.end;
    }

    String getOption1(){
        return this.optionMap.get(options)[0];
    }

    String getOption2(){
        return this.optionMap.get(options)[1];
    }






}
