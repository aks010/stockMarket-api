package com.stockmarket.company.controller;

import com.stockmarket.company.exceptions.BadRequestException;
import com.stockmarket.company.entity.ExceptionJSONInfo;
import com.stockmarket.company.exceptions.InternalServerError;
import com.stockmarket.company.exceptions.RecordNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionController.class);

    @ExceptionHandler(value = { BadRequestException.class })
    public ResponseEntity<ExceptionJSONInfo> handleBadRequestException(BadRequestException ex) {
        logger.error(ex.getErrMsg());
        ExceptionJSONInfo errorResponse = new ExceptionJSONInfo();
        errorResponse.setMessage(ex.getErrMsg());
        errorResponse.setStatus(ex.getErrCode());
        return new ResponseEntity<ExceptionJSONInfo>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(value = { RecordNotFoundException.class })
    public ResponseEntity<ExceptionJSONInfo> handleRecordNotFoundException(RecordNotFoundException ex) {
        logger.error(ex.getErrMsg());
        ExceptionJSONInfo errorResponse = new ExceptionJSONInfo();
        errorResponse.setMessage(ex.getErrMsg());
        errorResponse.setStatus(ex.getErrCode());
        return new ResponseEntity<ExceptionJSONInfo>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(value = { InternalServerError.class })
    public ResponseEntity<ExceptionJSONInfo> handleInternalServerError(InternalServerError ex) {
        logger.error(ex.getErrMsg());
        ExceptionJSONInfo errorResponse = new ExceptionJSONInfo();
        errorResponse.setMessage(ex.getErrMsg());
        errorResponse.setStatus(ex.getErrCode());
        return new ResponseEntity<ExceptionJSONInfo>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ExceptionJSONInfo> handleException(Exception ex) {
        ex.printStackTrace();
        System.out.println(ex.getMessage());

        logger.error("Exception: ",ex.getMessage());
        ExceptionJSONInfo errorResponse = new ExceptionJSONInfo();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<ExceptionJSONInfo>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
