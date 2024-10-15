package com.example.zipperlock.ui.item.row.model;

public class Row {

    private int img;
    private int img_full;
    private int img_right;
    private int img_left;

    public Row(int img, int img_full, int img_right, int img_left) {
        this.img = img;
        this.img_full = img_full;
        this.img_right = img_right;
        this.img_left = img_left;
    }


    public int getImg_full() {
        return img_full;
    }

    public void setImg_full(int img_full) {
        this.img_full = img_full;
    }

    public int getImg_right() {
        return img_right;
    }

    public void setImg_right(int img_right) {
        this.img_right = img_right;
    }

    public int getImg_left() {
        return img_left;
    }

    public void setImg_left(int img_left) {
        this.img_left = img_left;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
