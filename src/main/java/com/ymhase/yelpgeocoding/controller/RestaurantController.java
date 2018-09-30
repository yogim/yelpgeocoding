package com.ymhase.yelpgeocoding.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ymhase.yelpgeocoding.dto.RestaurantName;

@RestController
public class RestaurantController {

	@RequestMapping(method = RequestMethod.POST, value = "/restaurantlist")
	public void login(@RequestBody RestaurantName restaurantName) throws Exception {

		String address = restaurantName.getLocationName().replaceAll(" ", "+");

		try {

			URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + address
					+ "&key=AIzaSyAoBE4q6qE4HApq84aMZF6leAg0jdAClj8");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			/*String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}*/
			
			
			StringBuilder sb = new StringBuilder();
		    String line;
		    while ((line = br.readLine()) != null) {
		        sb.append(line+"\n");
		    }
		    br.close();
		    //sb.toString();
		    JSONObject jsonObj = new JSONObject(sb.toString());
		    Object lat =  jsonObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat");
		    Object lng =  jsonObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng");
		    
		    
		    //jsonObj.get(results[0].geometry.location.lat);
		    
		    System.out.println(lat.toString());
		    System.out.println("LONNNNNN"+lng.toString());
		    System.out.println("++++++++++++++");
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
