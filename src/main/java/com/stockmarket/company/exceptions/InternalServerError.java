package com.stockmarket.company.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerError extends RuntimeException {
    private String errMsg;
    private final HttpStatus errCode = HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalServerError(){
        super("Internal Server Error");
        this.errMsg = "Internal Server Error";
    };

    public InternalServerError(String message) {
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
