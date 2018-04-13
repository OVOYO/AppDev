package io.github.ovoyo.appdev.data;


public class Feed {

    private String lastId;

    private String title;

    private String desc;

    private String from;

    private long time;

    public Feed() {
    }

    public Feed(String lastId, String title, String desc, String from, long time) {
        this.lastId = lastId;
        this.title = title;
        this.desc = desc;
        this.from = from;
        this.time = time;
    }

    public Feed(Feed feed) {
        this.lastId = feed.getLastId();
        this.title = feed.getTitle();
        this.desc = feed.getDesc();
        this.from = feed.getFrom();
        this.time = feed.getTime();
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
