package com.shojib.asoftbd.adust;

import java.io.Serializable;

/**
 * Created by Shopno-Shomu on 17-02-16.
 */
public class Notifications implements Serializable{
    String date;
    String description;
    String id;
    String title;

    public Notifications(String date, String description, String id, String title) {
        this.date = date;
        this.description = description;
        this.id = id;
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
