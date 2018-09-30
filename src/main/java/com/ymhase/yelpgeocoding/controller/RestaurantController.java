package com.ymhase.yelpgeocoding.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ymhase.yelpgeocoding.dto.RestaurantName;
import com.ymhase.yelpgeocoding.models.LocationCoordinateModel;
import com.ymhase.yelpgeocoding.utils.GeocodingUtils;

@RestController
public class RestaurantController {

	@RequestMapping(method = RequestMethod.POST, value = "/restaurantlist")
	public void login(@RequestBody RestaurantName restaurantName) throws Exception {

		LocationCoordinateModel locCordinate = GeocodingUtils
				.getLocationCordinateFromAddress(restaurantName.getLocationName());

		System.out.println(">>>" + locCordinate.getLangitude());
		System.out.println(">>>" + locCordinate.getLattitude());

	}
}
