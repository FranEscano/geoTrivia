package client;

import model.Question;
import service.QuestionService;

import java.util.List;
import java.util.Scanner;

public class GeoTriviaClient {

    private static final QuestionService questionService = new QuestionService();
   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);

       while (true) {
           System.out.println("Welcome to GeoTrivia");
           System.out.println("Choose an option:");
           System.out.println("1. Play");
           System.out.println("2. Add new question");
           System.out.println("3. Exit");
           int choice = scanner.nextInt();
           scanner.nextLine();

           switch (choice) {
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

   private static void playGame(Scanner scanner){
        List<Question> questions = questionService.fetchQuestions();

        if(questions == null) {
            System.out.println("Could not read questions from API.");
            return;
        }

        int score = 0;

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
    }

    private static void addNewQuestion(Scanner scanner){
        System.out.println("Enter the question: ");
        String questionText = scanner.nextLine();
        System.out.println("Enter the answer: ");
        String answerText = scanner.nextLine();

        Question newQuestion = new Question(questionText, answerText);
        questionService.addQuestion(newQuestion);
    }
}
