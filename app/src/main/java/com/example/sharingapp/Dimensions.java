package com.example.sharingapp;

/**
 * Dimensions class
 */
public final class Dimensions {

    private String length;
    private String width;
    private String height;

    public Dimensions(final String length, final String width, final String height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public String getLength() {
        return this.length;
    }

    public String getWidth() {
        return this.width;
    }

    public String getHeight() {
        return this.height;
    }

    public String getDimensions() {
        return this.length + " x " + this.width + " x " + this.height;
    }

    public void setDimensions(final String length, final String width, final String height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }
}
