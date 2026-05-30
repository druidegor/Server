package org.example.tasktraker.network;

public class ResponseFactory {

    public static Response createSuccess(String message, Object data) {
        return new Response(true, message, data);
    }

    public static Response createSuccess(String message) {
        return new Response(true, message, null);
    }

    public static Response createError(String errorMessage) {
        return new Response(false, errorMessage, null);
    }
}
