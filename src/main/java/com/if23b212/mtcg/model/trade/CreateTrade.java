package com.if23b212.mtcg.model.trade;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.if23b212.mtcg.model.card.CardType;

public class CreateTrade {
	@JsonProperty("Id")
	private UUID id;
	@JsonProperty("CardToTrade")
	private UUID cardToTrade;
	@JsonProperty("Type")
	private CardType type;
	@JsonProperty("MinimumDamage")
	private int minimumDamage;
	
	public CreateTrade() {
	}
	public CreateTrade(UUID id, UUID cardToTrade, CardType type, int minimumDamage) {
		this.id = id;
		this.cardToTrade = cardToTrade;
		this.type = type;
		this.minimumDamage = minimumDamage;
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public UUID getCardToTrade() {
		return cardToTrade;
	}
	public void setCardToTrade(UUID cardToTrade) {
		this.cardToTrade = cardToTrade;
	}
	public CardType getType() {
		return type;
	}
	public void setType(CardType type) {
		this.type = type;
	}
	public int getMinimumDamage() {
		return minimumDamage;
	}
	public void setMinimumDamage(int minimumDamage) {
		this.minimumDamage = minimumDamage;
	}
	
	
}
