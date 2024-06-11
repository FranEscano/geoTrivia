package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Question;
import middleware.ValidationMiddleware;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class QuestionService {

    private static final String AUTH = "admin:password";

    // Method to fetch questions using the QuestionsFetcher class
    public List<Question> fetchQuestions(){
        return QuestionFetcher.fetchQuestions(); // Returning the List of questions fetched from the remote API
    }

    // Method to fetch categories
    public  List<String> fetchCategories(){
        List<String> categories = new ArrayList<>();
        try {
            URL url = new URL("http://localhost:3000/categories");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            String encodeAuth = Base64.getEncoder().encodeToString(AUTH.getBytes());
            conn.setRequestProperty("Authorization", "Basic " + encodeAuth);

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

                Gson gson = new Gson();
                Type categoryListType = new TypeToken<List<Map<String, Object>>>(){}.getType();
                List<Map<String, Object>> categoriesList = gson.fromJson(content.toString(), categoryListType);

                for (Map<String, Object> category : categoriesList){
                    String categoryName = (String) category.get("name");
                    categories.add(categoryName);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return categories;
    }

    // Method to add a new question to a specific category

    // Method to add a new question
    public void addQuestionToCategory(Question question, String category){
        try {
            ValidationMiddleware.isValid(question); // Validating the new question using Validation Middleware

            URL url = new URL("http://localhost:3000/categories/" + category + "/questions");
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
