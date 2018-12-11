package com.example.customview.adview;

/**
 * Created by lvqiu on 2018/10/9.
 */

public class ContentBean {
    private String imageurl;
    private String title;
    private String key;

    public ContentBean(String imageurl, String title) {
        this.imageurl = imageurl;
        this.title = title;
    }

    public ContentBean(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
