package com.ymhase.yelpgeocoding.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import com.ymhase.yelpgeocoding.models.LocationCoordinateModel;

public class GeocodingUtils {

	public static LocationCoordinateModel getLocationCordinateFromAddress(String address) {

		address = address.replaceAll(" ", "+");
		
		LocationCoordinateModel locCordinateModel = new LocationCoordinateModel();

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

			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			JSONObject jsonObj = new JSONObject(sb.toString());
			double lat = (double)jsonObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry")
					.getJSONObject("location").get("lat");
			double lng = (double)jsonObj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry")
					.getJSONObject("location").get("lng");

			locCordinateModel.setLangitude(lat);
			locCordinateModel.setLattitude(lng);
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return locCordinateModel;
	}

}
