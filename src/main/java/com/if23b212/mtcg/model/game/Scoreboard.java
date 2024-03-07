package com.if23b212.mtcg.model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Scoreboard {
	List<PlayerRating> ratings = new ArrayList<>();
	
	public Scoreboard() {
	}

	public Scoreboard(List<PlayerRating> ratings) {
		this.ratings = ratings;
	}

	public List<PlayerRating> getRatings() {
		return ratings;
	}

	public void setRatings(List<PlayerRating> ratings) {
		this.ratings = ratings;
	}
	
    public void sortRatings() {
        Collections.sort(ratings, new Comparator<PlayerRating>() {
            @Override
            public int compare(PlayerRating scoreOne, PlayerRating scoreTwo) {
                return Integer.compare(scoreOne.getElo(), scoreTwo.getElo())*(-1);
            }
        });
    }

	@Override
	public String toString() {
		return "Scoreboard{" +
				"ratings=" + ratings +
				'}';
	}
	
	public String printScoreboard() {
		sortRatings();
		String lines = "Scorboard:\n";
		for(PlayerRating rating : ratings) {
			lines += rating.getUsername() + " : " + rating.getElo() + "\n";
		}
		return lines;
	}
}
