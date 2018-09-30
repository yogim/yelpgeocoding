package com.ymhase.yelpgeocoding.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Demo {

	public static void main(String[] args) throws Exception {
		URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=600 langsdorf drive fullerton&key=AIzaSyBsg3DmhAle-_G9X3Kh6Mgcqt_BlVldKmk");
			
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();

	}

}
