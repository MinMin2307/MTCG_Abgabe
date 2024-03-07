package com.if23b212.mtcg.util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HttpUtils {

    /**
     * Sends a response back to the client
     * @param exchange
     * @param response
     * @param statusCode
     * @throws IOException
     */
   public static void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
       exchange.sendResponseHeaders(statusCode, response.length());
       OutputStream os = exchange.getResponseBody();
       os.write(response.getBytes());
       os.close();
   }
}
