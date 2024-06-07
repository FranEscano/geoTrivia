import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.validation.*;
import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class QuestionFetcher {

    private static final String API_URL = "http://localhost:3000/questions";
    private static final Gson gson = new Gson();
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static List<Question> fetchQuestions(){
        List<Question> questions = null;

        try{
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200){
                throw new RuntimeException("HttpResponseCode: " +responseCode);
            } else{
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null){
                    content.append(inputLine);
                }

                in.close();
                conn.disconnect();

                Type questionListType = new TypeToken<List<Question>>() {}.getType();
                questions = gson.fromJson(content.toString(), questionListType);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return questions;
    }

    public static int getNextQuestionId(){

        List<Question> questions = fetchQuestions();
        if (questions == null || questions.isEmpty()){
            return 1; //If there's any questions, starts with 1
        }
        // Sorts ID of questions in descending order and obtains the highest value
        Question highestIdQuestion = questions.stream()
                .max(Comparator.comparingInt(Question::getId))
                .orElseThrow();
        return highestIdQuestion.getId() + 1;
    }

    public static boolean addQuestion(Question question){

        // Validate the question
        Set<ConstraintViolation<Question>> violations = validator.validate(question);
        if (!violations.isEmpty()){
            for (ConstraintViolation<Question> violation : violations){
                System.out.println(violation.getMessage());
            }

            return false;
        }

        try{
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = gson.toJson(question);
            try (OutputStream os = conn.getOutputStream()){
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            conn.disconnect();

            return  responseCode == 201;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateQuestion(int id, Question question){
        //Validate the question
        Set<ConstraintViolation<Question>> violations = validator.validate(question);
        if(!violations.isEmpty()){
            for (ConstraintViolation<Question> violation : violations){
                System.out.println(violation.getMessage());
            }
            return false;
        }

        try {
            URL url = new URL(API_URL + "/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = gson.toJson(question);
            try (OutputStream os = conn.getOutputStream()){
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            conn.disconnect();

            return responseCode == 200;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
