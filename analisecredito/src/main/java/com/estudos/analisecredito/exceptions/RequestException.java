package com.estudos.analisecredito.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RequestException {

    public static void badRequestException(String msg) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }

    public static void acceptedRequestException(String msg) {
        throw new ResponseStatusException(HttpStatus.ACCEPTED, msg);
    }

    public static void notFoundRequestException(String msg) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg);
    }

    public static void forbiddenException(String msg) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, msg);
    }

    public static void unauthorized(String msg) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, msg);
    }

    public static void unprocessableEntity(String msg) {
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, msg);
    }

    public static String ok(String msg) {
        throw new ResponseStatusException(HttpStatus.OK, msg);
    }

}
