package model;

public class Question {

    int id;

    String question;

    String answer;

    // Constructor to initialize a Question object with question text and answer
    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    // Constructor to initialize a Question object with ID, question text and answer
    public Question(int id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
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
}
