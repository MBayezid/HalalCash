package com.mb_lab.halal_cash.DataModels;

import java.io.Serializable;

public class SimpleResponse  implements Serializable {
    private String message;

    private Boolean status;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
