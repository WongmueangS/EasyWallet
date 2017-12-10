package com.example.easywallet.model;

/**
 * Created by ADMIN on 10/12/2560.
 */

public class ListItem {
    public final int id;
    public final String name;
    public final String money;
    public final String type;
    public final String picture;

    public ListItem(int id, String name, String money, String type,String picture) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.type = type;
        this.picture = picture;
    }

    @Override
    public String toString() {
        return name;
    }
}
