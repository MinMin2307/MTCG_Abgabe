package com.if23b212.mtcg.model.game;

import java.util.UUID;

public class PlayerRating {
	private int elo;
	private UUID userId;
	private String username;
	public PlayerRating(int elo, UUID userId, String username) {
		this.elo = elo;
		this.userId = userId;
		this.username = username;
	}
	public int getElo() {
		return elo;
	}
	public void setElo(int elo) {
		this.elo = elo;
	}
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "PlayerRating [elo=" + elo + ", userId=" + userId + ", username=" + username + "]";
	}
	
}
