package com.all.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.testng.Assert;

import com.response.pojos.BookRoomResponse;
import com.travelport.hotel.Base;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;


public class CommnUtils {
	public static String getTomorrowDate() {
		  Calendar calendar = Calendar.getInstance();
		  SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		  calendar.add(Calendar.DAY_OF_YEAR, 1);
		  Date date = calendar.getTime();
		  String tomorrow_Date = dateformat.format(date);
		  return tomorrow_Date;
  
	}
	public static void validateBookRoomPostResponsObjFieldsAvailability(Response response) {
		String bodyAsString = response.getBody().asString();
		Assert.assertEquals(bodyAsString.contains("checkInDate"), true, "Verifying BookRoomResponsObj response contains checkInDate field");
		Assert.assertEquals(bodyAsString.contains("checkOutDate"), true, "Verifying BookRoomResponsObj response contains checkOutDate field");
		Assert.assertEquals(bodyAsString.contains("totalPrice"), true, "Verifying BookRoomResponsObj response contains totalPrice field");
	}
	
	public static void validateBookRoomPostResponsObj(Response response) {
		String bodyAsString = response.getBody().asString();
		Assert.assertEquals(bodyAsString.contains("checkInDate"), true, "Verifying BookRoomResponsObj response contains checkInDate field");
		Assert.assertEquals(bodyAsString.contains("checkOutDate"), true, "Verifying BookRoomResponsObj response contains checkOutDate field");
		Assert.assertEquals(bodyAsString.contains("totalPrice"), true, "Verifying BookRoomResponsObj response contains totalPrice field");
		Base.printResponse(response);
		ResponseBody body = response.getBody();
		BookRoomResponse roomResponse = body.as(BookRoomResponse.class);
		System.out.println("Room amount="+roomResponse.getTotalPrice()+"       "+roomResponse.getCheckOutDate());
		String[] inDatesplitter=roomResponse.getCheckInDate().split("-");
		String[] outDatesplitter=roomResponse.getCheckOutDate().split("-");
		int inDate[] = new int[3];
		int outDate[] = new int[3];
		for(int i=0; i<3; i++) {
			inDate[i] = Integer.parseInt(inDatesplitter[i]);
			outDate[i] = Integer.parseInt(outDatesplitter[i]);
	      }
		if((inDate[0] == outDate[0]) && (inDate[1] == outDate[1]))
		{
			Assert.assertEquals((inDate[1] > outDate[1]), true, "Checkout Date is incorrect in Response object..");
		}
		Assert.assertEquals(roomResponse.getTotalPrice()>0, true, "Validating Total price value should be a positive number:"+roomResponse.getTotalPrice());
	}

}

