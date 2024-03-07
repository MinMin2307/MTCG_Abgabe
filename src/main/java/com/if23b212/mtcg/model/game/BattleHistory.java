package com.if23b212.mtcg.model.game;

import java.time.LocalDateTime;
import java.util.UUID;

public class BattleHistory {
    private UUID id;
    private LocalDateTime date;
    private UUID participantUserId1;
    private UUID participantUserId2;
    private UUID winnerUserId;
    
	public BattleHistory(UUID id, LocalDateTime date, UUID participantUserId1, UUID participantUserId2, UUID winnerUserId) {
		this.id = id;
		this.date = date;
		this.participantUserId1 = participantUserId1;
		this.participantUserId2 = participantUserId2;
		this.winnerUserId = winnerUserId;
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public UUID getParticipantUserId1() {
		return participantUserId1;
	}
	public void setParticipantUserId1(UUID participantUserId1) {
		this.participantUserId1 = participantUserId1;
	}
	public UUID getParticipantUserId2() {
		return participantUserId2;
	}
	public void setParticipantUserId2(UUID participantUserId2) {
		this.participantUserId2 = participantUserId2;
	}
	public UUID getWinnerUserId() {
		return winnerUserId;
	}
	public void setWinnerUserId(UUID winnerUserId) {
		this.winnerUserId = winnerUserId;
	}

	@Override
	public String toString() {
		return "BattleHistory [id=" + id + ",\n date=" + date + ", \nparticipantUserId1=" + participantUserId1
				+ ", \nparticipantUserId2=" + participantUserId2 + ",\nwinnerUserId=" + winnerUserId + "\n]";
	}
	
	
}
