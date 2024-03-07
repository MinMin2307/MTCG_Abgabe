package com.if23b212.mtcg.util;
import com.sun.net.httpserver.HttpExchange;

public class AuthUtil {
		
	public static boolean isAuthorized(HttpExchange exchange) {
		if(exchange != null) {
			String authHeader = exchange.getRequestHeaders().getFirst("Authorization");

			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				String token = authHeader.substring(7);
				return token != null && token.length() > 0;
			}
		}
		return false;
	}

}
