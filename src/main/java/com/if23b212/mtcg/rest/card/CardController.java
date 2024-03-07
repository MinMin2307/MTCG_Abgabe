package com.if23b212.mtcg.rest.card;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.if23b212.mtcg.model.card.Card;
import com.if23b212.mtcg.rest.HTTPMethod;
import com.if23b212.mtcg.rest.HTTPStatusCode;
import com.if23b212.mtcg.service.card.CardService;
import com.if23b212.mtcg.util.HttpUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CardController {

	private final static String LIST_CARDS_MESSAGE = "Card list successfully fetched";

	private final static CardService service = new CardService();	
	
	public static class GetCardList implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			if (exchange != null && HTTPMethod.checkMethod(HTTPMethod.GET, exchange.getRequestMethod())) {
				String authHeader = exchange.getRequestHeaders().getFirst("Authorization");

				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					String token = authHeader.substring(7);
					if (token != null && token.length() > 0) {
						try {
							String username = token.replace("-mtcgToken", "").replace(" ", "");
							List<Card> cards = service.getUserCards(username);
							HttpUtils.sendResponse(exchange, LIST_CARDS_MESSAGE + "\n " + cards.toString() + "\n", HTTPStatusCode.OK.getCode());
						} catch (Exception e) {
							e.printStackTrace();
							HttpUtils.sendResponse(exchange, e.getMessage(), HTTPStatusCode.CONFLICT.getCode());
						}
					} else {
						HttpUtils.sendResponse(exchange, HTTPStatusCode.FORBIDDEN.name(), HTTPStatusCode.FORBIDDEN.getCode());

					}
				} else {
					HttpUtils.sendResponse(exchange, HTTPStatusCode.METHOD_NOT_ALLOWED.name(), HTTPStatusCode.METHOD_NOT_ALLOWED.getCode());
				}
			}
		}
	}
}