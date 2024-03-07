package com.if23b212.mtcg.model.card;

import java.util.UUID;


public class Card {

    private UUID id;

    private String name;

    private double damage;

    private CardType cardType;

    private ElementType elementType;

    private UUID userId;

    private UUID packageId;

	public Card(UUID id, String name, double damage, CardType cardType, ElementType elementType,
			UUID userId, UUID packageId) {
		this.id = id;
		this.name = name;
		this.damage = damage;
		this.cardType = cardType;
		this.elementType = elementType;
		this.userId = userId;
		this.packageId = packageId;
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

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public ElementType getElementType() {
		return elementType;
	}

	public void setElementType(ElementType elementType) {
		this.elementType = elementType;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public UUID getPackageId() {
		return packageId;
	}

	public void setPackageId(UUID packageId) {
		this.packageId = packageId;
	}

	@Override
	public String toString() {
		return "Card [id=" + id + ", name=" + name + ", damage=" + damage + ", cardType=" + cardType.name() + ", elementType="
				+ elementType.name() + ", userId=" + userId + ", packageId=" + packageId + "]";
	}
   
    
}
