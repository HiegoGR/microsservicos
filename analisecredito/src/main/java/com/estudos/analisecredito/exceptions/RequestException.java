package com.pontaagro.tgt.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Map;

public class RequestExceptionUtil {

    private static final MessageUtil msgUtil = new MessageUtil(RequestExceptionUtil.class);

    private static ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

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

    public static String badRequestExceptionString(String msg) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
    }

    public static String ok(String msg) {
        throw new ResponseStatusException(HttpStatus.OK, msg);
    }


    public static String paymentRequiredRequestException(String msg) {
        long timestamp = System.currentTimeMillis();
        int status = 402;
        String error = "Payment Required";

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", timestamp);
        response.put("status", status);
        response.put("error", error);
        response.put("message", msg);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static String internalServerErrorDao(PersistenceException e, String msg) {
        // Verifica se a causa da exceção é uma ConstraintViolationException
        Throwable cause = e.getCause();
        if (cause instanceof ConstraintViolationException) {
            String mensagemErro = ((ConstraintViolationException) cause).getSQLException().getMessage();
            String msgFormatada = mensagemErro.split("Detalhe: ")[1];
            String msgFormatada2 = mensagemErro.split("Detalhe: ")[0];
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, msg + returnMsgExistOrNotPresent(
                    TgtServerApiUtil.extrairTextoParaServerError(msgFormatada,msgFormatada2)));
        } else {
            String mensagemErro = cause.getMessage();

            if (mensagemErro.split("Detalhe: ").length == 1) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, msg);
            }

            String msgFormatada = mensagemErro.split("Detalhe: ")[1];
            String msgFormatada2 = mensagemErro.split("Detalhe: ")[0];
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, msg + returnMsgExistOrNotPresent(
                    TgtServerApiUtil.extrairTextoParaServerError(msgFormatada,msgFormatada2)));
        }

    }

    public static String badRequestDao(PersistenceException e, String msg) {
        // Verifica se a causa da exceção é uma ConstraintViolationException
        Throwable cause = e.getCause();
        if (cause instanceof ConstraintViolationException) {
            String mensagemErro = ((ConstraintViolationException) cause).getSQLException().getMessage();
            String msgFormatada = mensagemErro.split("Detalhe: ")[1];
            String msgFormatada2 = mensagemErro.split("Detalhe: ")[0];
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, msg + returnMsgExistOrNotPresent(
                    TgtServerApiUtil.extrairTextoParaServerError(msgFormatada,msgFormatada2)));
        } else {
            String mensagemErro = cause.getMessage();

            if (mensagemErro.split("Detalhe: ").length == 1) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, msg);
            }

            String msgFormatada = mensagemErro.split("Detalhe: ")[1];
            String msgFormatada2 = mensagemErro.split("Detalhe: ")[0];
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, msg + returnMsgExistOrNotPresent(
                    TgtServerApiUtil.extrairTextoParaServerError(msgFormatada,msgFormatada2)));
        }

    }

    public static String returnMsgExistOrNotPresent(String mensagem){
        if(mensagem.contains("already exist")){
           return mensagem.replace("already exist",msgUtil.getMsg("dadosexistente"));
        }

        if(mensagem.contains("is not present")){
            return mensagem.replace("is not present",msgUtil.getMsg("isnotpresent"));
        }

        return mensagem;
    }

    public static String methodNotAllowedString(String msg) {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, msg);
    }

    public static String methodNotAllowedString2(String msg) {
        throw new ResourceNotFoundException(HttpStatus.METHOD_NOT_ALLOWED,HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(),msg,"testeHiego", requestAttributes.getRequest().getRequestURI());
/*  Retorno da mensagem de erro
            {
                "dateTime": "2024-01-25 12:24:21",
                "status": 405,
                "error": "Method Not Allowed",
                "message": {
                    "detail": "testeHiego",
                    "title": "Veículo com implantação em aberto! implantação: teste,  início: 2023-11-28 08:24:13.  É necessário efetuar o encerramento para prosseguir."
                },
                "path": "/api/v1/vehicle/delete/1"
            }
*/
    }


}
