package com.example.rssreader.API.Data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Root(name = "item", strict = false)
public class News implements Serializable {
    @Element(name = "title")
    private String title;
    @Element(name = "link")
    private String link;
    @Element(name = "pubDate")
    private String data;
    @Element(name = "description")
    private String descriprion;
    @Element(name = "encoded", data = true)
    private String content;
    private String img;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public News(String title, String link, String data, String descriprion, String content) {
        this.title = title;
        this.link = link;
        this.data = data;
        this.descriprion = descriprion;
        this.content = content;
    }

    public News() {

    }

    public News(String title, String link, String data, String descriprion) {
        this.title = title;
        this.link = link;
        this.data = data;
        this.descriprion = descriprion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescriprion() {
        return descriprion;
    }

    public void setDescriprion(String descriprion) {
        this.descriprion = descriprion;
    }

    private String initImgList() {
        String pattern = "src=\"([^\"]+)\"";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(descriprion);
        m.find();
        return m.group(1);
    }

    public String getImg(int widt) {
        if (img == null)
            img = initImgList();
        return img;
    }
}
