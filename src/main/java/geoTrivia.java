import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

public class geoTrivia {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // read the user's answers from the console
        Gson gson = new Gson(); // Parses the JSON data

        List<Question> questions;

        // Reads the JSON file and parses it into a list of `Question` objects
        try(FileReader reader = new FileReader("src/main/resources/questions.json")) {
            Type questionListType = new TypeToken<List<Question>>() {}.getType();
            questions = gson.fromJson(reader, questionListType);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not read questions from file.");
            return;
        }

        int score = 0;

        /*
         iterates over the questions, displays each question, reads the user's answer, and compares it to the
         correct answer
         */
        for (Question q : questions) {
            System.out.println(q.question);
            String userAnswer = scanner.nextLine();

            if (userAnswer.equalsIgnoreCase(q.answer)){ // compare user's answer without considering case sensitivity
                System.out.println("Correct!");
                score++; // incremented for each correct answer
            } else{
                System.out.println("Incorrect. The correct answer is: " +q.answer);
            }
        }

        System.out.println("Your final score is: " +score +" out of " + questions.size());
        scanner.close();
    }
}
