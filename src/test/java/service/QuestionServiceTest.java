package service;

import model.Question;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuestionServiceTest {

    private static int existingQuestions;
    private static final String AUTH = "Basic " + Base64.getEncoder().encodeToString("admin:password".getBytes());

    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;

        Response response = given()
                .header("Authorization", AUTH)
                .when()
                .get("/questions");

        existingQuestions = response.then().extract().path("size()");
    }

    @Order(1)
    @Test
    public void testGetAllQuestions(){

        try {
            given()
                .header("Authorization", AUTH)
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
                .header("Authorization", AUTH)
                .pathParam("id", 1)
            .when()
                .get("/questions/{id}")
            .then()
                .statusCode(200)
            .and()
                .assertThat()
                    .body("id", equalTo(1))
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
                .header("Authorization", AUTH)
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
                .header("Authorization", AUTH)
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
                .header("Authorization", AUTH)
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

    @Order(6)
    @Test
    public void testGetNonExistentQuestion(){
        int nonExistentId = existingQuestions + 100; // Assume this ID does not exist

        try {
            given()
                .header("Authorization", AUTH)
                .pathParam("id", nonExistentId)
            .when()
                .get("questions/{id}")
            .then()
                .assertThat()
                    .statusCode(404);

            System.out.println("testGetNonExistentQuestion passed");
        } catch (AssertionError e){
            System.out.println("Test failed: " +e.getMessage());
            fail("testGetNonExistentQuestion failed");
        }
    }

    @Order(7)
    @Test
    public void testCreateQuestionWithNoBody(){

        try {
            given()
                .header("Authorization", AUTH)
                .contentType(ContentType.JSON)
            .when()
                .post("/questions")
            .then()
                .assertThat()
                    .statusCode(400);

            System.out.println("testCreateQuestionWithNoBody passed");
        } catch (AssertionError e){
            System.out.println("Test failed: " +e.getMessage());
            fail("testCreateQuestionWithNoBody failed");
        }
    }

    @Order(8)
    @Test
    public void testUpdateNonExistentQuestion(){
        int nonExistentId = existingQuestions + 100; // Assume this ID does not exist
        Question updatedQuestion = new Question("What is the capital of Germany?", "Berlin");

        try {
            given()
                .header("Authorization", AUTH)
                .contentType(ContentType.JSON)
                .body(updatedQuestion)
                .pathParam("id", nonExistentId)
            .when()
                .put("/questions/{id}")
            .then()
                .assertThat()
                    .statusCode(404);

            System.out.println("testUpdateNonExistentQuestion passed");
        } catch (AssertionError e){
            System.out.println("Test failed: " +e.getMessage());
            fail("testUpdateNonExistentQuestion failed");
        }
    }

    @Order(9)
    @Test
    public void  testDeleteNonExistentQuestion(){
        int nonExistentId = existingQuestions + 100; // Assume this ID does not exist

        try {
            given()
                .header("Authorization", AUTH)
                .pathParam("id", nonExistentId)
            .when()
                .delete("/questions.{id}")
            .then()
                .assertThat()
                    .statusCode(404);

            System.out.println("testDeleteNonExistentQuestion passed");
        }catch (AssertionError e){
            System.out.println("Test failed: " +e.getMessage());
            fail("testDeleteNonExistentQuestion failed");
        }
    }

    @Order(10)
    @Test
    public void testCreateQuestionWithInvalidData(){
        Question invalidQuestion = new Question("", "");

        try {
            given()
                .header("Authorization", AUTH)
                .contentType(ContentType.JSON)
                .body(invalidQuestion)
            .when()
                .post("/questions")
            .then()
                .assertThat()
                    .statusCode(400);

            System.out.println("testCreateQuestionWithInvalidData passed");
        } catch (AssertionError e){
            System.out.println("Test failed: " +e.getMessage());
            fail("testCreateQuestionWithInvalidData failed");
        }
    }

    @Order(11)
    @Test
    public void testSearchQuestions(){
        String searchTerm = "capital";

        try {
            given()
                .header("Authorization", AUTH)
                .queryParam("q", searchTerm)
            .when()
                .get("/search")
            .then()
                .statusCode(200)
                .assertThat()
                    .body("size()", greaterThan(0));

            System.out.println("testSearchQuestions passed");
        } catch (AssertionError e){
            System.out.println("Test failed: " + e.getMessage());
            fail("testSearchQuestions failed");
        }
    }

    @Order(12)
    @Test
    public void testPagination(){
        int page = 1;
        int limit = 2;

        try {
            given()
                .header("Authorization", AUTH)
                .queryParam("page", page)
                .queryParam("limit", limit)
            .when()
                .get("/questions")
            .then()
                .statusCode(200)
                .assertThat()
                    .body("size()", lessThanOrEqualTo(limit));

            System.out.println("testPagination passed");
        } catch (AssertionError e) {
            System.out.println("Test failed: " + e.getMessage());
            fail("testPagination failed");
        }
    }
}
