package com.stockmarket.company.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    private String errMsg;
    private HttpStatus errCode = HttpStatus.BAD_REQUEST;

    public BadRequestException(){
        super("Bad Request Error");
        this.errMsg = "Bad Request Error";
    };

    public BadRequestException(String message) {
        super(message);
        this.errMsg = message;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public HttpStatus getErrCode() {
        return errCode;
    }

}
