package com.pango.comunicaciones.model;

public class Vid_galeria {
    private String position;
    private String url;

    public Vid_galeria(String position, String url) {
        this.position = position;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
       int a=position.hashCode();
        return a;
    }
    public static Vid_galeria[] ITEMS = {
            new Vid_galeria("1","https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4"),
            new Vid_galeria("2","https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4"),
            new Vid_galeria("3","https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4"),
            new Vid_galeria("4","https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4"),

    };


    public static Vid_galeria getItem(int id) {
        for (Vid_galeria item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

}


/*
    public static Img_galeria[] ITEMS = {
            new Img_galeria("1", R.drawable.ima1),
            new Img_galeria("2", R.drawable.ima2),
            new Img_galeria("3", R.drawable.ima3),
            new Img_galeria("4", R.drawable.ima4),
            new Img_galeria("5", R.drawable.ima1),
            new Img_galeria("6", R.drawable.ima2),
            new Img_galeria("7", R.drawable.ima3),
            new Img_galeria("8", R.drawable.ima1),

    };
    public static Img_galeria getItem(int id) {
        for (Img_galeria item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
*/