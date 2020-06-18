package com.example.apiarymange.Adapter;

public class CustomItemSpinner{

    private String spinnerItemName;
    private int spinnerItemImage;

    public CustomItemSpinner(String spinnerItemName, int spinnerItemImage) {
        this.spinnerItemName = spinnerItemName;
        this.spinnerItemImage = spinnerItemImage;
    }

    public String getSpinnerItemName() {
        return spinnerItemName;
    }

    public int getSpinnerItemImage() {
        return spinnerItemImage;
    }
}