package com.if23b212.mtcg.model.game;

import java.util.UUID;

public class Statistics {
    private UUID userId;
    private int numberOfBattles;
    private int wins;
    private int losses;

    public Statistics(UUID userId, int numberOfBattles, int wins, int losses) {
		this.userId = userId;
		this.numberOfBattles = numberOfBattles;
		this.wins = wins;
		this.losses = losses;
	}
    
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public int getNumberOfBattles() {
		return numberOfBattles;
	}
	public void setNumberOfBattles(int numberOfBattles) {
		this.numberOfBattles = numberOfBattles;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getLosses() {
		return losses;
	}
	public void setLosses(int losses) {
		this.losses = losses;
	}

	@Override
	public String toString() {
		return "Statistics [userId=" + userId + ", numberOfBattles=" + numberOfBattles + ", wins=" + wins + ", losses="
				+ losses + "]";
	}
}
