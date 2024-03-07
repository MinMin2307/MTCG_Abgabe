package com.if23b212.mtcg.model.user;

import java.util.List;
import java.util.UUID;

import com.if23b212.mtcg.model.card.Card;


public class User {


    public User() {}
    private UUID id;

    private String username;

    private String password;

    private int coins;
    
    private String name;
    
    private String bio;
    
    private String image;

    private List<Card> stack;

    public User(UUID id, String username, String password, int coins, List<Card> stack) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.coins = coins;
        this.stack = stack;
    }
    
    public User(UUID id, String username, String password, int coins, List<Card> stack,String name, String bio, String image) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.coins = coins;
        this.stack = stack;
        this.name = name;
        this.bio = bio;
        this.image = image;
    }

    public User(String username, String password, int coins, List<Card> stack) {
        this.username = username;
        this.password = password;
        this.coins = coins;
        this.stack = stack;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getCoins() {
        return coins;
    }
    public void setCoins(int coins) {
        this.coins = coins;
    }
    public List<Card> getStack() {
        return stack;
    }
    public void setStack(List<Card> stack) {
        this.stack = stack;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", coins=" + coins + ", name="
				+ name + ", bio=" + bio + ", image=" + image + ", stack=" + stack + "]";
	}
    
}
