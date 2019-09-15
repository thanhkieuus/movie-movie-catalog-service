package com.vk.moviecatalog.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vk.moviecatalog.models.CatalogItem;
import com.vk.moviecatalog.models.UserRating;
import com.vk.moviecatalog.services.MovieInfo;
import com.vk.moviecatalog.services.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCalalogResource {
	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;
	
	private static final Logger logger = LoggerFactory.getLogger(MovieCalalogResource.class);
	
	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		
		logger.info("userId: {}", userId);
		
		UserRating ratings = userRatingInfo.getUserRating(userId);
		
		return ratings.getRatings().stream()
				.map(rating -> movieInfo.getCatalogItem(rating))
				.collect(Collectors.toList());		
	}


}
