package com.if23b212.mtcg.rest;

public enum HTTPMethod {
    GET,
    POST,
    PUT,
    HEAD,
    DELETE,
    PATCH,
    OPTIONS,
    CONNECT,
    TRACE;

    /**
     * Returns the HTTPMethod equivalent to the input
     * @param input
     * @return
     */
    public static HTTPMethod getMethod(String input){
        HTTPMethod method = null;
        if(input != null) {
            method = HTTPMethod.valueOf(input.toUpperCase());
        }
        return method;
    }

    /**
     * Checks if the provided HTTP methods are equivalent
     * @param method1
     * @param input
     * @return
     */
    public static boolean checkMethod(HTTPMethod method1, String input) {
        HTTPMethod method2 = getMethod(input);
        return method1 != null && method2 != null && method1 == method2;
    }
}
