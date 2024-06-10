package middleware;

import model.Question;

public class ValidationMiddleware {

    // Method to validate a Question object
    public static void isValid(Question question) throws IllegalArgumentException{
        // Checking if the Question object or its attributes are null or empty
        if (question == null || question.getQuestion() == null || question.getQuestion().trim().isEmpty()
                || question.getAnswer() == null | question.getAnswer().trim().isEmpty()){
            // Throwing an IllegalArgumentException if any of the conditions are met
            throw new IllegalArgumentException("Question and answer fields cannot be empty");
        }
    }
}
