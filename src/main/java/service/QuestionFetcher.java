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
import java.util.List;

public class QuestionFetcher {

    public static List<Question> fetchQuestions(){
        Gson gson = new Gson();
        List<Question> questions = null;

        try {
            URL url = new URL("http://localhost:3000/questions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200){
                throw new RuntimeException("httpResponseCode: " + responseCode);
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null){
                    content.append(inputLine);
                }

                in.close();
                conn.disconnect();

                Type questionListType = new TypeToken<List<Question>>(){}.getType();
                questions = gson.fromJson(content.toString(), questionListType);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return questions;
    }
}
