package com.hotelbooking.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import io.restassured.response.Response;
import utils.Data;

public class BookingTest extends BaseTest {
	int bookingid;
	@Test(priority = 1)
	public void createBookingId() {
		Response response = POST(Data.getProperty("URL"));
		response.prettyPrint();
		validateResponseCode(200);
		bookingid = response.jsonPath().getInt("bookingid");
		validateResponseBody("booking.firstname","firstname");
		validateResponseBody("booking.lastname","lastname");
		validateResponseBody("booking.totalprice","totalprice");
		validateResponseBody("booking.depositpaid","depositpaid");
		validateNestedResponseBody("booking.bookingdates.checkin","bookingdates.checkin");
		validateNestedResponseBody("booking.bookingdates.checkout","bookingdates.checkout");
		validateResponseBody("booking.additionalneeds","additionalneeds");
		System.out.println("Booking id : "+bookingid);
		Data.setProperty("BookingId", Integer.toString(bookingid));
	}
	
	@Test (priority = 2)
	public void getBookingId() {
		Response response = GET(Data.getProperty("URL")+"/"+Data.getProperty("BookingId"));
		response.prettyPrint();
		validateResponseCode(200);
		validateResponseBody("firstname","firstname");
		validateResponseBody("lastname","lastname");
		validateResponseBody("totalprice","totalprice");
		validateResponseBody("depositpaid","depositpaid");
		validateNestedResponseBody("booking.bookingdates.checkin","bookingdates.checkin");
		validateNestedResponseBody("booking.bookingdates.checkout","bookingdates.checkout");
		validateResponseBody("additionalneeds","additionalneeds");
	}
	
	@Test(priority=3)
	public void deleteBookingId() {
		Response response = DELETE_Auth(Data.getProperty("URL")+"/"+Data.getProperty("BookingId"));
		response.prettyPrint();
		validateResponseCode(201);
		response = GET(Data.getProperty("URL")+"/"+Data.getProperty("BookingId"));
		response.prettyPrint();
		Assert.assertEquals(response.getBody().asString(), "Not Found", "Body should be 'Not Found', but it's not.");
	}
	
	@Test(enabled = false)
	public void updateBookingId() {
		Response response = GET(Data.getProperty("URL")+"/"+Data.getProperty("BookingId"));
		response.prettyPrint();
		response = PUT_Auth(Data.getProperty("URL")+"/"+Data.getProperty("BookingId"));
		response.prettyPrint();
		validateResponseCode(200);
		validateResponseBody("firstname","firstname");
		validateResponseBody("lastname","lastname");
		validateResponseBody("totalprice","totalprice");
		validateResponseBody("depositpaid","depositpaid");
		validateNestedResponseBody("booking.bookingdates.checkin","bookingdates.checkin");
		validateNestedResponseBody("booking.bookingdates.checkout","bookingdates.checkout");
		validateResponseBody("additionalneeds","additionalneeds");
	}

	@Test(enabled = false)
	public void partialUpdateBookingId() {
		Response response = GET(Data.getProperty("URL")+"/"+Data.getProperty("BookingId"));
		response.prettyPrint();
		response = PATCH_Auth(Data.getProperty("URL")+"/"+Data.getProperty("BookingId"));
		response.prettyPrint();
		validateResponseCode(200);
		validateResponseBody("firstname","firstname");
		validateNestedResponseBody("booking.bookingdates.checkin","bookingdates.checkin");
		validateNestedResponseBody("booking.bookingdates.checkout","bookingdates.checkout");
	}

}
