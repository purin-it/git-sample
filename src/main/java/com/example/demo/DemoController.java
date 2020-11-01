package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import reactor.core.publisher.Flux;

@Controller
public class DemoController {
	
	@Value("${azure.api.baseUri}")
	private String apiBaseUri;
	
	@Value("${azure.api.key}")
	private String apiKey;

	@Autowired
	private UserRepository repository;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping
	public String index(Model model) {

		// ユーザーデータをDBから直接取得する
		Flux<User> firstNameUserFlux = repository.findByEmail("user3@gmail.com");
		User dbData = firstNameUserFlux.blockFirst();

		model.addAttribute("db_email", dbData.getEmail());
		model.addAttribute("db_location", dbData.getLocation());
		model.addAttribute("db_interests", dbData.getInterests());

		// ユーザーデータをAPIで取得する
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Ocp-Apim-Subscription-Key", apiKey);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<String> response = restTemplate.exchange(
				apiBaseUri + "/GetData?email=" + "user2@gmail.com", HttpMethod.GET, entity, String.class);
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			List<Map<String, String>> mapList 
				= mapper.readValue(response.getBody(), new TypeReference<List<Map<String, String>>>() {});
			model.addAttribute("api_email", mapList.get(0).get("email"));
			model.addAttribute("api_location", mapList.get(0).get("location"));
			model.addAttribute("api_interests", mapList.get(0).get("interests"));
			
		}catch(Exception ex) {
			System.err.println(ex);
		}

		return "index";
	}

}
