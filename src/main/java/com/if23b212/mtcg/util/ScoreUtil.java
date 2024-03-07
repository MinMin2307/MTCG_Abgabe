package com.if23b212.mtcg.util;

import java.util.List;
import java.util.UUID;

import com.if23b212.mtcg.config.ScoreboardConfig;
import com.if23b212.mtcg.model.game.BattleHistory;
import com.if23b212.mtcg.model.game.Statistics;

public class ScoreUtil {
	
	public static int calculateScore(List<BattleHistory> battles, UUID userId) {
		int currentValue = ScoreboardConfig.BASE_SCORE;
		if(CommonUtils.checkList(battles)) {
			for(BattleHistory battle : battles) {
				if(battle.getParticipantUserId1().equals(userId) || battle.getParticipantUserId2().equals(userId)) {
					currentValue += battle.getWinnerUserId() != null ? ((battle.getWinnerUserId().equals(userId)) ? 3 : -5)  : 0;
				}
			}
		}
		return currentValue; 
	}
	
	public static Statistics calculateStatistics(List<BattleHistory> battles, UUID userId) {
		int losses = 0;
		int wins = 0;
		int draws = 0;
		if(CommonUtils.checkList(battles)) {
			for(BattleHistory battle : battles) {
				if(battle.getParticipantUserId1().equals(userId) || battle.getParticipantUserId2().equals(userId)) {
					if(battle.getWinnerUserId() != null) {
						if(battle.getWinnerUserId().equals(userId)) {
							wins += 1;
						} else {
							losses += 1;
						}
					} else {
						draws += 1;
					}
				}
			}
		}
		return new Statistics(userId, draws+wins+losses, wins, losses);
	}
}
