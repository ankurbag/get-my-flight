package com.neu.getmyflight.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.neu.getmyflight.Flight;
import com.neu.getmyflight.dao.ConnectionDao;
import com.neu.getmyflight.request.Request;
import com.neu.getmyflight.request.TravelDateParameter;
import com.neu.getmyflight.request.TravelDateRequest;
import com.neu.getmyflight.response.Response;
import com.neu.getmyflight.response.TravelOutput;




/**
 * Handles requests for the application home page.
 */
@Controller
public class FlightController {
	
	private static final Logger logger = LoggerFactory.getLogger(FlightController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/flight**", method = RequestMethod.POST)
	public void getFlights(HttpServletRequest request,HttpServletResponse response, @RequestBody String flightRequest){
		System.out.println("flights "+flightRequest);
		logger.info("flights "+flightRequest);
		Gson gson = new Gson();
		Connection conn = ConnectionDao.getConnection(); 
		Request flightRequest2 = gson.fromJson(flightRequest, Request.class);
		if(flightRequest2.getResult().getMetadata().getIntentName()!= null &&
				flightRequest2.getResult().getMetadata().getIntentName().equalsIgnoreCase("traveldate")){
			System.out.println("flightPreference for travel Request ");
			TravelDateRequest travelDateRequest = gson.fromJson(flightRequest, TravelDateRequest.class);
			if(travelDateRequest.getResult()!=null){
				TravelDateParameter travelDateParameter = travelDateRequest.getResult().getParameters();
				if(travelDateParameter!=null){
					String travelDate = travelDateParameter.getTravelDate();
					String source =  travelDateParameter.getSource();
					String destination = travelDateParameter.getDestination();
					System.out.println("travelDateParameter  travelDate "
							+travelDate);
					System.out.println("travelDateParameter  source "
							+travelDate);
					System.out.println("travelDateParameter  destination "
							+destination);
					//Sys
					//Calculate Today's Price
					Random r = new Random();
					double todaysPrice = 100 + (400 - 100) * r.nextDouble();
					//Date of Travel
					String travelDateParts[] = travelDate.split("-");
					//Today's Date
					DateFormat dateFormat = new SimpleDateFormat("MM/dd");
					Date date = new Date();
					System.out.println(dateFormat.format(date));
					String todayDateParts[] = dateFormat.format(date).split("/");
					
					String params[] = new String[]{travelDateParts[1],travelDateParts[2],todayDateParts[0],todayDateParts[1],"%"+source+"%","%"+destination+"%",String.valueOf(todaysPrice)};
					
					TravelOutput output = 
							ConnectionDao.getFlightOnDateOfTravel(conn, params);
					
					
					/*output.setActualPrice("230");
					output.setCarrier("Delta Airways");
					output.setDateOfPriceFall("04/4/2017");
					output.setDateOfTravel(travelDate);
					output.setPredictedPrice("200");*/
					
					Response response2 = new Response();
					response2.setData(output);
					response2.setSpeech("Hi");
					response2.setDisplayText("Hi");
					response2.setSource("Data");
					response2.setContextOut(new ArrayList<String>());
					String json = gson.toJson(response2);
					System.out.println("Response "
							+json);
					response.setHeader("Content-type", "application/json");
					try {
						response.getWriter().write(json);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	
}
