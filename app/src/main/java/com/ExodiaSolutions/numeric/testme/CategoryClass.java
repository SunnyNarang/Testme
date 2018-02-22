package com.ExodiaSolutions.numeric.testme;

/**
 * Created by Sunny on 09-01-2017.
 */

public class CategoryClass {
    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;
    public void setDesc(String desc) {
        this.desc = desc;
    }

    String name;

    public CategoryClass(String name, String desc, String icon,String id) {
        this.name = name;
        this.id=id;
        this.desc = desc;
        this.icon = icon;
    }

    String desc;



    String icon;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
