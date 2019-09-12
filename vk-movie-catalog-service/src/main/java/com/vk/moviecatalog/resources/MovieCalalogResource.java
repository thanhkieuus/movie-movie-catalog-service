package com.vk.moviecatalog.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.vk.moviecatalog.models.CatalogItem;
import com.vk.moviecatalog.models.Movie;
import com.vk.moviecatalog.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCalalogResource {
	
	@Autowired
	RestTemplate restTemplate;

	/* 
	 * @Autowired 
	 * WebClient.Builder webClientBuilder;
	 * 
	 */
	
	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		
		UserRating ratings = restTemplate.getForObject("http://vk-ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
		
		return ratings.getRatings().stream().map( rating -> {
			Movie movie = restTemplate.getForObject("http://vk-movie-info-service/movies/" + rating.getMovieId(), Movie.class);
			
			/* Example of using WebClient
			Movie movie = webClientBuilder.build()
					.get()
					.uri("http://localhost:8082/movies/" + rating.getMovieId())
					.retrieve()
					.bodyToMono(Movie.class)
					.block();
			*/
			
			return new CatalogItem(movie.getName(), "Des_" + rating.getMovieId(), rating.getRating());
		}).collect(Collectors.toList());
		
	}

}
