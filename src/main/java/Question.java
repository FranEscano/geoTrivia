public class Question {

    int id;
    String question;
    String answer;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Question(int id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }
}
