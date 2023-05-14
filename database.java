import java.io.*;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/*
Todo/Buglist :
- Remove timer is still bugging.(once displayed -1)
- add icon
- work on lightmode

 */
public class database {

    public static int tempID;


    public static void main(String[] args) {

    }

    public static void create(String name) throws IOException {
        System.out.println(tools.obtainCWD());
        new File(tools.obtainCWD()+"/"+name).mkdirs();
        File data = new File(tools.obtainCWD()+"/"+name+"/"+"data");
        data.createNewFile();

        // adding buyprice

        FileWriter fw = new FileWriter(tools.obtainCWD()+"/"+name+"/"+"data",true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("^LATEST^ID^0^LATEST^END^");
        bw.newLine();
        bw.write("^QUANTITY_SUM^0^QUANTITY_SUM^END^");
        bw.newLine();
        bw.close();

    }

    public static void renameInvestment(String oldName, String newName){
        File oldFile = new File(tools.obtainCWD()+"/"+oldName);
        File newFile = new File(tools.obtainCWD()+"/"+newName);

        oldFile.renameTo(newFile);
    }

    public static void remove(String name){
        File data = new File(tools.obtainCWD()+"/"+name+"/"+"data");
        data.delete();

        File directory = new File(tools.obtainCWD()+"/"+name);
        directory.delete();
    }

    public static int latestId(String name,boolean correct) throws IOException {
        Path path = Paths.get(tools.obtainCWD()+"/"+name+"/"+"data");
        String content = Files.readString(path, StandardCharsets.US_ASCII);
        int id = Integer.parseInt(tools.findBetween(content,"^LATEST^ID^","^LATEST^END^"));

        if(correct){
            /*
            String tempPath = (tools.obtainCWD()+"/"+name+"/"+"data");
            Scanner scc = new Scanner(new File(tempPath));
            StringBuffer bufferr = new StringBuffer();
            while(scc.hasNextLine()){
                bufferr.append(scc.nextLine()+System.lineSeparator());
            }

            String tempContent = bufferr.toString();
            scc.close();
            String oL = (String)("^LATEST^ID^"+id+"^LATEST^END^");
            System.out.println(oL);

            id = id+1;
            tempID = id;

            String nL = "^LATEST^ID^"+id+"^LATEST^END^";
            System.out.println(nL);
            tempContent = tempContent.replace(oL,nL);
            FileWriter writerr = new FileWriter(tempPath);
            writerr.append(tempContent);
            writerr.flush();
*/


            tools.changeValue(name,"^LATEST^ID^",id,id+1,"^LATEST^END^");

            id = id+1;
            tempID = id;

        }

        return id;
    }

    public static void addItem(String name,String ItemName,int quantity,double buy_price) throws IOException {
        FileWriter fw = new FileWriter(tools.obtainCWD()+"/"+name+"/"+"data",true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(" ");
        bw.newLine();
        bw.write("^ID^"+latestId(name,true)+"^NAME^"+ItemName+"^NAME^END^"+"ID^"+tempID+"^");
        bw.newLine();
        bw.write("^ID^"+tempID+"^QUANTITY^"+quantity+"^QUANTITY^END^"+"ID^"+tempID+"^");
        bw.newLine();
        bw.write("^ID^"+tempID+"^BUY_PRICE^"+buy_price+"^BUY_PRICE^END^"+"ID^"+tempID+"^");
        bw.newLine();
        bw.write("^ID^"+tempID+"^CACHED_PRICE^"+0+"^CACHED_PRICE^END^"+"ID^"+tempID+"^");
        bw.close();
        System.out.println();

    }


    public static void removeItem(String name,String itemID) throws IOException {
        Path pathh = Paths.get(tools.obtainCWD()+"/"+name+"/"+"data");
        String contentt = Files.readString(pathh, StandardCharsets.US_ASCII);

        String preparedF = "^ID^"+itemID+"^NAME^";
        String preparedL = "^NAME^END^"+"ID^"+itemID+"^";
             String findName = tools.findBetween(contentt,preparedF,preparedL);

             String preparedFs = "^ID^"+itemID+"^QUANTITY^";
             String preparedLs = "^QUANTITY^END^"+"ID^"+itemID+"^";

        String findQuan = tools.findBetween(contentt,preparedFs,preparedLs);

        System.out.println(findName);

        String pre = "^ID^"+itemID+"^";
        String suf = "^ID^"+itemID+"^";


        tools.changeValueString(name,pre,"NAME^"+findName+"^NAME^END","REMOVED",suf);
        tools.changeValueString(name,pre,"QUANTITY^"+findQuan+"^QUANTITY^END","REMOVED",suf);




       // tools.changeValueString(name,pre,"NAME^"+findName+"^NAME^END","REMOVED",suf);


        //add cleanup of removed ones
    }

    public static void changeQuantity(String name,String itemID,String newData) throws IOException {
        Path pathh = Paths.get(tools.obtainCWD()+"/"+name+"/"+"data");
        String contentt = Files.readString(pathh, StandardCharsets.US_ASCII);

        String preparedFs = "^ID^"+itemID+"^QUANTITY^";
        String preparedLs = "^QUANTITY^END^"+"ID^"+itemID+"^";

        String findQuan = tools.findBetween(contentt,preparedFs,preparedLs);

        String pre = "^ID^"+itemID+"^QUANTITY^";
        String suf = "^QUANTITY^END^ID^"+itemID+"^";

        tools.changeValueString(name,pre,findQuan,newData,suf);

    }

    public static void changeCachedPrice(String name,String itemID,String newData) throws IOException {
        Path pathh = Paths.get(tools.obtainCWD()+"/"+name+"/"+"data");
        String contentt = Files.readString(pathh, StandardCharsets.US_ASCII);

        String pre = "^ID^"+itemID+"^CACHED_PRICE^";
        String suf = "^CACHED_PRICE^END^"+"ID^"+itemID+"^";

        String findPrice = tools.findBetween(contentt,pre,suf);

        tools.changeValueString(name,pre,findPrice,newData,suf);

    }

    public static void updatePrices(String name) throws IOException {
        int latest = latestId(name,false);
        String pre;
        String suf;

        String pren;
        String sufn;

        String tempPrice = null;
        String tempName = null;


        Path pathh = Paths.get(tools.obtainCWD()+"/"+name+"/"+"data");
        String contentt = Files.readString(pathh, StandardCharsets.US_ASCII);

        for(int i = 1;i<=latest;i++){
            System.out.println("caching ...");
            pre = "^ID^" + i + "^CACHED_PRICE^";
            suf = "^CACHED_PRICE^END^" + "ID^" + i + "^";

            pren = "^ID^" + i + "^NAME^";
            sufn = "^NAME^END^" + "ID^" + i + "^";

            try{
                tempPrice = tools.findBetween(contentt, pre, suf);
                tempName = tools.findBetween(contentt, pren, sufn);
            }catch(StringIndexOutOfBoundsException ex){
                System.out.println("REMEMBER TO CLEANUP DATA");
            }



            try{
                checkPrice.main(findItem.find(tempName));
                String tmpprice = checkPrice.finalPrice;

               
                tmpprice = tmpprice.replaceAll("zł","");
                tmpprice = tmpprice.replaceAll(",",".");

                tools.changeValueString(name, pre, tempPrice, tmpprice, suf);
                checkPrice.finalPrice = "";
            }catch(MalformedInputException ex2){
                System.out.println("REMEMBER TO CLEANUP DATA");
            }

        }

    }

    public static void sumUp(String name) throws IOException {
        Path pathh = Paths.get(tools.obtainCWD()+"/"+name+"/"+"data");
        String contentt = Files.readString(pathh, StandardCharsets.US_ASCII);


        String findQuan;
        int findQuani = 1;
        int sum = 0;
        for(int i = 1; i <= latestId(name,false);i++){
            try {
                findQuan = tools.findBetween(contentt, "^ID^" + i + "^QUANTITY^"
                        , "^QUANTITY^END^" + "ID^" + i + "^");
                   findQuani = Integer.parseInt(findQuan);
                   sum = sum + findQuani;
            }
            catch(StringIndexOutOfBoundsException ex){
                System.out.println("REMEMBER TO CLEAN UP DATA FILE");
            }
        }
        System.out.println(sum);


        tools.changeValueString(name,"^QUANTITY_SUM^",tools.findBetween(contentt,"^QUANTITY_SUM^","^QUANTITY_SUM^END^"),
                String.valueOf(sum),"^QUANTITY_SUM^END^");
        

    }


}
