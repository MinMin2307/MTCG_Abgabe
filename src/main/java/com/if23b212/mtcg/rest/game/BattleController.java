package com.if23b212.mtcg.rest.game;

import java.io.IOException;

import com.if23b212.mtcg.model.game.BattleStatus;
import com.if23b212.mtcg.rest.HTTPMethod;
import com.if23b212.mtcg.rest.HTTPStatusCode;
import com.if23b212.mtcg.service.game.GameService;
import com.if23b212.mtcg.util.HttpUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class BattleController {
	
	
	public static class EnterBatteLobby implements HttpHandler {
		private GameService service = new GameService();

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			if (exchange != null && HTTPMethod.checkMethod(HTTPMethod.POST, exchange.getRequestMethod())) {
				String authHeader = exchange.getRequestHeaders().getFirst("Authorization");

				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					String token = authHeader.substring(7);
					if (token != null && token.length() > 0 ) {
						try {
							String username = token.replace("-mtcgToken", "").replace(" ", "");
							BattleStatus status = service.enterLobby(username);
							HttpUtils.sendResponse(exchange, status.printStatus(), HTTPStatusCode.OK.getCode());

						} catch (Exception e) {
							e.printStackTrace();
							HttpUtils.sendResponse(exchange, e.getMessage(), HTTPStatusCode.CONFLICT.getCode());
						}
					} else {
						HttpUtils.sendResponse(exchange, HTTPStatusCode.FORBIDDEN.name(),HTTPStatusCode.FORBIDDEN.getCode());

					}
				} else {
					HttpUtils.sendResponse(exchange, HTTPStatusCode.METHOD_NOT_ALLOWED.name(),HTTPStatusCode.METHOD_NOT_ALLOWED.getCode());
				}
			}
		}			
	}
		

}
