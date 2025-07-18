package com.lta.cursoapis.curso_introduccion_apis.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//se convierte en un manejador global de errores para todos los controladores
//va a detectar las excepciones
@RestControllerAdvice
public class GlobalExceptionHandler {

        //indico la excepcion que manejara el metodo
        //cuando ocurre esta excepcion
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception){
            //se lanza este mensaje personalizado
            ErrorResponse errorResponse = new ErrorResponse(
                    exception.getMessage(), // "El producto no existe" es el msg q me llega
                    HttpStatus.NOT_FOUND.value(), //error 404
                    "Recurso no encontrado");
            return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
        }


        //para manejar cuando hay una solicitud incorrecta:
        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exception){
            //se lanza este mensaje personalizado
            ErrorResponse errorResponse = new ErrorResponse(
                    exception.getMessage(), //el msg q me llega
                    HttpStatus.BAD_REQUEST.value(), //error 400
                    "Solicitud incorrecta");
            return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
        }


        //cuando detecta este error es porque algun campo que esta requerido no esta siendo completado
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException exception){
           Map<String,String> errors = new HashMap<>();
           exception.getBindingResult().getFieldErrors()
                   .forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));

           String errorMessage = "errores de validacion en los campos:" + String.join(", ", errors.keySet());

            ErrorResponse errorResponse = new ErrorResponse(
                    exception.getMessage(),
                    HttpStatus.BAD_REQUEST.value(),
                    "falla en la validacion");
            return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);


        }



}
