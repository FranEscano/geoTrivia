package client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import server.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class geoTriviaClient {
    private static final String SERVER_URL = "http://localhost:4567";
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); // read the user's answers from the console
        Gson gson = new Gson(); // Parses the JSON data

        List<Question> questions;

        // Reads the JSON file and parses it into a list of `server.Question` objects
        try {
            URL url = new URL(SERVER_URL +"/questions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200){
                throw new RuntimeException("HttpResponseCode: " +responseCode);
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                conn.disconnect();

                Type questionListType = new TypeToken<List<Question>>() {}.getType();
                questions = gson.fromJson(response.toString(), questionListType);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not read questions from the server.");
            return;
        }

        int score = 0;

        /*
         iterates over the questions, displays each question, reads the user's answer, and compares it to the
         correct answer
         */
        for (Question q : questions) {
            System.out.println(q.getQuestion());
            String userAnswer = scanner.nextLine();

            if (userAnswer.equalsIgnoreCase(q.getAnswer())){ // compare user's answer without considering case sensitivity
                System.out.println("Correct!");
                score++; // incremented for each correct answer
            } else{
                System.out.println("Incorrect. The correct answer is: " +q.getAnswer());
            }
        }

        System.out.println("Your final score is: " +score +" out of " + questions.size());
        scanner.close();
    }
}
