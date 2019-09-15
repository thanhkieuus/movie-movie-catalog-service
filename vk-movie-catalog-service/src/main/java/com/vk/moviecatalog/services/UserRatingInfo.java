package com.vk.moviecatalog.services;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.vk.moviecatalog.models.Rating;
import com.vk.moviecatalog.models.UserRating;

@Service
public class UserRatingInfo {

	private static final Logger logger = LoggerFactory.getLogger(UserRatingInfo.class);

	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackUserRating")
	public UserRating getUserRating(String userId) {
		logger.debug("userId: {}", userId);
		return restTemplate.getForObject("http://vk-ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
	}

	public UserRating getFallbackUserRating(String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setRatings(Arrays.asList(new Rating("0", 0)));
		return userRating;
	}
}
