package specs;


import config.ApiConfig;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.aeonbits.owner.ConfigFactory;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class Specs {
    private static ApiConfig config = ConfigFactory.create(ApiConfig.class);

    public static RequestSpecification reqresRequest = with()
            .baseUri(config.baseApiUri())
            .basePath("/api")
            .log().all()
            .filter(withCustomTemplates())
            .contentType(ContentType.JSON);

    public static ResponseSpecification response = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification responseCreate = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .build();

    public static ResponseSpecification responseDelete = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .build();

    public static ResponseSpecification responseFailed = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .build();

    public static ResponseSpecification responseNotFound = new ResponseSpecBuilder()
            .expectStatusCode(404)
            .build();
}
