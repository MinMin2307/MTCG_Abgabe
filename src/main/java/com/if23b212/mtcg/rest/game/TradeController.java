package com.if23b212.mtcg.rest.game;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.if23b212.mtcg.rest.HTTPMethod;
import com.if23b212.mtcg.rest.HTTPStatusCode;
import com.if23b212.mtcg.service.trade.TradeService;
import com.if23b212.mtcg.util.HttpUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class TradeController {
	private final static ObjectMapper objectMapper = new ObjectMapper();
	private final static TradeService service = new TradeService();

	private final static String REGISTERED_RESPONSE_MESSAGE = "User successfully created";
	private final static String LOGGED_IN_RESPONSE_MESSAGE = "User login successful";
	private final static String GET_USER_RESPONSE_MESSAGE = "User fetch successful";
	private final static String UPDATE_USER_RESPONSE_MESSAGE = "User update successful";

	public static class UserHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			if (exchange != null && HTTPMethod.checkMethod(HTTPMethod.POST, exchange.getRequestMethod())) {
				createTrade(exchange);
			} else if (exchange != null && HTTPMethod.checkMethod(HTTPMethod.GET, exchange.getRequestMethod())) {
				getTrades(exchange);
			} else {
				HttpUtils.sendResponse(exchange, HTTPStatusCode.METHOD_NOT_ALLOWED.name(),
						HTTPStatusCode.METHOD_NOT_ALLOWED.getCode());
			}
		}
	}

	private static void createTrade(HttpExchange exchange) throws IOException {

	}

	private static void getTrades(HttpExchange exchange) throws IOException {

	}

	private static void doTrade(HttpExchange exchange) throws IOException {

	}
}
