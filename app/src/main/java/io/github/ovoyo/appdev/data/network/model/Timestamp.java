package io.github.ovoyo.appdev.data.network.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timestamp {

    @Expose
    @SerializedName("timestamp")
    private String timestamp;

    @Expose
    @SerializedName("datetime")
    private String datetime;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
