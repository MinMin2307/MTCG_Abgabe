package com.if23b212.mtcg.db.card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.if23b212.mtcg.db.DatabaseRunner;
import com.if23b212.mtcg.model.card.Card;
import com.if23b212.mtcg.model.card.CardType;
import com.if23b212.mtcg.model.card.ElementType;
import com.if23b212.mtcg.model.trade.Trade;

public class CardRepository {

	public List<Card> getStack(UUID userId) throws Exception {
		try (Connection con = DatabaseRunner.getConnection()) {
			String query = "SELECT * FROM card WHERE user_id = ?";
			try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
				preparedStatement.setObject(1, userId); // Use setObject for UUID
				ResultSet rs = preparedStatement.executeQuery();
				List<Card> cards = new ArrayList<>();
				while (rs.next()) {
					cards.add(new Card(UUID.fromString(rs.getString(1)), rs.getString(2), rs.getDouble(3),
							CardType.valueOf(rs.getString(4)), ElementType.valueOf(rs.getString(5)),
							UUID.fromString(rs.getString(6)), UUID.fromString(rs.getString(7))));

				}
				return cards;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void updateCardOwnership(UUID offeredCardId, UUID offeredToUserId, UUID requestedCardId, UUID requestedToUserId) throws SQLException {
	    String query = "UPDATE card SET user_id = CASE WHEN id = ? THEN ? WHEN id = ? THEN ? END WHERE id IN (?, ?)";
	    
	    try (Connection con = DatabaseRunner.getConnection();
	         PreparedStatement stmt = con.prepareStatement(query)) {
	        con.setAutoCommit(false);
	        stmt.setObject(1, offeredCardId);
	        stmt.setObject(2, offeredToUserId);
	        stmt.setObject(3, requestedCardId);
	        stmt.setObject(4, requestedToUserId);
	        stmt.setObject(5, offeredCardId);
	        stmt.setObject(6, requestedCardId);
	        
	        stmt.executeUpdate();
	        con.commit();
	    } catch (SQLException e) {
	        throw new RuntimeException("Error updating card ownership: " + e.getMessage(), e);
	    }
	}
	
	public void logTrade(Trade trade) throws SQLException {
	    String query = "INSERT INTO trade (trade_id, offer_user_id, request_user_id, offered_card_id, requested_card_id, trade_status) VALUES (?, ?, ?, ?, ?, ?)";
	    
	    try (Connection con = DatabaseRunner.getConnection();
	         PreparedStatement stmt = con.prepareStatement(query)) {
	        stmt.setObject(1, trade.getId());
	        stmt.setObject(2, trade.getOfferUserId());
	        stmt.setObject(3, trade.getRequestUserId());
	        stmt.setObject(4, trade.getOfferedCardId());
	        stmt.setObject(5, trade.getRequestedCardId());	        
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        throw new RuntimeException("Error logging trade: " + e.getMessage(), e);
	    }
	}
}
