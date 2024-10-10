package com.example.zipperlock.ui.item.sound.model;

public class Sound {
    private int img;
    private int name;
    private int color_item;
    private int color_list;
    private int sound;
    private String type;

    public Sound(int img, int name, int color_item, int color_list, int sound, String type) {
        this.img = img;
        this.name = name;
        this.color_item = color_item;
        this.color_list = color_list;
        this.sound = sound;
        this.type = type;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getColor_item() {
        return color_item;
    }

    public void setColor_item(int color_item) {
        this.color_item = color_item;
    }

    public int getColor_list() {
        return color_list;
    }

    public void setColor_list(int color_list) {
        this.color_list = color_list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
