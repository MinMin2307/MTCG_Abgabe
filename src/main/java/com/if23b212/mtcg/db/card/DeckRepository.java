package com.if23b212.mtcg.db.card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.if23b212.mtcg.db.DatabaseRunner;
import com.if23b212.mtcg.model.card.Card;
import com.if23b212.mtcg.model.card.CardType;
import com.if23b212.mtcg.model.card.ElementType;

public class DeckRepository {


    public void addCardToDeck(UUID cardId, UUID userId) throws Exception {
        try (Connection con = DatabaseRunner.getConnection()) {
            // First, check how many cards the user already has in their deck
            try (PreparedStatement countStmt = con.prepareStatement("SELECT COUNT(*) AS card_count FROM deck WHERE user_id = ?")) {
                countStmt.setObject(1, userId);
                try (ResultSet rs = countStmt.executeQuery()) {
                    if (rs.next() && rs.getInt("card_count") >= 4) {
                        // The user already has 4 cards in their deck, handle this case appropriately
                        throw new Exception("User already has 4 cards in their deck. Cannot add more.");
                    }
                }
            }

            // Check if the card is already in the deck
            try (PreparedStatement checkStmt = con.prepareStatement("SELECT * FROM deck WHERE card_id = ?")) {
                checkStmt.setObject(1, cardId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) { // If the card is not already in the deck, insert it
                        try (PreparedStatement insertStmt = con.prepareStatement("INSERT INTO deck (user_id, card_id) VALUES (?, ?)")) {
                            insertStmt.setObject(1, userId);
                            insertStmt.setObject(2, cardId);
                            insertStmt.executeUpdate();
                        }
                    } else {
                        // The card is already in the deck, update it or handle this case
                        throw new Exception("Card is already in the deck.");
                    }
                }
            }
        }
    }


	public List<Card> getUserDeckCards(UUID userId) throws Exception {
	    try (Connection con = DatabaseRunner.getConnection()) {
	        String query = "SELECT c.* FROM card c INNER JOIN deck d ON c.id = d.card_id WHERE d.user_id = ?";
	        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
	            preparedStatement.setObject(1, userId); // Use setObject for UUID
	            ResultSet rs = preparedStatement.executeQuery();
	            List<Card> cards = new ArrayList<>();
	            while (rs.next()) {
	                cards.add(new Card(
	                    UUID.fromString(rs.getString("id")), 
	                    rs.getString("name"),
	                    rs.getDouble("damage"),
	                    CardType.valueOf(rs.getString("card_type")), 
	                    ElementType.valueOf(rs.getString("element_type")), 
	                    UUID.fromString(rs.getString("user_id")), 
	                    UUID.fromString(rs.getString("package_id")) 
	                ));
	            }
	            return cards;
	        }
	    } catch (Exception e) {
	        throw e;
	    }
	}

	
}
