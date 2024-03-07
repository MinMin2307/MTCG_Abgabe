package com.if23b212.mtcg.model.game;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private UUID userId;
    private UUID packageId;
    private LocalDateTime transactionDate;
    private int amount;
	public Transaction(UUID id, UUID userId, UUID packageId, LocalDateTime transactionDate, int amount) {
		this.id = id;
		this.userId = userId;
		this.packageId = packageId;
		this.transactionDate = transactionDate;
		this.amount = amount;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public UUID getPackageId() {
		return packageId;
	}
	public void setPackageId(UUID packageId) {
		this.packageId = packageId;
	}
	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
    
    
}
