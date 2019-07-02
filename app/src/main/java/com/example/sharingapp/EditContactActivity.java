package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Editing a pre-existing contact consists of deleting the old contact and adding a new
 * contact with the old
 * contact's id.
 * Note: You will not be able contacts which are "active" borrowers
 */
public final class EditContactActivity extends AppCompatActivity {

    private final ContactList contact_list = new ContactList();
    private Contact contact;
    private EditText email;
    private EditText username;
    private Context context;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        this.context = getApplicationContext();
        this.contact_list.loadContacts(this.context);
        final Intent intent = getIntent();
        final int pos = intent.getIntExtra("position", 0);
        this.contact = this.contact_list.getContact(pos);
        this.username = findViewById(R.id.username);
        this.email = findViewById(R.id.email);
        this.username.setText(this.contact.getUsername());
        this.email.setText(this.contact.getEmail());
    }

    public void saveContact(final View view) {
        final String email_str = this.email.getText().toString();
        if (email_str.equals("")) {
            this.email.setError("Empty field!");
            return;
        }
        if (!email_str.contains("@")) {
            this.email.setError("Must be an email address!");
            return;
        }
        final String username_str = this.username.getText().toString();
        final String id = this.contact.getId(); // Reuse the contact id
        // Check that username is unique AND username is changed (Note: if username was
        not changed
        // then this should be fine, because it was already unique.)
        if (!this.contact_list.isUsernameAvailable(username_str) &&
                !(this.contact.getUsername().equals(username_str))) {
            this.username.setError("Username already taken!");
            return;
        }
        final Contact updated_contact = new Contact(username_str, email_str, id);
        this.contact_list.deleteContact(this.contact);
        this.contact_list.addContact(updated_contact);
        this.contact_list.saveContacts(this.context);
        // End EditContactActivity
        finish();
    }

    public void deleteContact(final View view) {
        this.contact_list.deleteContact(this.contact);
        this.contact_list.saveContacts(this.context);
        // End EditContactActivity
        finish();
    }
}

