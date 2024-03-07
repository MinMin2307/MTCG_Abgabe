package com.if23b212.mtcg.service.card;

import java.util.List;
import java.util.UUID;

import com.if23b212.mtcg.db.card.CardRepository;
import com.if23b212.mtcg.db.card.DeckRepository;
import com.if23b212.mtcg.db.user.UserRepository;
import com.if23b212.mtcg.model.card.Card;
import com.if23b212.mtcg.model.card.Deck;
import com.if23b212.mtcg.model.user.User;

public class DeckService {
    UserRepository userRepo = new UserRepository();
    CardRepository cardRepo = new CardRepository();
    DeckRepository deckRepo = new DeckRepository();
    
    public void configureDeck(String username, List<UUID> cardIds) throws Exception {
    	if(cardIds == null || cardIds.size() != 4) {
    		throw new Exception("Wrong amount of cards for a deck");
    	} else {
    		if(username == null || username.length() == 0) {
        		throw new Exception("Invalid user");
    		}
    		User user = userRepo.getUser(username);
    		for(UUID cardId : cardIds) {
        		deckRepo.addCardToDeck(cardId, user.getId());
    		}
    	}
    }
	
	public Deck getDeck(String username) throws Exception {
		User user = userRepo.getUser(username);
		List<Card> cards = deckRepo.getUserDeckCards(user.getId());
		if(cards != null) {
			return new Deck(cards);
		}
		return null;
	}

}
