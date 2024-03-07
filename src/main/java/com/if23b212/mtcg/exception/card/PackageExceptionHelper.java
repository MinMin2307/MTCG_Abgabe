package com.if23b212.mtcg.exception.card;

import com.if23b212.mtcg.model.card.Card;
import com.if23b212.mtcg.model.user.User;

import java.util.List;

public class PackageExceptionHelper {

    public static final String INSUFFICIENT_COINS_EXCEPTION_MESSAGE = "Not enough coins to acquire a package";
    public static final String INVALID_CARD_COUNT_EXCEPTION_MESSAGE = " package must contain exactly 5 cards";
    public static final String NO_AVAILABLE_PACKAGES_EXCEPTION_MESSAGE = "No package available";

    public static class PackageException extends RuntimeException {
        public PackageException(String message) {
            super(message);
        }
    }

    public static class InsufficientCoinsException extends PackageException {
        public InsufficientCoinsException() {
            super(INSUFFICIENT_COINS_EXCEPTION_MESSAGE);
        }
    }

    public static class InvalidCardCountException extends PackageException {
        public InvalidCardCountException() {
            super(INVALID_CARD_COUNT_EXCEPTION_MESSAGE);
        }
    }
    
    public static class NoAvailablePackageException extends PackageException {
        public NoAvailablePackageException() {
            super(NO_AVAILABLE_PACKAGES_EXCEPTION_MESSAGE);
        }
    }

    public static void checkSufficientCoins(User user) {
        if (user.getCoins() < 5) {
            throw new InsufficientCoinsException();
        }
    }

    public static void checkValidCardCount(List<Card> cards) {
        if (cards.size() != 5) {
            throw new InvalidCardCountException();
        }
    }
}