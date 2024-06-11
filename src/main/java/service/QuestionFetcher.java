package service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import config.AppConfig;
import model.Question;
import util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionFetcher {

    //Method to fetch questions from a remote API
    public static List<Question> fetchQuestions(){
        Gson gson = new Gson();
        List<Question> allQuestions = new ArrayList<>();

        try {
            URL url = new URL(AppConfig.BASE_URL + "/categories");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            String encondeAuth = Utils.encodeCredentials(); // Encoding authorization credentials
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

                Type categoryListType = new TypeToken<List<Map<String, Object>>>(){}.getType();
                List<Map<String, Object>> categories = gson.fromJson(content.toString(), categoryListType);

                for (Map<String, Object> category : categories) {
                    List<Map<String, Object>> questions = (List<Map<String, Object>>) category.get("questions");
                    for (Map<String, Object> q : questions) {
                        int id = ((Double) q.get("id")).intValue();
                        String questionText = (String) q.get("question");
                        String answer = (String) q.get("answer");
                        List<String> options = (List<String>) q.get("options");
                        Question question = new Question(id, questionText, answer, options);
                        allQuestions.add(question);
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return allQuestions;
    }
}
