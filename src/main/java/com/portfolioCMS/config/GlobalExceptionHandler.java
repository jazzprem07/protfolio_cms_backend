/**
 *
 * Author: Sathish K (000464)
 * Date: 01-Jul-2025
 *
 * Copyright (c) 2025 Caplin Point Laboratories Limited. All rights reserved.
 *
 */

package com.portfolioCMS.config;


import com.portfolioCMS.dto.ResponseDTO;
import com.portfolioCMS.exceptions.*;
import com.portfolioCMS.service.general.IGeneralFunctionUtils;
import com.portfolioCMS.service.general.impl.UserContextService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private UserContextService userContextService;
    private IGeneralFunctionUtils generalFunctionUtils;
    private static String reqURL = "Request URL: {}";

    public GlobalExceptionHandler(
            UserContextService userContextService,
            IGeneralFunctionUtils generalFunctionUtils) {
        super();
        this.userContextService = userContextService;
        this.generalFunctionUtils = generalFunctionUtils;
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ResponseDTO> handleValidationExceptions(HandlerMethodValidationException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        List<String> errors = new ArrayList<>();
        ex.getAllErrors().forEach(error -> {
            String message = error.getDefaultMessage();
            errors.add(message);
        });
        String errorStr = errors.toString();
        logger.error("HandlerMethodValidationException => {}", errorStr);
        ResponseDTO errorResponse = new ResponseDTO(false, errorStr);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        String errorStr = generalFunctionUtils.convertToJson(errors);
        logger.error("MethodArgumentNotValidException => {}", errorStr);
        ResponseDTO errorResponse = new ResponseDTO(false, errorStr);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenRelatedException.class)
    public ResponseEntity<ResponseDTO> handleJwtException(TokenRelatedException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        ResponseDTO errorResponse = new ResponseDTO(false, "Token has expired or is invalid. Please log in again.");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(OTPRelatedException.class)
    public ResponseEntity<ResponseDTO> handleOTPRelatedException(OTPRelatedException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        String errorMsg = ex.getMessage();
        logger.error(reqURL, requestURL);
        ResponseDTO errorResponse = new ResponseDTO(false, errorMsg);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseDTO> handleJwtException(ExpiredJwtException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        ResponseDTO errorResponse = new ResponseDTO(false, "Token has expired or is invalid. Please log in again.");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Unauthorized.class)
    public ResponseEntity<ResponseDTO> handleUnauthorized(Unauthorized ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        ResponseDTO errorResponse = new ResponseDTO(false, "Unauthorized");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseDTO> handleNotFound(NoResourceFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDTO(false, ex.getMessage(), null));
    }
//    @ExceptionHandler(CustomSQLException.class)
//    public ResponseEntity<ResponseDTO> handleException(CustomSQLException ex, HttpServletRequest request) {
//        String requestURL = request.getRequestURL().toString();
//        logger.error(reqURL, requestURL);
//        logger.error("CustomSQLException : SQL query error! => {}", ex.getMessage());
//        String errorMsg = ex.getMessage();
//        ResponseDTO errorResponse = new ResponseDTO(false, "Internal server error!");
//        if (errorMsg.contains(SQLConstantVariable.DUPLICATE_KEY)) {
//            logger.warn("Duplicate Data Found!");
//            errorResponse = new ResponseDTO(false, "Duplicate Data Found!");
//        } else if (errorMsg.contains(SQLConstantVariable.NO_PARAMETER)) {
//            logger.warn("Parameter missing! Eg: (?, ?)");
//        }
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseDTO> handleException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        logger.error("HttpRequestMethodNotSupportedException => {}", ex.getBody().getDetail());
        ResponseDTO errorResponse = new ResponseDTO(false, ex.getBody().getDetail());
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ResponseDTO> handleException(InternalAuthenticationServiceException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        logger.error("InternalAuthenticationServiceException => {}", ex.getMessage());
        ResponseDTO errorResponse = new ResponseDTO(false, "Required field value is Null/Empty!");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ResponseDTO> handleServletException(ServletException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        logger.error("ServletException => {}", ex.getMessage());
        ResponseDTO errorResponse = new ResponseDTO(false, "Invalid Request!");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseDTO> handleServletException(IOException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        logger.error("IOException => {}", ex.getMessage());
        ResponseDTO errorResponse = new ResponseDTO(false, "Invalid Input!");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ResponseDTO> handleResponseStatusException(HttpServerErrorException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        logger.error("HttpServerErrorException => {}", ex.getMessage());
        ResponseDTO errorResponse = new ResponseDTO(false, "Invalid Input!");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleResponseStatusException(UsernameNotFoundException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        logger.error("UsernameNotFoundException => {}", ex.getMessage());
        ResponseDTO errorResponse = new ResponseDTO(false, "User Not Found!");
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(ResetPasswordException.class)
    public ResponseEntity<ResponseDTO> handlException(ResetPasswordException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        logger.error("ResetPasswordException => {}", ex.getMessage());
        ResponseDTO errorResponse = new ResponseDTO(false, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseDTO> handlException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        logger.error("MethodArgumentTypeMismatchException => {}", ex.getMessage());
        ResponseDTO errorResponse = new ResponseDTO(false, "Argument Type Mismatch!");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseDTO> handlException(MissingServletRequestParameterException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        logger.error("MissingServletRequestParameterException => {}", ex.getBody().getDetail());
        ResponseDTO errorResponse = new ResponseDTO(false, ex.getBody().getDetail());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomInternalServerException.class)
    public ResponseEntity<ResponseDTO> handlException(CustomInternalServerException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        logger.error("CustomInternalServerException => {}", ex.getMessage());
        ResponseDTO errorResponse = new ResponseDTO(false, "Data Not Found!");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(AuthorizationDeniedException.class)
//    public ResponseEntity<ResponseDTO> handlAuthorizationDeniedException(AuthorizationDeniedException ex, HttpServletRequest request) {
//        String requestURL = request.getRequestURL().toString();
//        logger.error(reqURL, requestURL);
//        String msg = new StringJoiner(" - ").add(ConstantVariable.LOGGER_ERROR).add(ex.getMessage())
//                .add(userContextService.getNameFromContext()).add(ex.getAuthorizationResult().toString())
//                .add(request.getMethod()).add(request.getRequestURI()).toString();
//        logger.error(msg);
//        String currentUserRole = userContextService.getCurrentAllUserRole().toString();
//        logger.info("currentUserRole is {}", currentUserRole);
//        ResponseDTO errorResponse = new ResponseDTO(false, ex.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
//    }

    @ExceptionHandler(AccountIsInActiveException.class)
    public ResponseEntity<ResponseDTO> handleGenericException(AccountIsInActiveException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        logger.error("AccountIsInActiveException => {}", ex.getMessage());
        String msg = ex.getMessage();
        String errorMsg = "Your account has been blocked!";
        if (msg.equals("inactive")) {
            errorMsg = "Account has been deactivated.";
        }
        ResponseDTO errorResponse = new ResponseDTO(false, errorMsg);
        return new ResponseEntity<>(errorResponse, HttpStatus.LOCKED);
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseDTO> handleJwtException(NullPointerException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        ResponseDTO errorResponse = new ResponseDTO(false, "Internal Error!");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationErrorException.class)
    public ResponseEntity<ResponseDTO> handleJwtException(ValidationErrorException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        String errorMsg = ex.getMessage();
        ResponseDTO errorResponse = new ResponseDTO(false, errorMsg);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(EmptyResultDataAccessException.class)
//    public ResponseEntity<ResponseDTO> handleJwtException(EmptyResultDataAccessException ex, HttpServletRequest request) {
//        String requestURL = request.getRequestURL().toString();
//        logger.error(reqURL, requestURL);
//        ResponseDTO errorResponse = new ResponseDTO(false, ConstantVariable.NO_RECORD_FOUND);
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO> handleJwtException(IllegalArgumentException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        ResponseDTO errorResponse = new ResponseDTO(false, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleJwtException(DataNotFoundException ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        ResponseDTO errorResponse = new ResponseDTO(false, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleGenericException(Exception ex, HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        logger.error(reqURL, requestURL);
        logger.error("Exception => {}", ex.getMessage());
        ResponseDTO errorResponse = new ResponseDTO(false, "An unexpected error occurred");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> handleNotFound(NoResourceFoundException ex, HttpServletRequest request) throws NoResourceFoundException {
        String path = request.getRequestURI();

        // ✅ Skip Swagger paths so Spring can handle them
        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
            throw ex; // rethrow → Spring will handle static resources
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Resource not found: " + path);
    }
}