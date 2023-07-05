package com.example.juda.info;

import java.net.URL;

public class PostingInfo {

    int uid;
    String title;
    String sub_title;
    String detail_contents;
    String writer;
    URL img;
    String time;
    String main_tag;
    String sub_tag;

    public PostingInfo(int uid, String title, String sub_title, String detail_contents, String writer, URL img, String time, String main_tag, String sub_tag) {

        this.uid = uid;
        this.title = title;
        this.sub_title = sub_title;
        this.detail_contents = detail_contents;
        this.writer = writer;
        this.img = img;
        this.time = time;
        this.main_tag = main_tag;
        this.sub_tag = sub_tag;
    }

    public PostingInfo(String title, String sub_title, String detail_contents) {
        this.title = title;
        this.sub_title = sub_title;
        this.detail_contents = detail_contents;
    }

    public PostingInfo() {

    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getDetail_contents() {
        return detail_contents;
    }

    public void setDetail_contents(String detail_contents) {
        this.detail_contents = detail_contents;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public URL getImg() {
        return img;
    }

    public void setImg(URL img) {
        this.img = img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMain_tag() {
        return main_tag;
    }

    public void setMain_tag(String main_tag) {
        this.main_tag = main_tag;
    }

    public String getSub_tag() {
        return sub_tag;
    }

    public void setSub_tag(String sub_tag) {
        this.sub_tag = sub_tag;
    }
}
