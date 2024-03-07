package com.if23b212.mtcg.model.card;

public class MonsterTypeHelper {
	
	public static enum MonsterType {
		GOBLIN(10),
		ELF(25),
		DRAGON(50),
		ORK(45),
		KRAKEN(35),
		WIZARD(20),
		KNIGHT(25),
		TROLL(15),
		UNKNOWN(10);
		
		private int damage;
		private MonsterType(int damage) {
			this.damage = damage;
		}
		public int getDamage() {
			return damage;
		}
	}
	
	public static MonsterType getMonsterType(Card card) {
		if(card != null && card.getCardType() == CardType.MONSTER) {
			for(MonsterType type : MonsterType.values()) {
				if(card.getName().toLowerCase().contains(type.name().toLowerCase()))
					return type;
			}
		}
		return MonsterType.UNKNOWN;
	}
}
