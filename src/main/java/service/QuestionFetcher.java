package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;

public class QuestionFetcher {

    private static final String AUTH = "admin:password"; // Defining authorization credentials

    //Method to fetch questions from a remote API
    public static List<Question> fetchQuestions(){
        Gson gson = new Gson();
        List<Question> questions = null;

        try {
            URL url = new URL("http://localhost:3000/questions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            String encondeAuth = Base64.getEncoder().encodeToString(AUTH.getBytes()); // Encoding authorization credentials
            conn.setRequestProperty("Authorization", "Basic " + encondeAuth); // Setting Authorization header

            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200){
                throw new RuntimeException("httpResponseCode: " + responseCode);
            } else {
                // Creating a BufferedReader to read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder content = new StringBuilder(); // Creating a StringBuilder to store the response
                String inputLine; // Declaring a variable to store each line of the response

                while ((inputLine = in.readLine()) != null){
                    content.append(inputLine); // Appending the line to the StringBuilder
                }

                in.close();
                conn.disconnect();

                Type questionListType = new TypeToken<List<Question>>(){}.getType();
                // Parsing the JSON response into a List<Question>
                questions = gson.fromJson(content.toString(), questionListType); // Parsing the JSON response into a List<Question>
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return questions;
    }
}
