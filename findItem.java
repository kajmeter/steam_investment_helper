import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
public class findItem {
    static public String inputClass;
    static String findItemLink;
    public static String matchedLink;

    public static String find(String input) {
        inputClass = input;
        inputClass = input.replace(" ","+");

        findItemLink = "https://steamcommunity.com/market/search?appid="
                +checkPrice.appID
                +"&q="
                +inputClass;
        //System.out.println("[Debug]"+findItemLink);

        String content = null;
        URLConnection connection = null;
        try {
            connection = new URL(findItemLink).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        }catch (Exception ex){
            ex.printStackTrace();
            }

        String fWord = "<a class=\"market_listing_row_link\" href=\"";
        String sWord = "\" id=\"resultlink_0\">";

        matchedLink = content.substring(content.indexOf(fWord)+fWord.length(),
                      content.indexOf(sWord));

        //System.out.println("Link found : "+matchedLink);

        return matchedLink;

        }

    }