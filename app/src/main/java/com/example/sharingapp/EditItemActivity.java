package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Editing a pre-existing item consists of deleting the old item and adding a new item with the old
 * item's id.
 */
public final class EditItemActivity extends AppCompatActivity {

    private final ItemList item_list = new ItemList();
    private Item item;
    private Context context;

    private final ContactList contact_list = new ContactList();

    private Bitmap image;
    private final int REQUEST_CODE = 1;
    private ImageView photo;

    private EditText title;
    private EditText maker;
    private EditText description;
    private EditText length;
    private EditText width;
    private EditText height;
    private Spinner borrower_spinner;
    private TextView borrower_tv;
    private Switch status;
    private EditText invisible;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        this.title = findViewById(R.id.title);
        this.maker = findViewById(R.id.maker);
        this.description = findViewById(R.id.description);
        this.length = findViewById(R.id.length);
        this.width = findViewById(R.id.width);
        this.height = findViewById(R.id.height);
        this.borrower_spinner = findViewById(R.id.borrower_spinner);
        this.borrower_tv = findViewById(R.id.borrower_tv);
        this.photo = findViewById(R.id.image_view);
        this.status = findViewById(R.id.available_switch);
        this.invisible = findViewById(R.id.invisible);
        this.invisible.setVisibility(View.GONE);
        this.context = getApplicationContext();
        this.item_list.loadItems(this.context);
        this.contact_list.loadContacts(this.context);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                this.contact_list.getAllUsernames());
        this.borrower_spinner.setAdapter(adapter);

        final Intent intent = getIntent(); // Get intent from ItemsFragment
        final int pos = intent.getIntExtra("position", 0);

        this.item = this.item_list.getItem(pos);

        final Contact contact = this.item.getBorrower();
        if (contact != null) {
            final int contact_pos = this.contact_list.getIndex(contact);
            this.borrower_spinner.setSelection(contact_pos);
        }


        this.title.setText(this.item.getTitle());
        this.maker.setText(this.item.getMaker());
        this.description.setText(this.item.getDescription());

        final Dimensions dimensions = this.item.getDimensions();

        this.length.setText(dimensions.getLength());
        this.width.setText(dimensions.getWidth());
        this.height.setText(dimensions.getHeight());

        final String status_str = this.item.getStatus();
        if (status_str.equals("Borrowed")) {
            this.status.setChecked(false);
        } else {
            this.borrower_tv.setVisibility(View.GONE);
            this.borrower_spinner.setVisibility(View.GONE);
        }

        this.image = this.item.getImage();
        if (this.image != null) {
            this.photo.setImageBitmap(this.image);
        } else {
            this.photo.setImageResource(android.R.drawable.ic_menu_gallery);
        }
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
    protected void onActivityResult(final int request_code, final int result_code, final Intent intent) {
        if (request_code == this.REQUEST_CODE && result_code == RESULT_OK) {
            final Bundle extras = intent.getExtras();
            this.image = (Bitmap) extras.get("data");
            this.photo.setImageBitmap(this.image);
        }
    }

    public void deleteItem(final View view) {
        this.item_list.deleteItem(this.item);
        this.item_list.saveItems(this.context);

        // End EditItemActivity
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void saveItem(final View view) {

        final String title_str = this.title.getText().toString();
        final String maker_str = this.maker.getText().toString();
        final String description_str = this.description.getText().toString();
        final String length_str = this.length.getText().toString();
        final String width_str = this.width.getText().toString();
        final String height_str = this.height.getText().toString();

        Contact contact = null;
        if (!this.status.isChecked()) {
            final String borrower_str = this.borrower_spinner.getSelectedItem().toString();
            contact = this.contact_list.getContactByUsername(borrower_str);
        }

        final Dimensions dimensions = new Dimensions(length_str, width_str, height_str);

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

        // Reuse the item id
        final String id = this.item.getId();
        this.item_list.deleteItem(this.item);

        final Item updated_item = new Item(title_str, maker_str, description_str, dimensions, this.image, id);

        final boolean checked = this.status.isChecked();
        if (!checked) {
            updated_item.setStatus("Borrowed");
            updated_item.setBorrower(contact);
        }
        this.item_list.addItem(updated_item);

        this.item_list.saveItems(this.context);

        // End EditItemActivity
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Checked = Available
     * Unchecked = Borrowed
     */
    public void toggleSwitch(final View view) {
        if (this.status.isChecked()) {
            // Means was previously borrowed, switch was toggled to available
            this.borrower_spinner.setVisibility(View.GONE);
            this.borrower_tv.setVisibility(View.GONE);
            this.item.setBorrower(null);
            this.item.setStatus("Available");
        } else {
            // Means not borrowed
            if (this.contact_list.getSize() == 0) {
                // No contacts, need to add contacts to be able to add a borrower.
                this.invisible.setEnabled(false);
                this.invisible.setVisibility(View.VISIBLE);
                this.invisible.requestFocus();
                this.invisible.setError("No contacts available! Must add borrower to contacts.");
                this.status.setChecked(true); // Set switch to available
            } else {
                // Means was previously available
                this.borrower_spinner.setVisibility(View.VISIBLE);
                this.borrower_tv.setVisibility(View.VISIBLE);
            }
        }
    }
}