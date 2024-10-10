package com.example.zipperlock.ui.item.sound.model;

public class SoundType {
    private int title;
    private int content;
    private int color;
    private int img;

    public SoundType(int title, int content, int color, int img) {
        this.title = title;
        this.content = content;
        this.color = color;
        this.img = img;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
