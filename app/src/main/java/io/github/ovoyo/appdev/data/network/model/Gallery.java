package io.github.ovoyo.appdev.data.network.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gallery {

    @Expose
    @SerializedName("titleText")
    private String titleText;

    @Expose
    @SerializedName("imgUrl")
    private String imgUrl;

    @Expose
    @SerializedName("article_obj_id")
    private String articleObjId;

    @Expose
    @SerializedName("showTime")
    private String showTime;

    @Expose
    @SerializedName("showType")
    private int showType;

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getArticleObjId() {
        return articleObjId;
    }

    public void setArticleObjId(String articleObjId) {
        this.articleObjId = articleObjId;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }
}
