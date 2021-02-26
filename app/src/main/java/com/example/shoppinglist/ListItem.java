package com.example.shoppinglist;

public class ListItem extends parentItem{
    private String name = "Default Name";
    private double price = 0.0;
    private Boolean purchased = false;
    private String description = "None";
    private String type = "Food";
    private Boolean expanded = false;
    public ListItem(){
        super();

    }
    public ListItem(String name, double price, Boolean purchased, String description,String type){
        super();
        this.name = name;
        this.price = price;
        this.purchased = purchased;
        this.description = description;
        this.type = type;


    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPurchased(Boolean purchased) {
        this.purchased = purchased;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    public String getPriceInFormat(){
        return "$"+price;
    }

    public Boolean getPurchased() {
        return purchased;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }
}
