import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.EventListener;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.Timer;

public class welcomingScreen extends JFrame {
    // resolution
    public static int welcWindowX = 450;
    public static int welcWindowY = 150;
    // inputs/selection
    public static String selectedInvestment = "";
    public static String userInput = "";
    public static boolean darkmode = true;
    String[] invString = new String[countInvestments(1)];
    boolean wasClickedRemove = false;
    boolean ended = false;
    // declarations
    // text
    static JLabel upperText;
    static JLabel upToDateText;
    // panels
    JPanel top = new JPanel(new FlowLayout());
    JPanel center = new JPanel(new FlowLayout());
    JPanel bottom = new JPanel(new FlowLayout());
    JPanel topSpacerLeft = new JPanel(new FlowLayout());
    JPanel topSpacerLRight = new JPanel(new FlowLayout());
    // buttons
    JButton add = new JButton("Add");
    public static JButton remove = new JButton("Remove");
    JButton start = new JButton("Start");
    JButton rename = new JButton("Rename");
    JButton options = new JButton("Light");

    JComboBox invList;
    public welcomingScreen(){
        ////MAIN CONFIGURATION////
        configureWindow();
        configureElements();
        checkCurrVersion();
        setColors();
        buildElements();
        buttonActionListeners();
        countInvestments(2);

        ////ADD BUTTON CONFIGURATION////
        invList = new JComboBox(invString);
        selectedInvestment = invString[0];
        center.add(invList);
        invList.setBackground(Color.gray);
        invList.setForeground(Color.black);

        // checks for selected item
        invList.addActionListener(event -> {selectedInvestment = (String)invList.getSelectedItem();});
        JTextField addName = new JTextField("enter name",10);

        // waits for focus in the text area
        addName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                addName.setText("");
            }@Override public void focusLost(FocusEvent e) {}});

        // add event to add button that enables text field
        add.addActionListener(event -> {
            center.remove(invList);
            center.add(addName);
            // resets window
            repaint();
            revalidate();
        });
        Action detectEnter = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInput = addName.getText();
                try {
                    database.create(userInput);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                userInput = "";
                addName.setText("enter name");
                invList.removeAllItems();
                invString = null;
                invString = new String[countInvestments(1)];
                countInvestments(2);
                invList.setModel(new DefaultComboBoxModel(invString));

                center.remove(addName);
                center.add(invList);
                revalidate();
                repaint();
            }
        };
        addName.addActionListener(detectEnter);
    }

    void checkCurrVersion(){
        boolean isNewest = false;
        String newestVersion = "0.1";

        if(main.currVer == newestVersion){
            isNewest = true;
        }

        if(isNewest) {
            upToDateText.setText("Up to date");
            upToDateText.setForeground(Color.GREEN);
        }else if(!isNewest){
            upToDateText.setText("<html>Out of date<br>run updater</html>");
            upToDateText.setForeground(Color.RED);
            upToDateText.setFont(new Font("Serif",Font.PLAIN,12));
        }


    }

    void configureElements(){
        if(darkmode){
            options.setText("Light");
        }else{
            options.setText("Dark");
        }

        // top text
        upperText = new JLabel();
        upperText.setText("Choose your investment");
        upperText.setFont(new Font("Serif",Font.PLAIN,25));

        upToDateText = new JLabel();
        upToDateText.setFont(new Font("Serif",Font.PLAIN,15));
        remove.setPreferredSize(new Dimension(81,32)); // 57 base
        start.setPreferredSize(new Dimension(81,25));
        rename.setPreferredSize(new Dimension(81,25)); // to fix bounds
        options.setPreferredSize(new Dimension(81,25));
        add.setPreferredSize(new Dimension(81,32));

        topSpacerLRight.setPreferredSize(new Dimension(70,0));
        topSpacerLeft.setPreferredSize(new Dimension(7,0));
    }

    void buttonActionListeners(){
        remove.addActionListener(event -> {
            Timer timer = new Timer();

            if(wasClickedRemove){
                ended = true;
                wasClickedRemove = false;
                System.out.println("Removed!");
                database.remove((String)invList.getSelectedItem());

                invList.removeAllItems();
                invString = null;
                invString = new String[countInvestments(1)];
                countInvestments(2);
                invList.setModel(new DefaultComboBoxModel(invString));
                repaint();
                revalidate();
              //  database.remove(welcomingScreen);

            }else if(!wasClickedRemove){
                timer.scheduleAtFixedRate(new TimerTask() {
                    int seconds = 5;
                    @Override
                    public void run() {
                        remove.setText("<html>"+"Click<br>again("+seconds+")"+"</html>");
                        seconds--;
                        if(seconds<-1 | ended){
                            timer.cancel();
                            timer.purge();
                            remove.setText("Remove");
                            wasClickedRemove = false;
                            ended = false;
                        }

                    }
                },0,1000);
                wasClickedRemove = true;
            }


        });

        start.addActionListener(event -> {

        });
        ////////////////////////////////////
        JTextField renameField = new JTextField("enter name",10);
        renameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                renameField.setText("");
            }@Override public void focusLost(FocusEvent e) {}});

        rename.addActionListener(event -> {
            center.remove(invList);
            center.add(renameField);
            // resets window
            repaint();
            revalidate();
        });

        Action detectEnter = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInput = renameField.getText();

                //
                database.renameInvestment(selectedInvestment,userInput);
                //

                userInput = "";
                renameField.setText("enter name");
                invList.removeAllItems();
                invString = null;
                invString = new String[countInvestments(1)];
                countInvestments(2);
                invList.setModel(new DefaultComboBoxModel(invString));

                center.remove(renameField);
                center.add(invList);
                revalidate();
                repaint();
                System.out.println("Renamed");
            }
        };
        renameField.addActionListener(detectEnter);

        //////////////////////////////////
        options.addActionListener(event -> {
            if(darkmode){darkmode = false;}else{darkmode = true;}
            welcomingScreen main = new welcomingScreen();
            main.setVisible(true);
            this.dispose();
        });
    }

    void buildElements(){
        // panels
        add(top,BorderLayout.PAGE_START);
        add(center,BorderLayout.CENTER);
        add(bottom,BorderLayout.PAGE_END);
        // buttons
        bottom.add(rename);
        bottom.add(add);
        bottom.add(start);
        bottom.add(remove);
        bottom.add(options);
        // text
        top.add(upToDateText);
        top.add(topSpacerLeft);
        top.add(upperText);
        top.add(topSpacerLRight);

    }

    void setColors(){
        Color mainBackground = new Color(0);Color topPanelColor = new Color(0);
        Color centerPanelColor = new Color(0);Color bottomPanelColor = new Color(0);
        Color upperTextColor = new Color(0);Color buttonColor = new Color(0);
        Color startButtonColor = new Color(0);Color buttonTextColor = new Color(0);
        if(darkmode){
            mainBackground = new Color(0);
            topPanelColor = new Color(64, 64, 64);
            centerPanelColor = new Color(64, 64, 64);
            bottomPanelColor = new Color(64, 64, 64);
            upperTextColor = new Color(230,230,230);
            buttonColor = new Color(128,128,128);
            startButtonColor = new Color(94, 94, 94);
            buttonTextColor = new Color(255,255,255);
        }else if(!darkmode){
            mainBackground = new Color(0);
            topPanelColor = new Color(239, 245, 252);
            centerPanelColor = new Color(239, 245, 252);
            bottomPanelColor = new Color(239, 245, 252);
            upperTextColor = new Color(0x000000);
            buttonColor = new Color(0x9494FA);
            startButtonColor = new Color(0x5252FF);
            buttonTextColor = new Color(0);
        }

        add.setBackground(buttonColor);
        add.setForeground(buttonTextColor);
        remove.setBackground(buttonColor);
        remove.setForeground(buttonTextColor);
        start.setBackground(startButtonColor);
        start.setForeground(buttonTextColor);
        rename.setBackground(buttonColor);
        rename.setForeground(buttonTextColor);
        options.setBackground(buttonColor);
        options.setForeground(buttonTextColor);
        // panels
        top.setBackground(topPanelColor);
        center.setBackground(centerPanelColor);
        bottom.setBackground(bottomPanelColor);
        // text
        upperText.setForeground(upperTextColor);

    }

    void configureWindow(){
        setTitle("Steam Investment Helper "+main.currVer);
        setSize(welcWindowX,welcWindowY);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());
        setLocation(main.targetX,main.targetY);
    }



    int countInvestments(int pass){
        String cwd = System.getProperty("user.dir");
        //Correct it to read curr folder by default
        File file = new File(cwd+"/src/Investments");
        File[] files = file.listFiles(new FileFilter(){
            @Override
            public boolean accept(File f){
                return f.isDirectory();
            }
        });

        if(pass == 2){
            for(int i = 0 ; i < files.length ; i++){
                invString[i] = files[i].getName();
                // add " - 9532 items"
                // acomplish with second array that with parse that data with items when
                // needed
            }
        }
        return files.length;
    }
}

