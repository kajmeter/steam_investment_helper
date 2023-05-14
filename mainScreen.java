import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class mainScreen extends JFrame {

    public String workingInv = "Stickers";
    int longestWord;



    boolean refreshed = false;

    boolean devMode = true;

    JPanel primaryTOP = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel primaryBOTTOM = new JPanel(new BorderLayout());
    JPanel topLeft = new JPanel(new GridLayout(1,0));
    JPanel topRight = new JPanel(new GridLayout(6,0));
    JPanel topRightOne = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel topRightTwo = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel topRightThree = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel topRightFour = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel topRightFive = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel topRightSix = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel spacerone = new JPanel(new FlowLayout());
    JPanel spacertwo = new JPanel(new FlowLayout());
    JPanel spacerthree = new JPanel(new FlowLayout());
    String[] invString;
    String [] modInvString;

    //Right side
    JButton addButton = new JButton("ADD");
    JTextField name = new JTextField("enter name",17);
    JTextField bPrice = new JTextField("buy price",5);
    JTextField quan = new JTextField("quantity",5);
    JTextField bpriceField = new JTextField("buy price",6);
    JTextField quantityField = new JTextField("quantity",6);
    static JLabel addText;
    static JLabel optionsText;
    static JLabel loading;

    //Buttons
    JButton removeButton = new JButton("Remove");
    JButton bpriceButton = new JButton("Change");
    JButton quantityButton = new JButton("Change");
    JButton refreshButton = new JButton("REFRESH PRICES");
    JButton settingsButton = new JButton("Settings");

    //Dataset models
    DefaultListModel listData = new DefaultListModel();
    DefaultListModel listDataRaw = new DefaultListModel();
    JList list;

    //Colors out of class
    Color sixfour;
    Color darkWhite;
    Color darkerSixfour;
    Color buttonColor;


    public mainScreen(){
        //basic window configuration
        configureWindow();
        //panels positioning
        setPanels();
        //fill list with Data used for dataset
        fillListData();
        //basic list configuration
        configureList();
        //full configuration of add section with listeners
        configureAddItem();
        //set colors
        setColors();
        //buttons ui configuration
        configureButtons();
        //sets listeners for settings and refresh button
        refresh_settings_listeners();
        //configure change section /w remove button
        configure_change_section();

        outOfUseArea();

        topLeft.add(list);

    }

//change_section
    void configure_change_section(){

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TO_DO//
                try {
                    database.removeItem(workingInv,String.valueOf(list.getSelectedIndex()));


                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println(list.getSelectedIndex());
            }
        });

    }

//BUTTON ACTION LISTENERS
    void refresh_settings_listeners(){
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculatePrices.start();
            }
        });
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsButton.setText("WIP");
            }
        });

    }

    void configureButtons(){

        optionsText = new JLabel();
        optionsText.setText("          CHANGE          ");
        optionsText.setFont(new Font("Seriff",Font.PLAIN,22));
        optionsText.setForeground(darkWhite);
        refreshButton.setPreferredSize(new Dimension(150,52));
        refreshButton.setBackground(new Color(33, 33, 128));
        refreshButton.setForeground(Color.white);

        removeButton.setPreferredSize(new Dimension(150,35));
        removeButton.setBackground(new Color(128, 33, 33));
        removeButton.setForeground(Color.white);

        settingsButton.setPreferredSize(new Dimension(150,35));
        settingsButton.setBackground(new Color(128, 106, 33));
        settingsButton.setForeground(Color.white);

        bpriceButton.setForeground(new Color(230,230,230));
        quantityButton.setForeground(new Color(230,230,230));
        bpriceButton.setBackground(new Color(128,128,128));
        quantityButton.setBackground(new Color(128,128,128));
        bpriceField.setBackground(new Color(128,128,128));
        quantityField.setBackground(new Color(128,128,128));
        bpriceField.setForeground(new Color(230,230,230));
        quantityField.setForeground(new Color(230,230,230));

        bpriceButton.setPreferredSize(new Dimension(80,20));
        quantityButton.setPreferredSize(new Dimension(80,20));

        spacertwo.setBackground(new Color(30, 30, 30));
        spacertwo.setPreferredSize(new Dimension(270,4));

        spacerthree.setBackground(new Color(30, 30, 30));
        spacerthree.setPreferredSize(new Dimension(270,4));

        settingsButton.setHorizontalAlignment(JLabel.CENTER);

        topRightSix.add(settingsButton);
        topRightThree.add(optionsText);
        topRightThree.add(bpriceField);
        topRightThree.add(quantityField);

        topRightFour.add(bpriceButton);
        topRightFour.add(quantityButton);

        topRightFour.add(removeButton);
        topRightFive.add(spacerthree);
        topRightFive.add(refreshButton);
       // topRightFive.add(spacertwo);


    }

    void outOfUseArea(){
        primaryBOTTOM.setBackground(Color.BLACK);
        JLabel outOfUse_label = new JLabel("Work in progress");

        outOfUse_label.setHorizontalAlignment(JLabel.CENTER);
        outOfUse_label.setVerticalAlignment(JLabel.CENTER);
        outOfUse_label.setForeground(Color.WHITE);

        primaryBOTTOM.add(outOfUse_label,BorderLayout.CENTER);
    }

    void configureAddItem(){
        //add text
        addText = new JLabel();
        addText.setText("ADD");
        addText.setFont(new Font("Seriff",Font.PLAIN,22));
        topRightOne.add(addText);
        topRightOne.add(name);
        topRightTwo.add(bPrice);
        topRightTwo.add(quan);
        spacerone.setBackground(new Color(30, 30, 30));
        spacerone.setPreferredSize(new Dimension(270,4));
        addButton.setPreferredSize(new Dimension(150,30));
        topRightTwo.add(addButton);
        topRightThree.add(spacerone);

        String nameFetchListener = name.getText();
        name.addFocusListener(new FocusListener() {@Override
            public void focusGained(FocusEvent e) {if(name.getText().equals(nameFetchListener)){name.setText("");}}@Override public void focusLost(FocusEvent e) {}});

        String bPriceFetchListener = bPrice.getText();
        bPrice.addFocusListener(new FocusListener() {@Override
            public void focusGained(FocusEvent e) {if (bPrice.getText().equals(bPriceFetchListener)) {bPrice.setText("");}}@Override public void focusLost(FocusEvent e) {}});

        String quanFetchListener = quan.getText();

        quan.addFocusListener(new FocusListener() {@Override
            public void focusGained(FocusEvent e) {if(quan.getText().equals(quanFetchListener)){quan.setText("");}}@Override public void focusLost(FocusEvent e) {}});



    }

    void setColors(){
        sixfour = new Color(64,64,64);
        darkerSixfour = new Color(30, 30, 30);
        darkWhite = new Color(230,230,230);
        buttonColor = new Color(128,128,128);

        list.setBackground(sixfour);
        list.setForeground(darkWhite);

        topRightOne.setBackground(sixfour);
        topRightTwo.setBackground(sixfour);
        topRightThree.setBackground(sixfour);
        topRightFour.setBackground(sixfour);
        topRightFive.setBackground(sixfour);
        topRightSix.setBackground(sixfour);
        addText.setForeground(darkWhite);

        primaryTOP.setBackground(darkerSixfour);

        topLeft.setBackground(sixfour);
        topRight.setBackground(sixfour);

        primaryBOTTOM.setBackground(darkerSixfour);
        addButton.setBackground(buttonColor);
        addButton.setForeground(darkWhite);

        name.setBackground(buttonColor);
        bPrice.setBackground(buttonColor);
        quan.setBackground(buttonColor);

        name.setForeground(darkWhite);
        bPrice.setForeground(darkWhite);
        quan.setForeground(darkWhite);



    }

    void configureList(){
        list = new JList(listData);
        list.setPreferredSize(new Dimension(320,400));
        list.setFont(new Font("Seriff",Font.PLAIN,15));
    }

    void fillListData(){
        String content = getContent();
        listData.clear();
        listDataRaw.clear();
        int latestID = 0;try{latestID = database.latestId(workingInv,false);}catch (IOException a){}

        String tmpVal;
        String olP;
        String newP;

        for(int i = 1;i<latestID;i++){
            try{
                tmpVal = tools.findBetween(content,
                        "^ID^"+Integer.toString(i)+"^NAME^",
                        "^NAME^END^ID^"+Integer.toString(i)+"^");
                listDataRaw.addElement(tmpVal);
                tmpVal = tmpVal.replaceAll("%20"," ");
                tmpVal = tmpVal.replaceAll("%7","");
                tmpVal = tmpVal.replaceAll(" C","");
             //   listData.addElement(tmpVal);

                //Red and green indicators of price
                olP = tools.findBetween(content,
                        "^ID^"+Integer.toString(i)+"^BUY_PRICE^",
                        "^BUY_PRICE^END^ID^"+Integer.toString(i)+"^");
                newP = tools.findBetween(content,
                        "^ID^"+Integer.toString(i)+"^CACHED_PRICE^",
                        "^CACHED_PRICE^END^ID^"+Integer.toString(i)+"^");


                if(Double.parseDouble(olP)<Double.parseDouble(newP)){
                    listData.addElement("<html><font color=#00ff00>"+"/\\ "+"<font color = 00FFFF> | </font>" +newP+"</font>"+
                            "<font color = #00FFFF> |</font>"
                            +" "+tmpVal+"</html>");
                }else if(Double.parseDouble(olP)==Double.parseDouble(newP)){
                    listData.addElement("<html><font color =orange>"+"// "+"<font color = #00FFFF> | </font>" +newP+"</font>"+
                            "<font color = #00FFFF> |</font>"
                            +" "+tmpVal+"</html>");
                }else if(Double.parseDouble(olP)>Double.parseDouble(newP)){
                    listData.addElement("<html><font color =red>"+"\\/ "+"<font color = #00FFFF> | </font>" +newP+"</font>"
                            +"<font color = #00FFFF> |</font>" +
                            " "+tmpVal+"</html>");
                }

              //  listData.addElement("<html><font color =green>Zcentrowane" +
               //         "</font><font color =red> kolorowe</font>" +
              //          "<font color = orange> dane</font></html>");

            }catch (StringIndexOutOfBoundsException e){}

        }

    }

    void refreshList(){
        list.updateUI();
    }

    Thread calculatePrices = new Thread(new Runnable() {
        @Override
        public void run() {
            listData.clear();
            listDataRaw.clear();
            int latestID = 0;try{latestID = database.latestId(workingInv,false);}catch (IOException a){}
            for(int i = 0;i<latestID;i++){
                listData.addElement("Requesting prices from the API...");
            }

             try{
             database.updatePrices(workingInv);
             }catch (IOException a){
              }

             fillListData();
             refreshList();
        }
    });

    String getContent(){
        Path pathh = Paths.get(tools.obtainCWD()+"/"+workingInv+"/"+"data");
        String contentt = "";
        try{
            contentt = Files.readString(pathh, StandardCharsets.US_ASCII);
        }catch (IOException e){}
        return contentt;
    }

    void setPanels(){
        add(primaryTOP);
        add(primaryBOTTOM);
        primaryTOP.add(topLeft);
        primaryTOP.add(topRight);
        topRight.setPreferredSize(new Dimension(235,400));
        topLeft.setPreferredSize(new Dimension(320,400));
        topRight.add(topRightOne);
        topRight.add(topRightTwo);
        topRight.add(topRightThree);
        topRight.add(topRightFour);
        topRight.add(topRightFive);
        topRight.add(topRightSix);


    }

    void configureWindow(){
        setTitle("Steam Investment Helper "+main.currVer+" | "+workingInv+" |");
        setSize(585,801);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,0));
        setLocation(main.targetX,main.targetY-200);
    }
}
