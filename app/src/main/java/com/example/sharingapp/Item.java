package com.example.sharingapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * Item class
 */
public final class Item {

    private String title;
    private String maker;
    private String description;
    private Dimensions dimensions;
    private String status;
    private Contact borrower;
    protected transient Bitmap image;
    protected String image_base64;
    private String id;

    public Item(final String title, final String maker, final String description,
                final Dimensions dimensions, final Bitmap image, final String id) {
        this.title = title;
        this.maker = maker;
        this.description = description;
        this.dimensions = dimensions;
        this.status = "Available";
        this.borrower = null;
        addImage(image);

        if (id == null) {
            setId();
        } else {
            updateId(id);
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId() {
        this.id = UUID.randomUUID().toString();
    }

    public void updateId(final String id) {
        this.id = id;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setMaker(final String maker) {
        this.maker = maker;
    }

    public String getMaker() {
        return this.maker;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDimensions(final Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    public Dimensions getDimensions() {
        return this.dimensions;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setBorrower(final Contact borrower) {
        this.borrower = borrower;
    }

    public Contact getBorrower() {
        return this.borrower;
    }

    public void addImage(final Bitmap new_image) {
        if (new_image != null) {
            this.image = new_image;
            final ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
            new_image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
            final byte[] b = byteArrayBitmapStream.toByteArray();
            this.image_base64 = Base64.encodeToString(b, Base64.DEFAULT);
        }
    }

    public Bitmap getImage() {
        if (this.image == null && this.image_base64 != null) {
            final byte[] decodeString = Base64.decode(this.image_base64, Base64.DEFAULT);
            this.image = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
        }
        return this.image;
    }
}

