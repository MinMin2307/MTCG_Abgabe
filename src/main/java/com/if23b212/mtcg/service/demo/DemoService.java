package com.if23b212.mtcg.service.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.if23b212.mtcg.db.card.CardRepository;
import com.if23b212.mtcg.db.card.PackageRepository;
import com.if23b212.mtcg.db.user.UserRepository;
import com.if23b212.mtcg.model.card.Card;
import com.if23b212.mtcg.model.card.CardType;
import com.if23b212.mtcg.model.card.ElementType;
import com.if23b212.mtcg.model.card.MonsterTypeHelper.MonsterType;
import com.if23b212.mtcg.model.user.User;
import com.if23b212.mtcg.model.card.Package;

public class DemoService {
	CardRepository cardRepo = new CardRepository();
	PackageRepository packageRepo = new PackageRepository();
	UserRepository userRepo = new UserRepository();
	
	public List<Card> createRandomCard(int amount, UUID userId, UUID packageId) {
	    List<MonsterType> monsterTypes = Arrays.asList(MonsterType.values());
	    List<ElementType> elementTypes = Arrays.asList(ElementType.values());
	    List<CardType> cardTypes = Arrays.asList(CardType.values());
	    
	    Random random = new Random();
	    List<Card> cards = new ArrayList<>();
	    
	    for (int i = 0; i < amount; i++) {
	        CardType cardType = cardTypes.get(random.nextInt(cardTypes.size()));
	        ElementType elementType = elementTypes.get(random.nextInt(elementTypes.size()));
	        
	        String element = elementType.name().substring(0, 1).toUpperCase() + elementType.name().substring(1).toLowerCase();
	        
	        if (cardType == CardType.MONSTER) {
	            MonsterType monsterType = monsterTypes.get(random.nextInt(monsterTypes.size()));
				while(monsterType == MonsterType.UNKNOWN) {
					monsterType = monsterTypes.get(random.nextInt(monsterTypes.size()));
				}
				String cardName =  monsterType.name().substring(0, 1).toUpperCase() + monsterType.name().substring(1).toLowerCase();
	            element = elementType != ElementType.NORMAL ? element : "";
				cards.add(new Card(UUID.randomUUID(), element + cardName, monsterType.getDamage(), cardType, elementType, userId, packageId));
	        } else {
	            cards.add(new Card(UUID.randomUUID(), element + "Spell", 20, cardType, elementType, userId, packageId));
	        }
	    }
	    
	    return cards;
	}
	
	public void createPackage(String username) {
		User user = userRepo.getUser(username);
		UUID packageId = UUID.randomUUID();
		
		List<Card> cards = createRandomCard(5, user.getId(), packageId);
		Package package_ = new Package(packageId, cards);
		try {
			packageRepo.savePackage(package_);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
