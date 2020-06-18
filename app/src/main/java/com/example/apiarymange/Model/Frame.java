package com.example.apiarymange.Model;

public class Frame {

    private String reference;
    private Long value;

    public Frame() {
    }

    public Frame(String reference, Long value) {

        this.reference = reference;
        this.value = value;
    }


    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
