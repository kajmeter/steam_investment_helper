import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class checkPrice {
    static String classUrl;
    static String appID = "730";
    static String currency = "6";
    public static String finalPrice = "";

    static String JSONout;
    public static void main(String Url) throws IOException {
        classUrl = Url;
    //    System.out.println(getMarketHashName());
        String outUrl = "https://steamcommunity.com/market/priceoverview/?market_hash_name="
                +getMarketHashName()
                +"&appid="
                +appID
                +"&currency="
                +currency;
      //  System.out.println(outUrl);

/*
        try(Scanner scanner = new Scanner(new URL("http://steamcommunity.com/market/priceoverview/?market_hash_name=2020%20RMR%20Legends&appid=730&currency=1").openStream(),
                StandardCharsets.UTF_8.toString()))
        {
            scanner.useDelimiter("");
            String t = scanner.hasNext() ? scanner.next() : "";
            System.out.println(t);
        }
 */

        URL url = new URL(outUrl);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String inputLine;
        while((inputLine = in.readLine()) != null)
            JSONout = inputLine;
        in.close();


     //   System.out.println(JSONout);

        JSONout = JSONout.replace("{\"success\":true,\"lowest_price\":\"","");

        char[] JSONoutArr = new char[JSONout.length()];

        for(int i = 0;i<JSONout.length();i++){
            if(JSONout.charAt(i) == '"'){
                break;
            }
            JSONoutArr[i] = JSONout.charAt(i);
        }


        for(int i = 0 ; i < JSONoutArr.length;i++){
            if(JSONoutArr[i]  == 0){
                break;
            }
            finalPrice = finalPrice+JSONoutArr[i];
        }
        System.out.println("[Debug]"+finalPrice);


    }
    static String getMarketHashName(){
        String hash = classUrl.substring(47);
        return hash;
    }

}
