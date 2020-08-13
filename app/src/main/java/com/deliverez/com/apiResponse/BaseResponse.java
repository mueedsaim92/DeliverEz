package com.deliverez.com.apiResponse;


import java.io.Serializable;

public class BaseResponse implements Serializable {

    public int Status;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
