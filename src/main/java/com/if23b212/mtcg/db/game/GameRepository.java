package com.if23b212.mtcg.db.game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.if23b212.mtcg.db.DatabaseRunner;
import com.if23b212.mtcg.model.game.BattleHistory;
import com.if23b212.mtcg.util.CommonUtils;

public class GameRepository {

	public void saveBattleHistory(BattleHistory battle) {
	    String query = "INSERT INTO battle_history (battle_id, date, participant_user_id_1, participant_user_id_2, winner_user_id) VALUES (?, ?, ?, ?, ?)";
	    try (Connection con = DatabaseRunner.getConnection();
	         PreparedStatement preparedStatement = con.prepareStatement(query)) {
	        
	        preparedStatement.setObject(1, battle.getId());
	        preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(battle.getDate()));
	        preparedStatement.setObject(3, battle.getParticipantUserId1());
	        preparedStatement.setObject(4, battle.getParticipantUserId2());
	        preparedStatement.setObject(5, battle.getWinnerUserId());

	        int affectedRows = preparedStatement.executeUpdate();
	        if (affectedRows == 0) {
	            throw new SQLException("Saving battle history failed, no rows affected.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public List<BattleHistory> getBattles(UUID userId) throws Exception {
		try (Connection con = DatabaseRunner.getConnection()) {
			String query = "SELECT * FROM battle_history WHERE participant_user_id_1 = ? OR participant_user_id_2 = ?";
			try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
				preparedStatement.setObject(1, userId); // Use setObject for UUID
				preparedStatement.setObject(2, userId); // Use setObject for UUID

				ResultSet rs = preparedStatement.executeQuery();
				List<BattleHistory> battles = new ArrayList<>();
				while (rs.next()) {
					battles.add(new BattleHistory(UUID.fromString(rs.getString(1)), CommonUtils.toLocalDateTime(rs.getTimestamp(2)), UUID.fromString(rs.getString(3)), UUID.fromString(rs.getString(4)), rs.getString(5) != null ? UUID.fromString(rs.getString(5)) : null));
				}
				return battles;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<BattleHistory> getBattles() throws Exception {
		try (Connection con = DatabaseRunner.getConnection()) {
			String query = "SELECT * FROM battle_history";
			try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
				ResultSet rs = preparedStatement.executeQuery();
				List<BattleHistory> battles = new ArrayList<>();
				while (rs.next()) {
					battles.add(new BattleHistory(UUID.fromString(rs.getString(1)), CommonUtils.toLocalDateTime(rs.getTimestamp(2)), UUID.fromString(rs.getString(3)), UUID.fromString(rs.getString(4)), (rs.getString(5) != null ? UUID.fromString(rs.getString(5)) : null)));
				}
				return battles;
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
