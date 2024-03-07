import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main(String[] args) {

        String userMessage = "hi"  ;
        String response = chatGPT(userMessage);
        System.out.println("ChatGPT response: " + response);
    }

    public static String chatGPT(String message) {

        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";

        try {

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");


            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";

            con.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();


            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return (response.toString().split(   "\"content\":\"")[1].split(  "\"")[0]).substring( 4) ;
        }
         catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}