package com.if23b212.mtcg.service.game;

import java.util.ArrayList;
import java.util.List;

import com.if23b212.mtcg.db.game.GameRepository;
import com.if23b212.mtcg.db.user.UserRepository;
import com.if23b212.mtcg.model.game.BattleHistory;
import com.if23b212.mtcg.model.game.BattleStatus;
import com.if23b212.mtcg.model.game.PlayerRating;
import com.if23b212.mtcg.model.game.Scoreboard;
import com.if23b212.mtcg.model.game.Statistics;
import com.if23b212.mtcg.model.user.User;
import com.if23b212.mtcg.util.BattleHandler;
import com.if23b212.mtcg.util.ScoreUtil;

public class GameService {
	
    UserRepository userRepo = new UserRepository();
	GameRepository gameRepo = new GameRepository();
	
	List<User> gameLobby = new ArrayList<>();
	
	public Statistics getStatistics(String username) throws Exception {
		User user = userRepo.getUser(username);
		if(user != null) {
			List<BattleHistory> battles = gameRepo.getBattles(user.getId());
			return ScoreUtil.calculateStatistics(battles, user.getId());
		}
		return null;
	}
	
	
	public Scoreboard getScoreboard() throws Exception {
		List<BattleHistory> battles = gameRepo.getBattles();
		List<User> users = userRepo.getAllUsers();
		Scoreboard scoreboard = new Scoreboard();
		for(User user : users) {
			int score = ScoreUtil.calculateScore(battles, user.getId());
			scoreboard.getRatings().add(new PlayerRating(score, user.getId(), user.getUsername()));
		}
		return scoreboard;
	}
	
	public BattleStatus enterLobby(String username) {
		User user = userRepo.getUser(username);
		gameLobby.add(user);
		if(gameLobby.size() == 2) {
			BattleStatus battleStatus = new BattleHandler().battle(gameLobby);
			gameLobby.clear();
			return battleStatus;
		}
		return new BattleStatus();
	}
	
}
