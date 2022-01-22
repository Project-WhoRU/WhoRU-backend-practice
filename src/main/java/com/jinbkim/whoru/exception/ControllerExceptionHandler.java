package com.jinbkim.whoru.exception;

import com.jinbkim.whoru.exception.customexceptions.CustomException;
import com.jinbkim.whoru.exception.customexceptions.NotnullException;
import com.jinbkim.whoru.exception.error.ErrorCode;
import com.jinbkim.whoru.exception.error.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(NotnullException.class)
    protected ResponseEntity<ErrorResponse> handleFoundNullValueException(NotnullException e) {
        logger.error("NotnullException", e);

        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(errorCode.getStatus())
            .message(errorCode.getMessage())
            .build();
        errorResponse.setCustomFieldErrorList(e.getErrors());
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorCode.getStatus()));
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse>handleCustomException(CustomException e) {
        logger.error("handleCustomException", e);

        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(errorCode.getStatus())
            .message(errorCode.getMessage())
            .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorCode.getStatus()));
    }
}
