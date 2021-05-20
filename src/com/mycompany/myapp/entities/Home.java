/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author Zeineb Haffar
 */
public class Home {
    private int id;
    private String title;
    private String img;
    private String img2;

    public Home() {
    }

    public Home(int id, String title, String img, String img2) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.img2 = img2;
    }

    public Home(String title, String img, String img2) {
        this.title = title;
        this.img = img;
        this.img2 = img2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Home other = (Home) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Home{" + "id=" + id + ", title=" + title + ", img=" + img + ", img2=" + img2 + '}';
    }
    
    
    
}
