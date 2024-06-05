import java.util.Scanner;
public class TriviaGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // read the user's answers from the console
        int score = 0;

        //Questions
        String[] questions = {
                "What is the capital of France?",
                "What is the longest river in the world?",
                "What is the largest country in the world?",
                "What is the largest desert in the world?",
                "On which country is Argentina located?"
                // more questions here
        };

        //Answers
        String[] answers = {
                "Paris",
                "Nilo",
                "Russia",
                "Sahara",
                "South America"
                // more answers here
        };

        /*
         iterates over the questions, displays each question, reads the user's answer, and compares it to the
         correct answer
         */
        for (int i = 0; i < questions.length; i++) {
            System.out.println("Question " +(i + 1) + ": " +questions[i]);
            String userAnswer = scanner.nextLine();

            if (userAnswer.equalsIgnoreCase(answers[i])){ // compare user's answer without considering case sensitivity
                System.out.println("Correct!");
                score++; // incremented for each correct answer
            } else{
                System.out.println("Incorrect. The correct answer is: " +answers[i]);
            }
        }

        System.out.println("Your final score is: " +score +" out of " + questions.length);
        scanner.close();
    }
}
