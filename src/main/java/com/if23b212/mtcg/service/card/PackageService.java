package com.if23b212.mtcg.service.card;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.if23b212.mtcg.db.card.PackageRepository;
import com.if23b212.mtcg.db.user.UserRepository;
import com.if23b212.mtcg.exception.card.PackageExceptionHelper;
import com.if23b212.mtcg.model.card.Card;
import com.if23b212.mtcg.model.card.CardData;
import com.if23b212.mtcg.model.card.CardType;
import com.if23b212.mtcg.model.card.ElementType;
import com.if23b212.mtcg.model.card.Package;
import com.if23b212.mtcg.model.user.User;


public class PackageService {
    PackageRepository repo = new PackageRepository();
    UserRepository userRepo = new UserRepository();
    
    public void savePackage(List<CardData> cardData) throws Exception {
    	List<Card> cards = new ArrayList<>();
    	UUID packageId = UUID.randomUUID();

    	for(CardData data : cardData) {
    		cards.add(new Card(data.getId(), data.getName(), data.getDamage(), data.getName().contains("Spell") ? CardType.SPELL : CardType.MONSTER, ElementType.getElementType(data.getName()), null, packageId));
    	}
    	Package package_ = new Package(packageId, cards);
    	
         repo.savePackage(package_);
    }
    
    public void buyPackage(String username) throws Exception {
    	User user = userRepo.getUser(username);
    	if(user.getCoins() >= 5) {
    		repo.buyPackage(user.getId());
    		userRepo.reduceCoins(user.getId(), 5);
    	} else {
    		throw new PackageExceptionHelper.InsufficientCoinsException();
    	}
    }
}
