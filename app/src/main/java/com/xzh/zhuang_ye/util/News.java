package com.xzh.zhuang_ye.util;

/**
 * Created by 谢植焕 on 2018/6/14.
 */

public class News {
    private String title;
    private String description;
    private String image;
    private String type;
    private String comment;

    public String getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setType(String type) {
        this.type = type;

    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
