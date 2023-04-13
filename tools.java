import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class tools {
    public static void main(String[] args) {

    }

    public static String obtainCWD(){
        // it adds /src/Investments by default !!TO BE CORRECTED!!
        String cwd = System.getProperty("user.dir");
        return cwd+"/src/Investments";
    }

    public static String findBetween(String source,String fWord,String sWord){

          String match = source.substring(source.indexOf(fWord)+fWord.length(),
                  source.indexOf(sWord));
        return match;
    }

    public static void changeValue(String name,String prefix,int oldData,int newData,String Suffix) throws IOException {
        String tempPath = (tools.obtainCWD()+"/"+name+"/"+"data");
        Scanner scc = new Scanner(new File(tempPath));
        StringBuffer bufferr = new StringBuffer();
        while(scc.hasNextLine()){
            bufferr.append(scc.nextLine()+System.lineSeparator());
        }

        String tempContent = bufferr.toString();
        scc.close();
        String oL = (String)(prefix+oldData+Suffix);
        System.out.println(oL);



        String nL = prefix+newData+Suffix;
        System.out.println(nL);
        tempContent = tempContent.replace(oL,nL);
        FileWriter writerr = new FileWriter(tempPath);
        writerr.append(tempContent);
        writerr.flush();

    }

    public static void changeValueString(String name,String prefix,String oldData,String newData,String Suffix) throws IOException {
        String tempPath = (tools.obtainCWD()+"/"+name+"/"+"data");
        Scanner scc = new Scanner(new File(tempPath));
        StringBuffer bufferr = new StringBuffer();
        while(scc.hasNextLine()){
            bufferr.append(scc.nextLine()+System.lineSeparator());
        }

        String tempContent = bufferr.toString();
        scc.close();
        String oL = (String)(prefix+oldData+Suffix);
        System.out.println(oL);



        String nL = prefix+newData+Suffix;
        System.out.println(nL);
        tempContent = tempContent.replace(oL,nL);
        FileWriter writerr = new FileWriter(tempPath);
        writerr.append(tempContent);
        writerr.flush();

    }
}
