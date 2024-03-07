package com.if23b212.mtcg.model.card;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Stack {
    private List<Card> cards;

    public Stack() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public void removeCard(UUID id) {
        cards = cards.stream().filter(card -> !card.getId().equals(id)).collect(Collectors.toList());
    }

    public List<Card> getCards() {
        return cards;
    }
}
