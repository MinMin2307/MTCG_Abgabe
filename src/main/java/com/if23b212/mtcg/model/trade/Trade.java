package com.if23b212.mtcg.model.trade;

import java.util.UUID;

public class Trade {
    private UUID id;
    private UUID offerUserId;
    private UUID requestUserId;
    private UUID offeredCardId;
    private UUID requestedCardId;
    private String tradeStatus;
    
	public Trade(UUID id, UUID offerUserId, UUID requestUserId, UUID offeredCardId, UUID requestedCardId) {
		this.id = id;
		this.offerUserId = offerUserId;
		this.requestUserId = requestUserId;
		this.offeredCardId = offeredCardId;
		this.requestedCardId = requestedCardId;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public UUID getOfferUserId() {
		return offerUserId;
	}
	public void setOfferUserId(UUID offerUserId) {
		this.offerUserId = offerUserId;
	}
	public UUID getRequestUserId() {
		return requestUserId;
	}
	public void setRequestUserId(UUID requestUserId) {
		this.requestUserId = requestUserId;
	}
	public UUID getOfferedCardId() {
		return offeredCardId;
	}
	public void setOfferedCardId(UUID offeredCardId) {
		this.offeredCardId = offeredCardId;
	}
	public UUID getRequestedCardId() {
		return requestedCardId;
	}
	public void setRequestedCardId(UUID requestedCardId) {
		this.requestedCardId = requestedCardId;
	}
}
