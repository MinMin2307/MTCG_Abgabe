package com.if23b212.mtcg.db.card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.if23b212.mtcg.db.DatabaseRunner;
import com.if23b212.mtcg.exception.card.PackageExceptionHelper;
import com.if23b212.mtcg.model.card.Card;
import com.if23b212.mtcg.model.card.Package;

public class PackageRepository {

    public void savePackage(Package package_) throws Exception{

        try(Connection con = DatabaseRunner.getConnection()) {
        	UUID packageId = UUID.randomUUID();
        	
        	String query = "INSERT INTO package (id, available) VALUES(?, ?)";
            try(PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setObject(1, packageId);
                preparedStatement.setObject(2, true);
                preparedStatement.execute();
            }
            
            for(Card card : package_.getCards()) {
            	saveCard(card, packageId);
            }         
        } catch (Exception e) {
            throw e;
        }
    }
    
    private void saveCard(Card card, UUID packageId) throws Exception {
        try(Connection con = DatabaseRunner.getConnection()) {
            String query = "INSERT INTO card (id, name, damage, card_type, element_type, user_id, package_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
            try(PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setObject(1, card.getId()); // Use setObject for UUID
                preparedStatement.setString(2, card.getName());
                preparedStatement.setDouble(3, card.getDamage());
                preparedStatement.setString(4, card.getCardType().name());
                preparedStatement.setString(5, card.getElementType().name());
                preparedStatement.setObject(6, card.getUserId()); // Use setObject for UUID
                preparedStatement.setObject(7, packageId); // Use setObject for UUID
                preparedStatement.execute();
            }
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void buyPackage(UUID userId) throws Exception {
        try (Connection con = DatabaseRunner.getConnection()) {
            UUID packageId = getFirstAvailablePackage(con);
            if (packageId != null) {
                updateCardOwnership(con, packageId, userId);

                String updatePackageQuery = "UPDATE package SET available = false WHERE id = ?";
                try (PreparedStatement updatePackageStmt = con.prepareStatement(updatePackageQuery)) {
                    updatePackageStmt.setObject(1, packageId);
                    updatePackageStmt.executeUpdate();
                }
            } else {
            	throw new PackageExceptionHelper.NoAvailablePackageException();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private UUID getFirstAvailablePackage(Connection con) throws SQLException {
        String query = "SELECT id FROM package WHERE available = true LIMIT 1";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return UUID.fromString(rs.getString("id"));
            }
        }
        return null;
    }

    private void updateCardOwnership(Connection con, UUID packageId, UUID userId) throws SQLException {
        String updateCardQuery = "UPDATE card SET user_id = ? WHERE package_id = ?";
        try (PreparedStatement updateCardStmt = con.prepareStatement(updateCardQuery)) {
            updateCardStmt.setObject(1, userId);
            updateCardStmt.setObject(2, packageId);
            updateCardStmt.executeUpdate();
        }
    }

}
