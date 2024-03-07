package com.if23b212.mtcg.service.card;

import java.util.List;

import com.if23b212.mtcg.db.card.CardRepository;
import com.if23b212.mtcg.db.card.PackageRepository;
import com.if23b212.mtcg.db.user.UserRepository;
import com.if23b212.mtcg.model.card.Card;
import com.if23b212.mtcg.model.user.User;

public class CardService {
    PackageRepository packagingRepo = new PackageRepository();
    UserRepository userRepo = new UserRepository();
    CardRepository cardRepo = new CardRepository();
    
	public List<Card> getUserCards(String username) throws Exception {
		User user = userRepo.getUser(username);
		return cardRepo.getStack(user.getId());
	}

}
