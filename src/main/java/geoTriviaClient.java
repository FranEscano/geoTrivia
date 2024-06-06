//import com.google.gson.Gson;
import java.util.List;
import java.util.Scanner;

public class geoTriviaClient {
   public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
//        Gson gson = new Gson();

        List<Question> questions = QuestionFetcher.fetchQuestions();

        if(questions == null) {
            System.out.println("Could not read questions from API.");
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
