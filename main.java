import java.awt.*;
import java.io.IOException;

public class main {
//rename first place bug
    public static String currVer = "0.1";
    public static int targetX;
    public static int targetY;

    public static void main(String[] args) throws IOException {

        checkRes("welcomingWindow");
     //   database.addItem("stockholmas2","Sticker | Gambit Gaming | Stockholm 2021",2376,0.4);

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                new welcomingScreen();
              //  new mainScreen();
            }
        });
    }

    static void checkRes(String a){
        if(a=="welcomingWindow"){
            Dimension resD = Toolkit.getDefaultToolkit().getScreenSize();
            targetX = (int)((resD.getWidth()-welcomingScreen.welcWindowY)/2);
            targetY = (int)((resD.getHeight()-welcomingScreen.welcWindowX)/2);
        }else{}
    }
}
