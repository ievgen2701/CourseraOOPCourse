package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Add a new item
 */
public final class AddItemActivity extends AppCompatActivity {

    private EditText title;
    private EditText maker;
    private EditText description;
    private EditText length;
    private EditText width;
    private EditText height;

    private ImageView photo;
    private Bitmap image;
    private final int REQUEST_CODE = 1;

    private final ItemList item_list = new ItemList();
    private Context context;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        this.title = findViewById(R.id.title);
        this.maker = findViewById(R.id.maker);
        this.description = findViewById(R.id.description);
        this.length = findViewById(R.id.length);
        this.width = findViewById(R.id.width);
        this.height = findViewById(R.id.height);
        this.photo = findViewById(R.id.image_view);

        this.photo.setImageResource(android.R.drawable.ic_menu_gallery);

        this.context = getApplicationContext();
        this.item_list.loadItems(this.context);
    }

    public void saveItem (final View view) {

        final String title_str = this.title.getText().toString();
        final String maker_str = this.maker.getText().toString();
        final String description_str = this.description.getText().toString();
        final String length_str = this.length.getText().toString();
        final String width_str = this.width.getText().toString();
        final String height_str = this.height.getText().toString();

        if (title_str.equals("")) {
            this.title.setError("Empty field!");
            return;
        }

        if (maker_str.equals("")) {
            this.maker.setError("Empty field!");
            return;
        }

        if (description_str.equals("")) {
            this.description.setError("Empty field!");
            return;
        }

        if (length_str.equals("")) {
            this.length.setError("Empty field!");
            return;
        }

        if (width_str.equals("")) {
            this.width.setError("Empty field!");
            return;
        }

        if (height_str.equals("")) {
            this.height.setError("Empty field!");
            return;
        }

        final Dimensions dimensions = new Dimensions(length_str, width_str, height_str);
        final Item item = new Item(title_str, maker_str, description_str, dimensions, this.image, null );

        this.item_list.addItem(item);
        this.item_list.saveItems(this.context);

        // End AddItemActivity
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addPhoto(final View view) {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, this.REQUEST_CODE);
        }
    }

    public void deletePhoto(final View view) {
        this.image = null;
        this.photo.setImageResource(android.R.drawable.ic_menu_gallery);
    }

    @Override
    protected void onActivityResult(final int request_code, final int result_code, final Intent intent){
        if (request_code == this.REQUEST_CODE && result_code == RESULT_OK){
            final Bundle extras = intent.getExtras();
            this.image = (Bitmap) extras.get("data");
            this.photo.setImageBitmap(this.image);
        }
    }
}