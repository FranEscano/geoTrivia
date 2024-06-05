package server;

/*
Holds question and answer pairs
 */
public class Question {

    private final int id;
    private final String question;
    private final String answer;

    public Question(int id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
