package com.if23b212.mtcg.model.game;

public class BattleStatus {
	
	private BattleState state = BattleState.WAITING_IN_LOBBY;
	private BattleLog log = new BattleLog();
	private BattleHistory battle = null;;
	
	public BattleStatus(BattleState state, BattleLog log, BattleHistory battle) {
		this.state = state;
		this.log = log;
		this.battle = battle;
	}

	public BattleStatus() {

	}

	public BattleState getState() {
		return state;
	}
	public void setState(BattleState state) {
		this.state = state;
	}
	public BattleLog getLog() {
		return log;
	}
	public void setLog(BattleLog log) {
		this.log = log;
	}
	public BattleHistory getBattle() {
		return battle;
	}
	public void setBattle(BattleHistory battle) {
		this.battle = battle;
	}
	
	public String printStatus() {
		if(state == BattleState.WAITING_IN_LOBBY) {
			return "Player is waiting in lobby";
		} else if(state == BattleState.FOUGHT_BATTLE) {
			return "Battle history: \n" + battle.toString() + "\n log: " + "\n " + log.toString() + "\n\n";
		}
		return "Error";
	}

	public static enum BattleState {
		WAITING_IN_LOBBY,
		FOUGHT_BATTLE;
	}
}
