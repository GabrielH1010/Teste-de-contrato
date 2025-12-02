package schema;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import pact.utils.Utils;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class AddPetSchemaValidationTest {

    @Test
    void validateAddPetSchema() {
        JSONObject body = Utils.lerArquivoJson("bodyAddPet.json");

        RestAssured.given()
                .baseUri("https://petstore.swagger.io/v2")
                .contentType(ContentType.JSON)
                .body(body.toString())
                .log().all()
        .when()
                .post("/pet")
        .then()
                .statusCode(200)
                .log().all()
                .body(matchesJsonSchemaInClasspath("schemas/pet-schema.json"));
    }
}