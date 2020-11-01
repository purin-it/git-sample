package com.example.demo;

import com.microsoft.azure.spring.data.cosmosdb.repository.ReactiveCosmosRepository;

import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveCosmosRepository<User, String> {

	Flux<User> findByEmail(String email);
	
}
