import java.io.IOException;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) throws IOException {
        PTUI p = new PTUI(args[0], args[1]);
      //  p.display(0);
        int i = 0;
        Scanner in = new Scanner( System.in );
        while (true ){
            while (p.choice() == false){
                String x = in.nextLine();
                p.display(0);

            }
            System.out.print("\n");
            if(p.state() == true){
                System.out.print("End Game");
                break;
            }
            p.choice1();
            System.out.print("\n");
            p.choice2();
            String line = in.nextLine();
            System.out.print("\n");
            i = Integer.parseInt(line);
            if(i == 1){
                p.display(1);
                p.setNextOP(1);
            } else if (i == 2) {
                p.display(2);
                p.setNextOP(2);
            }else {
                break;
            }
        }

    }


}
