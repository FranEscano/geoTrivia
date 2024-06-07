import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuestionFetcherTest {

    private static int existingQuestions;

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;

        Response response = given()
                .when()
                .get("/questions");

        existingQuestions = response.then().extract().path("size()");
    }

    @Order(1)
    @Test
    public void testGetAllQuestions(){

        try {
            given()
            .when()
                .get("/questions")
            .then()
                .statusCode(200)
            .and()
                .assertThat()
                .body("size()", equalTo(existingQuestions));

            System.out.println("testGetAllQuestions passed");
        } catch (AssertionError e){
            System.out.println("Test failed: " +e.getMessage());
            fail("testGetAllQuestions failed");
        }

    }

    @Order(2)
    @Test
    public void testGetQuestionById(){
        try {
            given()
                .pathParam("id", 1)
            .when()
                .get("/questions/{id}")
            .then()
                .statusCode(200)
            .and()
                .assertThat()
                    .body("id", equalTo("1"))
                    .body("question", equalTo("What is the capital of France?"))
                    .body("answer", equalTo("Paris"));
            System.out.println("testGetQuestionById passed");
        } catch (AssertionError e){
            System.out.println("Test failed: " +e.getMessage());
            fail("testGetQuestionById failed");
        }
    }

    @Order(3)
    @Test
    public void testCreateQuestion(){

        Question question = new Question(existingQuestions+1, "What is the capital of Italy?", "Rome");

        try {
            given()
                .contentType(ContentType.JSON)
                .body(question)
            .when()
                .post("/questions")
            .then()
                .statusCode(201)
            .and()
                .assertThat()
                .body("answer", equalTo("Rome"));

            System.out.println("testCreateQuestion passed");
        } catch (AssertionError e){
            System.out.println("Test failed: " +e.getMessage());
            fail("testGetAllQuestions failed");
        }
    }

    @Test
    @Order(4)
    public void testUpdateQuestion(){
        Question updatedQuestion = new Question("What is the capital of Spain?", "Madrid");

        try {
            given()
                .contentType(ContentType.JSON)
                .body(updatedQuestion)
                .pathParam("id", 1)
            .when()
                .put("/questions/{id}")
            .then()
                .statusCode(200)
            .and()
                .body("answer", equalTo("Madrid"));

            System.out.println("testUpdateQuestion passed");
        } catch (AssertionError e){
            System.out.println("Test failed: " +e.getMessage());
            fail("testUpdateQuestion failed");
        }


    }

    @Test
    @Order(5)
    public void testDeleteQuestions(){
        int lastQuestionId = existingQuestions;

        try {
            given()
                .pathParam("id", lastQuestionId)
            .when()
                .delete("/questions/{id}")
            .then()
                .assertThat()
                .statusCode(200);

            System.out.println("testDeleteQuestion passed");
        } catch (AssertionError e){
            System.out.println("Test failed: " +e.getMessage());
            fail("testDeleteQuestion failed");
        }
    }
}
