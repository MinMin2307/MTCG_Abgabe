package com.if23b212.mtcg.util;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.if23b212.mtcg.db.card.DeckRepository;
import com.if23b212.mtcg.db.game.GameRepository;
import com.if23b212.mtcg.model.card.Card;
import com.if23b212.mtcg.model.card.CardType;
import com.if23b212.mtcg.model.card.Deck;
import com.if23b212.mtcg.model.card.ElementType;
import com.if23b212.mtcg.model.card.MonsterTypeHelper;
import com.if23b212.mtcg.model.card.MonsterTypeHelper.MonsterType;
import com.if23b212.mtcg.model.game.BattleHistory;
import com.if23b212.mtcg.model.game.BattleLog;
import com.if23b212.mtcg.model.game.BattleStatus;
import com.if23b212.mtcg.model.game.BattleStatus.BattleState;
import com.if23b212.mtcg.model.user.User;

public class BattleHandler {
	
	DeckRepository deckRepo = new DeckRepository();
	GameRepository gameRepo = new GameRepository();
	
	public double calculateDamage(Card attacker, Card defender) {
		if(attacker.getCardType() == CardType.MONSTER && defender.getCardType() == CardType.MONSTER) {
			return attacker.getDamage();
		} else {
			if(attacker.getElementType() == ElementType.WATER && defender.getElementType() == ElementType.FIRE) {
				return attacker.getDamage()*2;
			} else if (attacker.getElementType() == ElementType.FIRE && defender.getElementType() == ElementType.AIR) {
				return attacker.getDamage()*2;
			}  else if (attacker.getElementType() == ElementType.AIR && defender.getElementType() == ElementType.EARTH) {
				return attacker.getDamage()*2;
			}	else if (attacker.getElementType() == ElementType.EARTH && defender.getElementType() == ElementType.LIGHTNING) {
					return attacker.getDamage()*2;
			} else if (attacker.getElementType() == ElementType.LIGHTNING && defender.getElementType() == ElementType.WATER) {
				return attacker.getDamage()*2;
			} else if(attacker.getElementType() == ElementType.FIRE && defender.getElementType() == ElementType.WATER) {
				return attacker.getDamage()/2;
			} else if(attacker.getElementType() == ElementType.EARTH && defender.getElementType() == ElementType.AIR) {
				return attacker.getDamage()/2;
			} else if(attacker.getElementType() == ElementType.LIGHTNING && defender.getElementType() == ElementType.EARTH) {
				return attacker.getDamage()/2;
			} else if(attacker.getElementType() == ElementType.WATER && defender.getElementType() == ElementType.LIGHTNING) {
				return attacker.getDamage()/2;
			} else {
				return attacker.getDamage();
			}
		}
	}
	
	private class DamageResult {
		private double damageOne = 0;
		private double damageTwo = 0;
		public DamageResult(double damageOne, double damageTwo) {
			this.damageOne = damageOne;
			this.damageTwo = damageTwo;
		}
		
	}
	
	public BattleStatus battle(List<User> users) {
		BattleLog log = new BattleLog();
		
	    UUID winnerId = null;
	    BattleHistory battleHistory = null;
	    try {
	        Deck deckOne = new Deck(deckRepo.getUserDeckCards(users.get(0).getId()));
	        Deck deckTwo = new Deck(deckRepo.getUserDeckCards(users.get(1).getId()));

	        int round = 0;
	        Random random = new Random();
	        while (!deckOne.getCards().isEmpty() && !deckTwo.getCards().isEmpty() && round < 100) {
	            Card cardOne = deckOne.getCards().get(random.nextInt(deckOne.getCards().size()));
	            Card cardTwo = deckTwo.getCards().get(random.nextInt(deckTwo.getCards().size()));
	            
	            DamageResult damage = useSpecialRules(cardOne, cardTwo, round, log);
	            if (damage.damageOne > damage.damageTwo) {
	                deckOne.addCard(cardTwo);
	                deckTwo.getCards().remove(cardTwo);
	            } else if (damage.damageTwo > damage.damageOne) {
	                deckTwo.addCard(cardOne);
	                deckOne.getCards().remove(cardOne);
	            }
	            round++;
	        }

	        // Determine the winner
	        if (deckOne.getCards().size() > deckTwo.getCards().size()) {
	            winnerId = users.get(0).getId();
	        } else if (deckTwo.getCards().size() > deckOne.getCards().size()) {
	            winnerId = users.get(1).getId();
	        } else {
				winnerId = null;
			}

	        // Create BattleHistory object
	        battleHistory = new BattleHistory(UUID.randomUUID(), LocalDateTime.now(), users.get(0).getId(), users.get(1).getId(), winnerId);
	        gameRepo.saveBattleHistory(battleHistory);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return new BattleStatus(BattleState.FOUGHT_BATTLE, log, battleHistory); // This now holds the outcome of the battle, including the winner.
	}
	
	private DamageResult useSpecialRules(Card cardOne,  Card cardTwo, int round, BattleLog log) {
        double damageOne = calculateDamage(cardOne, cardTwo);
        double damageTwo = calculateDamage(cardTwo, cardOne);
        String line = "round " + round + ": ";
        if(MonsterTypeHelper.getMonsterType(cardOne) == MonsterType.GOBLIN && MonsterTypeHelper.getMonsterType(cardTwo) == MonsterType.DRAGON) {
        	damageOne = 0.0;
        	line = ("Goblins are too afraid of Dragons to attack => Dragon wins");
        } else if(MonsterTypeHelper.getMonsterType(cardOne) == MonsterType.ORK && MonsterTypeHelper.getMonsterType(cardTwo) == MonsterType.WIZARD) {
        	damageOne = 0.0;
        	line = ("Wizard can control Orks so they are not able to damage them => Wizard wins");
        } else if(MonsterTypeHelper.getMonsterType(cardOne) == MonsterType.KNIGHT && (cardTwo.getCardType() == CardType.SPELL && cardTwo.getElementType() == ElementType.WATER)) {
        	damageOne = 0.0;
        	damageTwo = 9999.0;
        	line = ("The armor of Knights is so heavy that WaterSpells make them drown them instantly => WaterSpell wins");
        } else if(MonsterTypeHelper.getMonsterType(cardOne) == MonsterType.ORK && (cardTwo.getCardType() == CardType.SPELL && cardTwo.getElementType() == ElementType.WATER)) {

        } else if((cardOne.getCardType() == CardType.SPELL) && MonsterTypeHelper.getMonsterType(cardTwo) == MonsterType.KNIGHT ) {
        	damageOne = 0.0;
        	damageTwo = 9999.0;
        	line = ("The Kraken is immune against spells => Kraken wins");
        } else if(MonsterTypeHelper.getMonsterType(cardOne) == MonsterType.DRAGON && (MonsterTypeHelper.getMonsterType(cardTwo) == MonsterType.ELF && cardTwo.getElementType() == ElementType.FIRE)) {
        	damageOne = 0.0;
        } else {
        	line = (cardOne.getName() +"  (" + damageOne +" Damage) vs "+cardTwo.getName() +"  (" + damageTwo +  "Damage) => " +
        			(damageOne == damageTwo ? (" draw") : (damageOne > damageTwo) ? cardOne.getName() + " wins " : cardTwo.getName() + " wins "));
        }
        log.addLine(line+"\n");
        return new DamageResult(damageOne, damageTwo);
	}
}
