
package com.recycler.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("aData")
    @Expose
    private AData aData;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AData getAData() {
        return aData;
    }

    public void setAData(AData aData) {
        this.aData = aData;
    }

}
