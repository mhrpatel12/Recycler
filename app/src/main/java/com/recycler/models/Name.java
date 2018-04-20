
package com.recycler.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Name {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("information")
    @Expose
    private Information information;
    @SerializedName("lastlogin")
    @Expose
    private String lastlogin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }

}
