package com.if23b212.mtcg.model.card;

public enum ElementType {
    FIRE,
    WATER,
    NORMAL,
    EARTH,
    AIR,
    LIGHTNING;
	
	public static ElementType getElementType(String s) {
		if(s != null) {
			if(s.toLowerCase().contains(FIRE.name().toLowerCase())) {
				return FIRE;
			} else if(s.toLowerCase().contains(WATER.name().toLowerCase())) {
				return WATER;
			} else if(s.toLowerCase().contains(EARTH.name().toLowerCase())) {
				return EARTH;
			} else if(s.toLowerCase().contains(AIR.name().toLowerCase())) {
				return AIR;
			} else if(s.toLowerCase().contains(LIGHTNING.name().toLowerCase())) {
				return LIGHTNING;
			}
		
		}
		return NORMAL;
	}
}
