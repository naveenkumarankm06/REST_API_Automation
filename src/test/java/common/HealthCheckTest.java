package common;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import base.BaseTest;

public class HealthCheckTest extends BaseTest {

	@Test
	public void healthCheck() {
		given().spec(spec).when().get("/ping").then().assertThat().statusCode(201);
	}

}
