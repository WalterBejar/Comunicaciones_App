package com.pango.comunicaciones.model;

public class ItemSlideMenu {

    private int imgId;
    private String menu_tit;

    public ItemSlideMenu(int imgId, String menu_tit) {
        this.imgId = imgId;
        this.menu_tit = menu_tit;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }


    public String getMenu_tit() {
        return menu_tit;
    }

    public void setMenu_tit(String menu_tit) {

        this.menu_tit = menu_tit;
    }
}
