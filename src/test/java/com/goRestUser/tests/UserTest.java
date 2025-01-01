package com.goRestUser.tests;

import org.testng.annotations.Test;

import base.BaseTest;
import io.restassured.response.Response;
import utils.Data;

public class UserTest extends BaseTest {
	
	@Test
	public void createUser() {
		Response response = POST_Bearer(Data.getProperty("URL"));
		response.prettyPrint();
	}

}
