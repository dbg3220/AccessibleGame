import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public class GameModel {

  //  private String file;
    /** Checks if the next dialogue allows choice input*/
    private boolean choice;
    /** the current choice to display*/
    private int options;

    /** The font size*/
    private static int fontSize = 12;

    /** A TreeMap of all the diaglogueID dialogue*/
    private TreeMap<Integer, ArrayList<String>> lineMap;

    /** A TreeMap with the choiceID as key, the string array of choice options*/
    private TreeMap<Integer, String[]> optionMap;

    /** A map of choices that indicate its the last choice */
    private HashMap<Integer, String> endings;

    /** Current Dialogue to display*/
    private Integer dialogueID;
    /**Current array index for a dialogue*/
    private Integer lineID;

    /** The current speaker*/
    private String speaker;
    /** String to return to textbox_line*/
    private String textbox_line;
    /** Game State end or not*/
    private boolean end;

    /**
     * GameModel() - creates a GameModel Object and reads the
     *
     * @param filename: the file for dialogue
     * @param file2 : the file for choices
     * */
    public GameModel(String filename, String file2) throws IOException{
        try(BufferedReader in = new BufferedReader(new FileReader(filename))){
            String str = "";
            String line = in.readLine();
            String[] fields = line.split("\\s+");
            this.lineMap = new TreeMap<Integer, ArrayList<String>>();
            int count = 0;
            while(in.ready()){
                str = "";
                if(fields.length <= 1){
                    fields = in.readLine().split("\\s+");
                    continue;
                }
                // make an array list
                ArrayList<String> list = new ArrayList<>();
                // loop through to add strings with 100 words or less to list
                for(int i = 1; i < fields.length ; i ++){
                    str = str + fields[i] + " ";
                    if(i % 1000 == 0){
                        list.add(str);
                        str = "";
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
            this.endings = new HashMap<Integer, String> ();

            while(in2.ready()){

                if(f.length <= 1){
                    f = in2.readLine().split("\\s+");
                    continue;
                }
                int num = 0;
                String Op1 = "";
                String Op2 = "";
                String[] optList = new String[2];

                int first = Integer.parseInt(f[0]);
                // loop to assign ID, and strings
                if(f.length == 2){
                    endings.put(Integer.parseInt(f[0]), f[1]);
                    f = in2.readLine().split("\\s+");
                    continue;
                }
                for (int i = 1; i < f.length; i ++){
                    // check which string to append to
                    if(f[i].matches("-?\\d+")){
                        num ++;
                        continue;
                    }
                    if(num == 1){
                        Op1 += f[i] + " ";
                    }else {
                        Op2 += f[i] + " ";
                    }
                }
                optList[0] = Op1;
                optList[1] = Op2;
                if(Op1.length() == 0){
                    String[] lenOne = new String[1];
                    lenOne[0] = Op2;
                    optionMap.put(Integer.parseInt(f[0]),lenOne);
                }else {
                    optionMap.put(Integer.parseInt(f[0]),optList);
                }

                f = in2.readLine().split("\\s+");
                count ++;
             //   System.out.print(Op1 + Op2);
            }
        }
        this.dialogueID = 1;
        this.lineID = 0;
        this.speaker = "";
        this.textbox_line = "";
        this.choice = false;
        this.end = false;
        this.options = 1;
        this.choice = false;
    }

    String getDialogue(int choice) {

        // update the dialogue ID number
        if (choice == 1 || choice == 2) {
            String s = String.valueOf(dialogueID) + String.valueOf(choice);
            dialogueID = Integer.parseInt(s);
            lineID = 0;
         //   setOptions(choice);
        }
        System.out.print(dialogueID);
        if (lineID >= lineMap.get(dialogueID).size()){
            this.choice = true;
            this.lineID = 0;
        }
        ArrayList<String> str = lineMap.get(dialogueID);
        textbox_line = " " +str.get(lineID);
        lineID ++;
        setChoice();

        if(endings.containsKey(dialogueID)){
            setGameState(true);
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

    void setChoice(){
        ArrayList<String> x = lineMap.get(dialogueID);
        int size = x.size();
        if(lineID > size - 1){
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
        if(num == 0){

        }else {
            String str = String.valueOf(options);
            str = str + String.valueOf(num);
            this.options = Integer.parseInt(str);
        }
    }


    String getOption1(){
      //  String str = String.valueOf(options) + "1";
      //  int temp  = Integer.parseInt(str);
        String[] array = this.optionMap.get(options);
        String label = "1." + array[0];


        return label;
    }

    String getOption2(){
      //  String str = String.valueOf(options) + "2";
        //int temp  = Integer.parseInt(str);
        String[] array = this.optionMap.get(options);
        String label ="2."+ array[1];
    //    this.options = Integer.parseInt(str);
        return label;
    }

    public boolean getGameState(){
        return this.end;
    }

    public void setGameState(boolean state){
        this.end = state;
    }

    public void reset(){
        this.dialogueID = 1;
        this.lineID = 0;
        this.speaker = "";
        this.textbox_line = "";
        this.choice = false;
        this.end = false;
        this.options = 1;
        this.choice = false;
    }




}
