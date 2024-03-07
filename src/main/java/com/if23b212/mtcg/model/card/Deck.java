package com.if23b212.mtcg.model.card;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public Deck(List<Card> cards) {
        if (cards.size() != 4) {
            throw new IllegalArgumentException("A deck must contain exactly 4 cards.");
        }
        this.cards = cards;
    }

    public void addCard(Card card) {
        if(this.cards == null)
            cards = new ArrayList<>();
        if(cards.size() < 4) {
            cards.add(card);
        } else {
//            throw new IllegalArgumentException("Deck is already full.");
        }
    }

    public List<Card> getCards() {
        return cards;
    }

	@Override
	public String toString() {
		return "Deck [cards=" + cards + "]";
	}
    
    
}
