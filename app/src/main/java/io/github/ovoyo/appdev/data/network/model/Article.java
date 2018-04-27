package io.github.ovoyo.appdev.data.network.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article {

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("desc")
    private String desc;

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("content")
    private String urlContent;

    @Expose
    @SerializedName("imgUrl")
    private String img;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlContent() {
        return urlContent;
    }

    public void setUrlContent(String urlContent) {
        this.urlContent = urlContent;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
