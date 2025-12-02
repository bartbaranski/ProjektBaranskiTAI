package pl.baranski.model;

import java.io.Serializable;

public class MenuItem implements Serializable {
    private String itemName;
    private String itemParam;

    public MenuItem() {}

    public MenuItem(String itemName, String itemParam) {
        this.itemName = itemName;
        this.itemParam = itemParam;
    }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public String getItemParam() { return itemParam; }
    public void setItemParam(String itemParam) { this.itemParam = itemParam; }
}