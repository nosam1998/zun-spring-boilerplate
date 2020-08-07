/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sgz.server.exceptionhandlers;

import com.sgz.server.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author samg.zun
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(InvalidIdException.class)
    public final ResponseEntity<CustomError> handleInvalidIdException(
            InvalidIdException ex,
            WebRequest req) {

        logHandler(ex, req);

        return new ResponseEntity<>(new CustomError(ex.getMessage(), "InvalidIdException"),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NoItemsException.class)
    public final ResponseEntity<CustomError> handleNoItemsException(
            NoItemsException ex,
            WebRequest req) {

        logHandler(ex, req);

        return new ResponseEntity<>(new CustomError(ex.getMessage(), "NoItemsException"),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public final ResponseEntity<CustomError> handleInvalidEntityException(
            InvalidEntityException ex,
            WebRequest req) {

        logHandler(ex, req);

        final String CUSTOM_ERR_MESSAGE = "Fields entered are invalid";

        return new ResponseEntity<>(new CustomError(CUSTOM_ERR_MESSAGE, "InvalidEntityException"),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidNameException.class)
    public final ResponseEntity<CustomError> handleInvalidNameException(
            InvalidNameException ex,
            WebRequest req) {

        logHandler(ex, req);

        final String CUSTOM_ERR_MESSAGE = "Name entered is invalid";

        return new ResponseEntity<>(new CustomError(CUSTOM_ERR_MESSAGE, "InvalidNameException"),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidAuthorityException.class)
    public final ResponseEntity<CustomError> handleInvalidAuthorityException(
            InvalidAuthorityException ex,
            WebRequest req) {

        logHandler(ex, req);

        final String CUSTOM_ERR_MESSAGE = "Authority entered is invalid";

        return new ResponseEntity<>(new CustomError(CUSTOM_ERR_MESSAGE, "InvalidAuthorityException"),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<CustomError> handleAccessDeniedException(
            AccessDeniedException ex,
            WebRequest req) {

        logHandler(ex, req);

        final String CUSTOM_ERR_MESSAGE = "Access to the requested resource was denied";

        return new ResponseEntity<>(new CustomError(CUSTOM_ERR_MESSAGE, "AccessDenied"),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<CustomError> handleBindingErrors(
            MethodArgumentNotValidException ex,
            WebRequest req) {

        logHandler(ex, req);

        List<FieldErrorResponse> errs = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fe -> {
            errs.add(new FieldErrorResponse(fe.getField(), fe.getDefaultMessage()));
        });

        CustomError err = new CustomError("Fields are invalid", "InvalidRequestBodyException", errs);
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberFormatException.class)
    public final ResponseEntity<CustomError> handleNumberFormatException(
            NumberFormatException ex,
            WebRequest req) {

        logHandler(ex, req);

        final String CUSTOM_ERR_MESSAGE = "Number Format Exception";

        return new ResponseEntity<>(new CustomError(CUSTOM_ERR_MESSAGE, "NumberFormatException"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<CustomError> handleIllegalArgumentException(
            IllegalArgumentException ex,
            WebRequest req) {

        logHandler(ex, req);

        final String CUSTOM_ERR_MESSAGE = "Illegal argument";

        return new ResponseEntity<>(new CustomError(CUSTOM_ERR_MESSAGE, "IllegalArgumentException"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> catchAll(
            Exception ex,
            WebRequest req) {

        logHandler(ex, req);

        return new ResponseEntity<>(new CustomError("Server Exception", "Exception"), HttpStatus.BAD_REQUEST);
    }

    private void logHandler(Exception toLog, WebRequest req){
        logger.error(toLog.getMessage(), toLog);
        logger.info(
                req.getDescription(true),
                req.getHeaderNames(),
                req.getUserPrincipal()
        );

    }

}
