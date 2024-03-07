package com.if23b212.mtcg.model.game;

import java.util.ArrayList;
import java.util.List;

public class BattleLog {
	List<String> log = new ArrayList<>();
	
	public void addLine(String s) {
		log.add(s);
	}

	@Override
	public String toString() {
		return "BattleLog [\n" + log + "]";
	}
	
	
}
