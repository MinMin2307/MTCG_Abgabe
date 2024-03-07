package com.if23b212.mtcg.rest.card;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.if23b212.mtcg.model.card.CardData;
import com.if23b212.mtcg.rest.HTTPMethod;
import com.if23b212.mtcg.rest.HTTPStatusCode;
import com.if23b212.mtcg.service.card.PackageService;
import com.if23b212.mtcg.util.HttpUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class PackageController {

	private final static ObjectMapper objectMapper = new ObjectMapper();
	private final static String CREATE_PACKAGE_MESSAGE = "Package successfully created";
	private final static String BOUGHT_PACKAGE_MESSAGE = "Package bought successfully created";

	private final static PackageService service = new PackageService();

	public static class PackageCreation implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			if (exchange != null && HTTPMethod.checkMethod(HTTPMethod.POST, exchange.getRequestMethod())) {
				String authHeader = exchange.getRequestHeaders().getFirst("Authorization");

				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					String token = authHeader.substring(7);
					if (token != null) {
						InputStream is = exchange.getRequestBody();
						try {
							List<CardData> cardDataList = objectMapper.readValue(is,
									new TypeReference<List<CardData>>() {
									});
							service.savePackage(cardDataList);
							HttpUtils.sendResponse(exchange, CREATE_PACKAGE_MESSAGE, HTTPStatusCode.CREATED.getCode());
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
	
	public static class BuyPackage implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			if (exchange != null && HTTPMethod.checkMethod(HTTPMethod.POST, exchange.getRequestMethod())) {
				String authHeader = exchange.getRequestHeaders().getFirst("Authorization");

				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					String token = authHeader.substring(7);
					if (token != null && token.length() > 0 ) {
						try {
							String username = token.replace("-mtcgToken", "").replace(" ", "");
							service.buyPackage(username);
							HttpUtils.sendResponse(exchange, BOUGHT_PACKAGE_MESSAGE, HTTPStatusCode.CREATED.getCode());
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