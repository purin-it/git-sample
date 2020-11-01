package com.example.demo;

import com.microsoft.azure.spring.data.cosmosdb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "ascosmosdbcontainer")
public class User {

    @Id
    private String id;
    
    private String email;
    
    private String location;
    
    private String interests;

    public User(String id, String email, String location, String interests) {
        this.id = id;
        this.email = email;
        this.location = location;
        this.interests = interests;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	@Override
    public String toString() {
        return String.format("%s %s, %s", email, location, interests);
    }
}
