package com.example.shoppinglist;

public class descriptionItem extends parentItem {
    private String description;
    public descriptionItem(String description){
        super();
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
