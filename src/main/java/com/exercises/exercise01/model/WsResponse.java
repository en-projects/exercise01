package com.exercises.exercise01.model;

import com.exercises.exercise01.util.WsStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WsResponse implements Serializable {

    private int returnCode;

    private Integer errorCode;

    private String errorDescription;

    public WsResponse() {
    }

    public WsResponse(int returnCode) {
        this.returnCode = returnCode;
    }

    public WsResponse(int returnCode, Integer errorCode, String errorDescription) {
        this.returnCode = returnCode;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public static WsResponse ofSuccess() {
        return new WsResponse(WsStatus.OK.getId());
    }

    @Override
    public String toString() {
        return "WsResponse{" +
                "returnCode=" + returnCode +
                ", errorCode=" + errorCode +
                ", errorDescription='" + errorDescription + '\'' +
                '}';
    }
}
