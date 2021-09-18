package com.travelport.hotel;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.all.utils.CommnUtils;
import io.restassured.response.Response;


public class RequestRouter extends Base {
	
	@Test(description="This method sent a GET req, and verify the response.")
	public void testGetRequestForRoomAvailability()
	{
		String validDate = CommnUtils.getTomorrowDate();
		Response response =getCheckAvailability(validDate);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(description="This method verify GET req's response field contents")
	public void testValidateGetRequestFieldsContents()
	{
		String validDate = CommnUtils.getTomorrowDate();
		Response response =getCheckAvailability(validDate);
		Assert.assertEquals(response.getStatusCode(), 200);
		validatAvailabilityResponseObjContents(response);
	}
	
	@Test(description="This method validate Values of Response Object")
	public void testValidateGetRequestFieldsValuess()
	{
		String tomorrow = CommnUtils.getTomorrowDate();
		Response response =getCheckAvailability(tomorrow);
		Assert.assertEquals(response.getStatusCode(), 200);
		validatAvailabilityResponseObjFieldValues(response);
	}
	
	@Test(description="This method sent a GET req with Previous day dates..")
	public void testValidateGetRequestWithPastDate()
	{
		String previousInvaliddate = "2010-09-20";
		Response response =getCheckAvailability(previousInvaliddate);
		Assert.assertEquals(response.getStatusCode(), 400, "As it is a previous date, it should get an error msg..");
	}
	
	@Test(description="This method verifis with a invalid date ....")
	public void testValidateGetRequestWithANonValidStringDate()
	{
		String previousInvaliddate = "INVALIDDATE";
		Response response =getCheckAvailability(previousInvaliddate);
		Assert.assertEquals(response.getStatusCode(), 400, "As it is an invalid date, it should get an error msg..");
	}

	@Test(description="This method sent a POST req, and verify the response.")
	public void testPostReqForBookARoom()
	{		
		JSONObject requestParams = new JSONObject();
		requestParams.put("numOfDays", 4); 
		requestParams.put("checkInDate", CommnUtils.getTomorrowDate());
		Response response = getPostRespForBookARoomReq(requestParams);
		Assert.assertEquals(response.getStatusCode(), 200, "Veryfying POST for bookRoom request... ");
	}
	
	@Test(description="This method sent a POST req, and verify response contents.")
	public void testBookARoomPostResponseObjFieldValidation2() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("numOfDays", 15); 
		requestParams.put("checkInDate", CommnUtils.getTomorrowDate());
		Response response = getPostRespForBookARoomReq(requestParams);
		CommnUtils.validateBookRoomPostResponsObjFieldsAvailability(response);
	}
	@Test
	public void testBookARoomPostResponseObjValidation() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("numOfDays", 15); 
		requestParams.put("checkInDate", CommnUtils.getTomorrowDate());
		Response response = getPostRespForBookARoomReq(requestParams);
		CommnUtils.validateBookRoomPostResponsObj(response);
	}
	
	@Test
	public void testBookRoomPostWithMoreDay() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("numOfDays", 15); 
		requestParams.put("checkInDate", CommnUtils.getTomorrowDate());
		Response response = getPostRespForBookARoomReq(requestParams);
		Assert.assertEquals(response.getStatusCode(), 200, "Veryfying POST for bookRoom request... ");
		CommnUtils.validateBookRoomPostResponsObjFieldsAvailability(response);
	}
	
	@Test
	public void testBookRoomPostWithInvalidDate() {
		JSONObject requestParams = new JSONObject();
		requestParams.put("numOfDays", 15); 
		requestParams.put("checkInDate", "INVALIDDATE");
		Response response = getPostRespForBookARoomReq(requestParams);
		Assert.assertEquals(response.getStatusCode(), 400, "Veryfying POST With Invalid Date... ");
	}
	
	
}
