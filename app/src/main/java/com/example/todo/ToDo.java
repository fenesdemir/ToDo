package com.example.todo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ToDo {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("reminder")
    @Expose
    private Boolean reminder;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("isDone")
    @Expose
    private Boolean isDone;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public ToDo(String id, String title, Boolean reminder, String date, Boolean isDone, String icon, String color, String user, Integer v) {
        this.id = id;
        this.title = title;
        this.reminder = reminder;
        this.date = date;
        this.isDone = isDone;
        this.icon = icon;
        this.color = color;
        this.user = user;
        this.v = v;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getReminder() {
        return reminder;
    }

    public void setReminder(Boolean reminder) {
        this.reminder = reminder;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
