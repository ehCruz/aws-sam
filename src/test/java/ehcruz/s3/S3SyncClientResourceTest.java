package ehcruz.s3;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;

import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(S3ServiceResource.class)
public class S3SyncClientResourceTest {

    private static final String OBJECT_KEY = "key-test";
    private static final String OBJECT_DATA = "data-test";

    @Test
    public void testHelloEndpoint() {
        String json = Json.createObjectBuilder()
                .add("key", OBJECT_KEY)
                .add("content", OBJECT_DATA)
                .build()
                .toString();

        // upload
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(json)
                .when()
                .post("/s3")
                .then()
                .assertThat()
                .statusCode(201);
    }

}