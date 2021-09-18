package com.travelport.hotel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;

import com.all.utils.CommnUtils;
import com.response.pojos.RoomAvailable;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Base {
	public static String BASE_URI = "http://localhost:9090/";
	public static final String APPLICATION_JSON = "application/json";
	public static Logger logger = LogManager.getLogger("ApiMessage");

	public static Response response;
	public static RequestSpecification request;


	public static void getCheckAvailability() {
		/*
		 http://localhost:9090/checkAvailability/2021-09-19
{
    "date": "2021-09-19",
    "rooms_available": 10,
    "price": 120
}
		 
		 */
		System.out.println(" starting");
		RestAssured.baseURI = BASE_URI;
		RequestSpecification request = RestAssured.given();
		String apiURL = BASE_URI + "/checkAvailability/{availdate}";
		String tomorrow = CommnUtils.getTomorrowDate();
		request.pathParam("availdate", tomorrow);
		response = request.get(apiURL);
		printResponse(response);
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}
	
	public Response getCheckAvailability(String dateParam) {
		RestAssured.baseURI = "http://localhost:9090";
		RequestSpecification request = RestAssured.given();
		//String tomorrow = CommnUtils.getTomorrowDate();
		String pathURL = "/checkAvailability/"+dateParam;
		Response response = request.get(pathURL);
		//Assert.assertEquals(response.getStatusCode(), 200);
		//CommnUtils.validatAvailabilityResponseContents(response);
		return response;
	}
	
	public void validatAvailabilityResponseObjContents(Response response) {
		
		String bodyAsString = response.getBody().asString();
		Assert.assertEquals(bodyAsString.contains("date"), true, "Verifying response contains date field");
		Assert.assertEquals(bodyAsString.contains("rooms_available"), true, "Verifying response contains rooms_available field");
		Assert.assertEquals(bodyAsString.contains("price"), true, "Verifying response contains price field");
	}
	
	public void validatAvailabilityResponseObjFieldValues(Response response) {
		RoomAvailable respObj = response.getBody().as(RoomAvailable.class);
		Assert.assertEquals(respObj.getPrice()>0, true, "Price field should be positive amount..");
		Assert.assertEquals(respObj.getRooms_available()>=0, true, "Rooms_available field should be greater or Zero.."+respObj.getRooms_available());
	}
	
	public Response getPostRespForBookARoomReq(JSONObject requestParams) {
		RestAssured.baseURI = "http://localhost:9090";
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		
		//JSONObject requestParams = new JSONObject();
		requestParams.put("numOfDays", 4); 
		requestParams.put("checkInDate", CommnUtils.getTomorrowDate());
		request.body(requestParams.toJSONString());
		
		requestParams.get("numOfDays");
		requestParams.get("checkInDate");
		Response response = request.post("/bookRoom");
		return response;
	}
	
	public static void printResponse(Response response) {
		// response = getCountryAndCitiesAsDestShipol();
		String jsonString = response.asPrettyString();
    	System.out.println(response.getStatusCode());
		System.out.println("Respons String: " + jsonString);
	}
	
}
