package base;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.Data;
import utils.PayLoadReader;

public class BaseTest extends PayLoadReader {
	String payload = null;
	protected RequestSpecification spec;
	String baseURI = null;
	String filepath = null;
	Response response = null;
	SoftAssert softAssert = new SoftAssert();
	JSONObject request = null;

	@BeforeMethod
	protected void setup() {
		filepath = System.getProperty("user.dir") + Data.getProperty("PayLoadRoot")
				+ Data.getProperty("PayLoadFilePath");
		switch (Data.getProperty("PayLoad").toUpperCase()) {
		case "JSON":
			payload = super.JSONread(filepath);
			request = new JSONObject(payload);
			break;
		case "XML":
			payload = super.XMLread(filepath);
			break;
		}
		baseURI = Data.getProperty("BaseURI");
		spec = new RequestSpecBuilder().setBaseUri(baseURI).build();

	}

	public Response POST(String url) {

		switch (Data.getProperty("PayLoad").toUpperCase()) {
		case "JSON":
			response = RestAssured.given().spec(spec).contentType(ContentType.JSON).body(payload).post(url);
			break;
		case "XML":
			response = RestAssured.given().spec(spec).contentType(ContentType.XML).body(payload).post(url);
			break;
		}
		return response;
	}

	public Response POST_Bearer(String url) {

		switch (Data.getProperty("PayLoad").toUpperCase()) {
		case "JSON":
			response = RestAssured.given().spec(spec).auth().oauth2(Data.getProperty("Token"))
					.contentType(ContentType.JSON).body(payload).post(url);
			break;
		case "XML":
			response = RestAssured.given().spec(spec).auth().oauth2(Data.getProperty("Token"))
					.contentType(ContentType.XML).body(payload).post(url);
			break;
		}
		return response;
	}

	public Response GET(String url) {
		response = RestAssured.given().spec(spec).get(url);
		return response;
	}

	public Response DELETE_Auth(String url) {
		response = RestAssured.given().spec(spec).auth().preemptive()
				.basic(Data.getAuthproperty("UserName"), Data.getAuthproperty("PassWord")).delete(url);
		return response;
	}

	public Response DELETE(String url) {
		response = RestAssured.given().spec(spec).delete(url);
		return response;
	}

	public Response PUT_Auth(String url) {
		response = RestAssured.given().spec(spec).auth().preemptive()
				.basic(Data.getAuthproperty("UserName"), Data.getAuthproperty("PassWord")).contentType(ContentType.JSON)
				.body(payload).put(url);
		return response;
	}

	public Response PATCH_Auth(String url) {
		response = RestAssured.given().spec(spec).auth().preemptive()
				.basic(Data.getAuthproperty("UserName"), Data.getAuthproperty("PassWord")).contentType(ContentType.JSON)
				.body(payload).patch(url);
		return response;
	}

	public void validateResponseCode(int code) {
		Assert.assertEquals(response.getStatusCode(), code, "Status code should be 200, but it's not");
	}

	public void validateResponseBody(String responseParam, String requestparam) {
		Object Actual = getDynamicValue(response.jsonPath().get(responseParam));
		Object Expected = getDynamicValue(request.get(requestparam));
		softAssert.assertEquals(Actual, Expected,
				requestparam.toUpperCase() + " should be " + Expected + ", but it's not");
	}
	
	public void validateNestedResponseBody(String responseParam, String requestparam) {
		String Actual = response.jsonPath().getString(responseParam);
		String items[] = requestparam.split("\\.");
		String Expected = request.getJSONObject(items[0]).getString(items[1]);
		softAssert.assertEquals(Actual, Expected,
				requestparam.toUpperCase() + " should be " + Expected + ", but it's not");
	}

	public Object getDynamicValue(Object value) {
		if (value instanceof Integer) {
			return (Integer) value;
		} else if (value instanceof Double) {
			return (Double) value;
		} else if (value instanceof String) {
			return (String) value;
		} else if (value instanceof Boolean) {
			return (Boolean) value;
		} else if (value instanceof JSONObject) {
			return (JSONObject) value;
		} else {
			System.out.println("Unexpected type: " + (value != null ? value.getClass() : "null"));
			return null;
		}

	}

	@AfterMethod
	public void validateAssertions() {
		softAssert.assertAll();
	}

}
