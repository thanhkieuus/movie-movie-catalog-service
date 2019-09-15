package com.vk.moviecatalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.vk.moviecatalog.models.CatalogItem;
import com.vk.moviecatalog.models.Movie;
import com.vk.moviecatalog.models.Rating;

@Service
public class MovieInfo {

	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://vk-movie-info-service/movies/" + rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getMovieId(), movie.getName(), movie.getDesc(), rating.getRating());
	}

	public CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem(rating.getMovieId(), "Movie name not found", "",  rating.getRating());
	}

}
