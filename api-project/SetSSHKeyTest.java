import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.InputStream;

import static io.restassured.RestAssured.given;

public class SetSSHKeyTest {
    // Set base URL
    final static String baseURI = "https://api.github.com";
    static String SSHKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDFWMfyWQRuPnaa24ATt9DwnXyi2aD405PDJS0OUD0uKoPojTVsxRyb0VygIoIWgRXSzMZlEasQNiAhb8FL6wXLjIqWODYHUQr5KMeEYiy55X1DhkgZw3MQmvEM/2Y8IBTXwq6zn9iZW0jaVX/0c/l8W068QkLAG6Jm0tyniYP/w9oHQs4BSjUzVUEH3XC8CX+CTVfgTUUy+e7pNTE04E3DUfCqAMJn9D9rIcLibnlia2Dg5URK7iRcXRPrdBTe/+x7yBvH5K/4Nn9CC4eqKKDhZ7Ee+40rYRUqfVZncgCYwM2kOJ3a051C+D6sJoZop+ohxRHUJDdgDuyck/SC8+aadt2YVMJMB3L+iUdN+ScQ51QvrnHpJCXZeATHBAxFUcfDTChsfder4XCJqpMwsYEFx6JaKowFlbi3Cx2F/uRUpgOAlT+oR0ZQfsvtQSmQ/6tq6kTLoD8Mv9/9XUyouZoFcSJgNURtJTCwonzf+kaMWd6PPjEKR1/uESYzXkrrtG8=";
    static int id ;
    RequestSpecification requestSpec ;

    @BeforeClass
    public void setup()
    {
        requestSpec =  new RequestSpecBuilder().setContentType(ContentType.JSON).addHeader("Authorization","token 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").setBaseUri(baseURI).build();
    }


    @Test(priority=1)
    public void getSshkey()
    {

        Response response =given().spec(requestSpec).get("/user/keys"); // Send GET request

        // Print response
        String body = response.getBody().asPrettyString();
        System.out.println(body);

    }

    @Test(priority=2)
    public void addSshKey()
    {
        String reqBody = "{"
                + "\"title\": \"TestAPIKey\","
                + "\"key\": \""+SSHKey +"\""
                + "}";

        Response response =given().spec(requestSpec).body(reqBody).post("/user/keys");

        // Print response
        String body = response.getBody().asPrettyString();
        System.out.println(body);

        JsonPath jsonPath =  JsonPath.from(body);
        id = jsonPath.get("id");
        System.out.println(" id is : "+ id);

    }

    @Test(priority=3)
    public void deleteKey()
    {
        //id = 66576914;
        Response response =given().spec(requestSpec).pathParams("keyId", id).delete("/user/keys/{keyId}");

        // Print response
        String body = response.getBody().asPrettyString();
        System.out.println(body);
    }

}
