package middleware;

import model.Question;

public class ValidationMiddleware {

    public static void isValid(Question question) throws IllegalArgumentException{
        if (question == null || question.getQuestion() == null || question.getQuestion().trim().isEmpty()
                || question.getAnswer() == null | question.getAnswer().trim().isEmpty()){
            throw new IllegalArgumentException("Question and answer fields cannot be empty");
        }
    }
}
