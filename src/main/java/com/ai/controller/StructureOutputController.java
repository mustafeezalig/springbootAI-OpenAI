package com.ai.controller;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.model.CountryCities;

@RestController
@RequestMapping("/api")
public class StructureOutputController {

	private ChatClient chatClient;

	public StructureOutputController(@Qualifier("openai") ChatClient chatClientBuilder) {
		this.chatClient = chatClientBuilder;
	}

	@GetMapping("/country")
	public ResponseEntity<CountryCities> getCountryCitiesList(@RequestParam("message") String message) {

		CountryCities countryCities = chatClient.prompt().user(message).call().entity(CountryCities.class);

		return ResponseEntity.ok(countryCities);

	}
	
	

	@GetMapping("/cities")
	public ResponseEntity<List<String>> getCitiesList(@RequestParam("message") String message) {

		List<String> listCities = chatClient.prompt().user(message).call().entity(new ListOutputConverter());

		return ResponseEntity.ok(listCities);

	}
	
	@GetMapping("/country-cities")
	public ResponseEntity<List<CountryCities>> getCountryCities(@RequestParam("message") String message) {

		List<CountryCities> listCities = chatClient.prompt().user(message).call().entity(new ParameterizedTypeReference<List<CountryCities>>() {
		});

		return ResponseEntity.ok(listCities);

	}
	
	@GetMapping("/map")
	public ResponseEntity<Map<String,Object>> getCitiesMap(@RequestParam("message") String message) {

		Map<String,Object> mapCities = chatClient.prompt().user(message).call().entity(new MapOutputConverter());

		return ResponseEntity.ok(mapCities);

	}

}
