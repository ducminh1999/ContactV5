package com.example.contact_v1;

import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.Serializable;

public class Contact implements Serializable {
    private String name;
    private String phone;
    private ImageView avatar;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Contact(){
        this.id = id;
        this.name = name;
        this.phone = phone;
    };
    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
