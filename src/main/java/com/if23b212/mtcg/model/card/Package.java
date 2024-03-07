package com.if23b212.mtcg.model.card;

import java.util.List;
import java.util.UUID;

public class Package {
    private UUID id;
    private boolean available;
    
    private List<Card> cards;

    public Package(UUID id, List<Card> cards) {
        if (cards.size() != 5) {
            throw new IllegalArgumentException("A package must contain exactly 5 cards.");
        }
        this.id = id;
        this.cards = cards;
    }

    public Package(UUID id, boolean available, List<Card> cards) {
        if (cards.size() != 5) {
            throw new IllegalArgumentException("A package must contain exactly 5 cards.");
        }
		this.id = id;
		this.available = available;
		this.cards = cards;
	}

	public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
    
}
