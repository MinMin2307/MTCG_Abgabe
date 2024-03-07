package com.if23b212.mtcg.rest.card;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.if23b212.mtcg.model.card.Deck;
import com.if23b212.mtcg.rest.HTTPMethod;
import com.if23b212.mtcg.rest.HTTPStatusCode;
import com.if23b212.mtcg.service.card.DeckService;
import com.if23b212.mtcg.util.HttpUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class DeckController {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final static String DECK_MESSAGE = "Deck list successfully fetched";
    private final static String DECK_CONFIGURE_MESSAGE = "Deck successfully configured";

    private final static DeckService service = new DeckService();


    public static class HandleDeck implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
            String method = exchange.getRequestMethod();

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (token != null && token.length() > 0) {
                    String username = token.replace("-mtcgToken", "").replace(" ", "");
                    if (HTTPMethod.checkMethod(HTTPMethod.GET, method)) {
                        handleGetRequest(exchange, username);
                    } else if (HTTPMethod.checkMethod(HTTPMethod.PUT, method)) {
                        handlePutRequest(exchange, username);
                    } else {
                        HttpUtils.sendResponse(exchange, HTTPStatusCode.METHOD_NOT_ALLOWED.name(), HTTPStatusCode.METHOD_NOT_ALLOWED.getCode());
                    }
                } else {
                    HttpUtils.sendResponse(exchange, HTTPStatusCode.FORBIDDEN.name(), HTTPStatusCode.FORBIDDEN.getCode());
                }
            } else {
                HttpUtils.sendResponse(exchange, HTTPStatusCode.FORBIDDEN.name(), HTTPStatusCode.FORBIDDEN.getCode());
            }
        }

        private void handleGetRequest(HttpExchange exchange, String username) throws IOException {
            try {
                Deck deck = service.getDeck(username);
                // Check for the format query parameter
                String query = exchange.getRequestURI().getQuery();
                boolean isPlainFormat = query != null && query.contains("format=plain");

                String response;
                if (isPlainFormat) {
                    // Convert the deck to a plain text format
                    response = deckToPlainText(deck);
                } else {
                    // Default to JSON or structured format
                    response = DECK_MESSAGE + "\n " + deck.toString() + "\n"; // Assume deck.toString() returns JSON or structured format
                }
                HttpUtils.sendResponse(exchange, response, HTTPStatusCode.OK.getCode());
            } catch (Exception e) {
                e.printStackTrace();
                HttpUtils.sendResponse(exchange, e.getMessage(), HTTPStatusCode.CONFLICT.getCode());
            }
        }

        private String deckToPlainText(Deck deck) {
            // Implement the logic to convert the Deck object to a plain text representation
            StringBuilder sb = new StringBuilder("Deck List:\n");
            deck.getCards().forEach(card -> sb.append(String.format("Card ID: %s, Card Name: %s, Damage: %.2f\n",
                    card.getId().toString(), card.getName(), card.getDamage())));
            return sb.toString();
        }

        private void handlePutRequest(HttpExchange exchange, String username) throws IOException {
            try {
                InputStream is = exchange.getRequestBody();
                List<UUID> cardDataList = objectMapper.readValue(is, new TypeReference<List<UUID>>() {});
                service.configureDeck(username, cardDataList);
                HttpUtils.sendResponse(exchange, DECK_CONFIGURE_MESSAGE, HTTPStatusCode.OK.getCode());
            } catch (Exception e) {
                e.printStackTrace();
                HttpUtils.sendResponse(exchange, e.getMessage(), HTTPStatusCode.CONFLICT.getCode());
            }
        }
    }
}