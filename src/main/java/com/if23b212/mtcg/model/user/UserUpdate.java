package com.if23b212.mtcg.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserUpdate {
    //Mappes variable to json attribute
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Bio")
    private String bio;
    @JsonProperty("Image")
    private String image;
	public UserUpdate() {
	}

	public UserUpdate(String name, String bio, String image) {
		this.name = name;
		this.bio = bio;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
