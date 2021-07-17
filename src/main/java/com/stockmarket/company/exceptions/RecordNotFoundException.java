package com.stockmarket.company.exceptions;

import org.springframework.http.HttpStatus;

public class RecordNotFoundException extends RuntimeException {
    private String errMsg;
    private final HttpStatus errCode = HttpStatus.NOT_FOUND;

    public RecordNotFoundException(){
        super("Record Not Found Error");
        this.errMsg = "Record Not Found Error";
    };

    public RecordNotFoundException(String message) {
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
