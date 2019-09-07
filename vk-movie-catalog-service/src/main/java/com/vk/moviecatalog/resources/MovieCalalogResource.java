package com.vk.moviecatalog.resources;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vk.moviecatalog.models.CatalogItem;

@RestController
@RequestMapping("/catalog")
public class MovieCalalogResource {
	
	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		
		return Collections.singletonList(
				new CatalogItem("Transformer", "Des_Transformer", 4)
		);
	}

}
