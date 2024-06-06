import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class QuestionFetcherTest {

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
    }

    @Test
    public void testFetchQuestions(){
        Response response =
                given()
                .when()
                    .get("/questions")
                .then()
                    .statusCode(200)
                    .extract()
                    .response();

        // Basic validation,
        response.then().body("[0].id", notNullValue());
        response.then().body("[0].question", notNullValue());
        response.then().body("[0].answer", notNullValue());
    }

    @Test
    public void testFirstQuestion(){
        given()
        .when()
            .get("/questions")
        .then()
            .statusCode(200)
            .body("[0].id", equalTo("1"))
            .body("[0].question", equalTo("What is the capital of France?"))
            .body("[0].answer", equalTo("Paris"));
    }
}
