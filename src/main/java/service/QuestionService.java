package service;

import com.google.gson.GsonBuilder;
import model.Question;
import middleware.ValidationMiddleware;
import com.google.gson.Gson;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class QuestionService {

    private static final String AUTH = "admin:password";

    public List<Question> fetchQuestions(){
        return QuestionFetcher.fetchQuestions();
    }

    public void addQuestion(Question question){
        try {
            ValidationMiddleware.isValid(question);
            List<Question> existingQuestions = fetchQuestions();
            int newId = existingQuestions.size() + 1;
            question.setId(newId);

            URL url = new URL("http://localhost:3000/questions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String encodeAuth = Base64.getEncoder().encodeToString(AUTH.getBytes());
            conn.setRequestProperty("Authorization", "Basic " + encodeAuth);

            conn.setDoOutput(true);

            Gson gson = new GsonBuilder().create();
            String jsonInputString = gson.toJson(question);

            try (OutputStream os = conn.getOutputStream()){
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_CREATED){
                System.out.println("POST request not worked, Response Code: " + responseCode);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
