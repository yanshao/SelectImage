package com.yanshao.selectimagelib.bean;

import android.graphics.Bitmap;

public class ImageBean {

    String path;
    Bitmap bitmap;
    boolean ckeck;

    public boolean isCkeck() {
        return ckeck;
    }

    public void setCkeck(boolean ckeck) {
        this.ckeck = ckeck;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
