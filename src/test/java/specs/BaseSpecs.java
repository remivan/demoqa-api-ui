package specs;


import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;


public class BaseSpecs {

    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().uri()
            .log().body()
            .log().headers();

    public static RequestSpecification requestBookSpec(String token) {
        return with()
                .filter(withCustomTemplates())
                .header("Authorization", "Bearer " + token)
                .log().uri()
                .log().body()
                .log().method()
                .log().headers()
                .contentType(JSON);
    }


    public static ResponseSpecification responseSpecification(int expectedStatusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatusCode)
                .log(LogDetail.ALL)
                .build();
    }

}
