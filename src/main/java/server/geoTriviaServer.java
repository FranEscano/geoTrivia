package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import spark.Spark;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class geoTriviaServer {

    public static void main(String[] args) {
        Spark.port(4567); // Configure the port in which the server will run

        Gson gson = new Gson();

        Spark.get("/questions", (req, res) -> {
            res.type("application/json");
            List<Question> questions = readQuestionsFromJson();
            return gson.toJson(questions);
        });
    }

    private static List<Question> readQuestionsFromJson() throws IOException{
        try (FileReader reader = new FileReader("src/main/resources/questions.json")){
            Type questionListType = new TypeToken<List<Question>>() {}.getType();
            return new Gson().fromJson(reader, questionListType);
        }
    }
}
