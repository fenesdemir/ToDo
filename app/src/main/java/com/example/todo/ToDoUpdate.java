package com.example.todo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ToDoUpdate {
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

    public ToDoUpdate(String title, Boolean reminder, String date, Boolean isDone, String icon, String color) {
        this.title = title;
        this.reminder = reminder;
        this.date = date;
        this.isDone = isDone;
        this.icon = icon;
        this.color = color;
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

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
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
}
