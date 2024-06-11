package client;

import model.Question;
import service.QuestionService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GeoTriviaClient {
    private static final QuestionService questionService = new QuestionService();
    public static void main(String[] args) { // Main method
       Scanner scanner = new Scanner(System.in);

       while (true) { // Looping indefinitely until user chose to exit
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
            List<String> options = q.getOptions();
            if (options != null && !options.isEmpty()) {
                Collections.shuffle(options); // Shuffle the options to randomize their order
                for (int i = 0; i < options.size(); i++) {
                    System.out.println((i + 1) + ". " + options.get(i));
                }
            }
            assert options != null;
            System.out.println("Select the correct opcion (1-" + options.size() + "):");
            int userChoice = scanner.nextInt();
            scanner.nextLine();

            if (userChoice > 0 && userChoice <= options.size() && options.get(userChoice - 1)
                    .equalsIgnoreCase(q.getAnswer())){
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect. The correct answer is: " + q.getAnswer());
            }
        }

        System.out.println("Your final score is: " +score +" out of " + questions.size());
    }

    private static void addNewQuestion(Scanner scanner){ // Method to add a new question
        System.out.println("Enter the question: ");
        String questionText = scanner.nextLine();
        System.out.println("Enter the answer: ");
        String answerText = scanner.nextLine();
        System.out.println("Enter the options separated by commas: ");
        String optionsText = scanner.nextLine();
        List<String> options = Arrays.asList(optionsText.split(","));

        List<String> categories = questionService.fetchCategories();

        System.out.println("Choose a category:");
        for (int i = 0; i <categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }
        int categoryChoice = scanner.nextInt();

        scanner.nextLine();

        if (categoryChoice > 0 && categoryChoice <= categories.size()) {
            String selectedCategory = categories.get(categoryChoice - 1);

            Question newQuestion = new Question(questionText, answerText, options); // Creating a new Question object with the provided question and answer

            questionService.addQuestionToCategory(newQuestion, selectedCategory); // Adding the new question to the QuestionService
        } else {
            System.out.println("Invalid category choice.");
        }
    }
}
