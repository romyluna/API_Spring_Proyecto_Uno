package com.lta.cursoapis.curso_introduccion_apis.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

//Es una clase que define el formato del mensaje de error en que le voy a devolver al cliente.

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private int statusCode;
    private LocalDateTime timeStamp;
    private String errorDetails;

    public ErrorResponse(String message, int statusCode, String errorDetails) {
        this.message = message;
        this.statusCode = statusCode;
        this.timeStamp = LocalDateTime.now();
        this.errorDetails = errorDetails;
    }
}
