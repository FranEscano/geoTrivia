package client;

import model.Question;
import service.QuestionService;

import java.util.List;
import java.util.Scanner;

public class GeoTriviaClient {

    private static final QuestionService questionService = new QuestionService();
   public static void main(String[] args) { // Main method
       Scanner scanner = new Scanner(System.in);

       while (true) { // Looping indefinitely until user choosed to exit
           System.out.println("Welcome to GeoTrivia");
           System.out.println("Choose an option:");
           System.out.println("1. Play");
           System.out.println("2. Add new question");
           System.out.println("3. Exit");
           int choice = scanner.nextInt();
           scanner.nextLine();

           switch (choice) { // Switch case based on user's choice
               case 1:
                   playGame(scanner);
                   break;
               case 2:
                   addNewQuestion(scanner);
                   break;
               case 3:
                   System.out.println("Exiting...");
                   scanner.close();
                   System.exit(0);
               default:
                   System.out.println("Invalid option, please try again");
           }
       }
   }

   private static void playGame(Scanner scanner){ // Method to play the game
        List<Question> questions = questionService.fetchQuestions(); // Fetching questions from QuestionService

        if(questions == null) {
            System.out.println("Could not read questions from API.");
            return;
        }

        int score = 0; // Initializing the score variable to keep track of correct answers

        for (Question q : questions) { // Looping through each question
            System.out.println(q.getQuestion());
            String userAnswer = scanner.nextLine();

            if (userAnswer.equalsIgnoreCase(q.getAnswer())){ // Checking if user's answer matches the correct answer (case insensitive)
                System.out.println("Correct!");
                score++; // incremented for each correct answer
            } else{
                System.out.println("Incorrect. The correct answer is: " +q.getAnswer());
            }
        }

        System.out.println("Your final score is: " +score +" out of " + questions.size());
    }

    private static void addNewQuestion(Scanner scanner){ // Method to add a new question
        System.out.println("Enter the question: ");
        String questionText = scanner.nextLine();
        System.out.println("Enter the answer: ");
        String answerText = scanner.nextLine();

        Question newQuestion = new Question(questionText, answerText); // Creating a new Question object with the provided question and answer
        questionService.addQuestion(newQuestion); // Adding the new question to the QuestionService
    }
}
