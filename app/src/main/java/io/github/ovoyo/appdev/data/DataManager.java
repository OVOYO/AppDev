package io.github.ovoyo.appdev.data;


import javax.inject.Inject;

public class DataManager {

    public static final String TAG = "DataManager";

    private String clzName;

    @Inject
    public DataManager(String clzName) {
        this.clzName = clzName;
    }

    public String getClzName() {
        return clzName;
    }
}
