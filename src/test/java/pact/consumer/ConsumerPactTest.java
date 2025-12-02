package pact.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pact.utils.Utils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "PetProvider", port = "1234")
public class ConsumerPactTest {

    @Pact(consumer = "PetConsumer")
    public V4Pact createPact(PactBuilder builder) {

        //Request body
        PactDslJsonBody requestBody = (PactDslJsonBody) new PactDslJsonBody()
                .numberType("id", 99999)
                .stringValue("name", "Flash")
                .stringValue("status", "Pendente")
                .array("photoUrls")
                .stringValue("www.flash.com")
                .closeArray();

        //Response body
        PactDslJsonBody responseBody = (PactDslJsonBody) new PactDslJsonBody()
                .numberType("id", 99999)
                .stringValue("name", "Flash")
                .stringValue("status", "Pendente")
                .array("photoUrls")
                .stringValue("www.flash.com")
                .closeArray();

        return builder
                .usingLegacyDsl()
                .given("Pet existe")
                .uponReceiving("Adicionando novo Pet")
                .path("/pet")
                .method("POST")
                .headers("Content-Type", "application/json")
                .body(requestBody)
                .willRespondWith()
                .status(200)
                .body(responseBody)
                .toPact(V4Pact.class);
    }

    @Test
    void testAddPet(MockServer mockServer) {

        given()
                .baseUri(mockServer.getUrl())
                .header("Content-Type", "application/json")
                .body(Utils.lerArquivoJson("bodyAddPet.json").toString())
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("Flash"));
    }
}
