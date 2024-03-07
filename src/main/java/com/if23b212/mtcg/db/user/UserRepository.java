package com.if23b212.mtcg.db.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.if23b212.mtcg.db.DatabaseRunner;
import com.if23b212.mtcg.exception.user.UserExceptionHelper;
import com.if23b212.mtcg.model.user.User;
import com.if23b212.mtcg.model.user.UserCredentials;
import com.if23b212.mtcg.model.user.UserUpdate;
import com.if23b212.mtcg.util.PasswordUtils;

public class UserRepository {

	/**
	 * Creates a new User and stores it in the Database(if does not already exist)
	 * 
	 * @param userCredentials
	 * @throws UserExceptionHelper.AlreadyRegisteredException
	 */
	public void saveUser(UserCredentials userCredentials) throws UserExceptionHelper.AlreadyRegisteredException {
		boolean alreadyExists = false;

		try (Connection con = DatabaseRunner.getConnection()) {
			// Check if user with the given username already exists
			alreadyExists = checkIfUserExists(con, userCredentials);
			if (!alreadyExists) {
				String query = "INSERT INTO \"user\" (username, password, coins) VALUES(?,?,?)";
				try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
					preparedStatement.setString(1, userCredentials.getUsername());
					preparedStatement.setString(2, PasswordUtils.hashPassword(userCredentials.getPassword()));
					preparedStatement.setInt(3, 20);
					preparedStatement.execute();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (alreadyExists) {
			throw new UserExceptionHelper.AlreadyRegisteredException();
		}
	}

	private static boolean checkIfUserExists(Connection con, UserCredentials userCredentials) throws SQLException {
		String query = "SELECT COUNT(u) from \"user\" u where u.username = ?";
		try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
			preparedStatement.setString(1, userCredentials.getUsername());
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				long count = rs.getLong(1);
				if (count > 0) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public User loginUser(UserCredentials userCredentials) throws UserExceptionHelper.InvalidCredentialsException {
		try (Connection con = DatabaseRunner.getConnection()) {
			// Query to fetch a user with the given username and password
			String query = "SELECT id, username, password, coins FROM \"user\"  WHERE username = ?";
			User user = null;
			try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
				preparedStatement.setString(1, userCredentials.getUsername());
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					user = new User(UUID.fromString(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getInt(4),
							new ArrayList<>());
				}
			}
			if (user != null && PasswordUtils.checkPassword(userCredentials.getPassword(), user.getPassword())) {
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new UserExceptionHelper.InvalidCredentialsException();
	}

	public User getUser(String username) throws UserExceptionHelper.UserNotFoundException {
		User user = null;
		try (Connection con = DatabaseRunner.getConnection()) {
			String query = "SELECT id, username, password, coins,name, bio, image FROM \"user\"  WHERE username = ?";

			try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
				preparedStatement.setString(1, username);
				ResultSet rs = preparedStatement.executeQuery();
	            if (rs.next()) {
	                user = new User(
	                    UUID.fromString(rs.getString(1)), 
	                    rs.getString(2), 
	                    rs.getString(3), 
	                    rs.getInt(4),
	                    new ArrayList<>(), 
	                    rs.getString(5), 
	                    rs.getString(6), 
	                    rs.getString(7)  
	                );
	            }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user != null) {
			return user;
		} else {
			throw new UserExceptionHelper.UserNotFoundException();
		}
	}
	
	public List<User> getAllUsers() throws UserExceptionHelper.UserNotFoundException {
		List<User> users = new ArrayList<>();

		try (Connection con = DatabaseRunner.getConnection()) {
			String query = "SELECT id, username, password, coins,name, bio, image FROM \"user\"";
			try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
				ResultSet rs = preparedStatement.executeQuery();
	            while (rs.next()) {
	                users.add(new User(
		                    UUID.fromString(rs.getString(1)), 
		                    rs.getString(2), 
		                    rs.getString(3), 
		                    rs.getInt(4),
		                    new ArrayList<>(), 
		                    rs.getString(5), 
		                    rs.getString(6), 
		                    rs.getString(7)  
		                ));
	            }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	public void reduceCoins(UUID userId, int amount) throws SQLException {
		try (Connection con = DatabaseRunner.getConnection()) {
			String query = "UPDATE \"user\" SET coins = coins - ? WHERE id = ?";
			try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
				preparedStatement.setInt(1, amount);
				preparedStatement.setObject(2, userId);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			throw e;
		}
	}

	public void updateUser(String username, UserUpdate userData) throws UserExceptionHelper.UserNotFoundException {
		User user = null;
		try (Connection con = DatabaseRunner.getConnection()) {
	        String query = "SELECT id, username, password, coins, name, bio, image FROM \"user\" WHERE username = ?";

			try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
				preparedStatement.setString(1, username);
				ResultSet rs = preparedStatement.executeQuery();
	            if (rs.next()) {
	                user = new User(
	                    UUID.fromString(rs.getString(1)), 
	                    rs.getString(2), 
	                    rs.getString(3), 
	                    rs.getInt(4),
	                    new ArrayList<>(), 
	                    rs.getString(5), 
	                    rs.getString(6), 
	                    rs.getString(7)  
	                );
	            }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user != null) {
			try (Connection con = DatabaseRunner.getConnection()) {
				String query = "UPDATE \"user\" SET name = ?, bio = ?, image = ? WHERE id = ?";

				try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
					preparedStatement.setString(1, userData.getName());
					preparedStatement.setString(2, userData.getBio());
					preparedStatement.setString(3, userData.getImage());
					preparedStatement.setObject(4, user.getId());

					int rowsAffected = preparedStatement.executeUpdate(); 
					System.out.println(rowsAffected);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new UserExceptionHelper.UserNotFoundException();
		}
	}
}
