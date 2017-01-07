package com.itinapi.itinerary;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by hamehta3 on 1/7/17.
 */
@XmlRootElement
public class ErrorMessage {
    private int status;
    private int code;
    private String message;

    public ErrorMessage() {}

    public ErrorMessage(int status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
