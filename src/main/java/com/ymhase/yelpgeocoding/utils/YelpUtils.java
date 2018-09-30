package com.ymhase.yelpgeocoding.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ymhase.yelpgeocoding.models.LocationCoordinateModel;

public class YelpUtils {

	public static JSONObject getResturantList(LocationCoordinateModel locationCoordinateModel) {
		
		try {

			URL url = new URL("https://api.yelp.com/v3/businesses/search?term=food&latitude="+locationCoordinateModel.getLatitude()+"&longitude="+locationCoordinateModel.getLongitude());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Authorization", "Bearer UQu9Oh_1NYwvRgDoSZCFQSGx1b8OQTdNgsGEQQvaQqHICkYAJj1PtuxaGJTnzPC-g2vj1rwu6uQ2Ci5hU4MffG1xqqiRKssPj329IlD4hQgM556e_T8z65yYuiqwW3Yx");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			System.out.println(sb.toString());
			br.close();
			
			JSONObject jsonObj = new JSONObject(sb.toString());
			JSONArray business = jsonObj.getJSONArray("businesses");
			
			String name, title, address1, city, country, state, zip_code; 
			JSONObject category, coordinates, location;
			JSONArray categories;
			double latitude, longitude, distance, rating;
			
			for(int i=0; i<business.length(); i++) {
				JSONObject jObj = business.getJSONObject(i);
				name= (String) jObj.get("name");
				rating = (double) jObj.get("rating"); 
				categories = jObj.getJSONArray("categories");
				category = categories.getJSONObject(0);
				title = (String) category.get("title");
				coordinates = jObj.getJSONObject("coordinates");
				latitude = (double) coordinates.get("latitude");
				longitude = (double) coordinates.get("longitude");
				location = jObj.getJSONObject("location");
				address1 = (String) location.get("address1");
				city = (String) location.get("city");
				country = (String) location.get("country");
				state = (String) location.get("state");
				zip_code = (String) location.get("zip_code");
				distance  = (double) jObj.get("distance");
				
				System.out.println("Resturant Number : "+(i+1));
				System.out.println("Name : "+name);
				System.out.println("Category : "+title);
				System.out.println("Rating : "+rating);
				System.out.println("Adress : "+address1+" "+city+" "+country+" "+state+" "+zip_code);
				System.out.println("Latitude : "+latitude+",  Longitude : "+longitude);
				System.out.println("Distance : "+distance+" meters");
				System.out.println("\n\n");

				
			}
			
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return null;
	}
}
