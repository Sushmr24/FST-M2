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
    static String SSHKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDFWMfyWQRuPnaa24ATt9DwnXyi2aD405PDJS0OUD0uXXXXXXXXXXXXXXXXXXXXXXX=";
    static int id ;
    RequestSpecification requestSpec ;

    @BeforeClass
    public void setup()
    {
        requestSpec =  new RequestSpecBuilder().setContentType(ContentType.JSON).addHeader("Authorization","token ghp_xxxxxxxxxxxxxxx").setBaseUri(baseURI).build();
    }


    @Test(priority=1)
    public void getEmail()
    {

        Response response =given().spec(requestSpec).get("/user/emails"); // Send GET request

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
