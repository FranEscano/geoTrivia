import java.util.List;
import java.util.Scanner;

public class geoTriviaClient {
   public static void main(String[] args) {

       Scanner scanner = new Scanner(System.in);

       while (true) {
           System.out.println("Welcome to GeoTrivia");
           System.out.println("Please choose an option:");
           System.out.println("1. Play");
           System.out.println("2. Add a new question");
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
                   System.out.println("Thanks for playing. See you soon!");
                   scanner.close();
                   return;
               default:
                   System.out.println("Not valid option. Try again");
           }
       }
   }

   private static void playGame(Scanner scanner){

        List<Question> questions = QuestionFetcher.fetchQuestions();

        if(questions == null) {
            System.out.println("Could not read questions from API. Back to the main menu");
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
    }

    private static void addNewQuestion(Scanner scanner){

        System.out.println("Enter the question: ");
        String questionText = scanner.nextLine();

        System.out.println("Enter the answer: ");
        String answerText = scanner.nextLine();

        int newId = QuestionFetcher.getNextQuestionId();

        Question newQuestion = new Question(newId, questionText, answerText);

        if(QuestionFetcher.addQuestion(newQuestion)){
            System.out.println("Question added successfully!");
        } else {
            System.out.println("Failed to add the question.");
        }
    }
}
