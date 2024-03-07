package com.if23b212.mtcg.model.card;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CardData {
	@JsonProperty("Id")
    private UUID id;
	@JsonProperty("Name")
    private String name;	
	@JsonProperty("Damage")
    private double damage;
    
    public CardData() {}

    
	public CardData(UUID id, String name, double damage) {
		this.id = id;
		this.name = name;
		this.damage = damage;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}
    
	
    
}
