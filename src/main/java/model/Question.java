package model;

import java.util.List;

public class Question {

    int id;
    String question;
    String answer;
    List<String> options;

    // Constructor to initialize a Question object with question text and answer
    public Question(String question, String answer, List<String> options) {
        this.question = question;
        this.answer = answer;
        this.options = options;
    }

    // Constructor to initialize a Question object with ID, question text and answer
    public Question(int id, String question, String answer, List<String> options) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.options = options;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
