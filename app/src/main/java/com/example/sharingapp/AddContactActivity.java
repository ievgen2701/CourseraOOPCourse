package com.example.sharingapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Add a new contact
 */
public class AddContactActivity extends AppCompatActivity {
    private ContactList contact_list = new ContactList();
    private Context context;
    private EditText username;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        this.username = findViewById(R.id.username);
        this.email = findViewById(R.id.email);
        this.context = getApplicationContext();
        this.contact_list.loadContacts(this.context);
    }

    public void saveContact(View view) {
        String username_str = this.username.getText().toString();
        String email_str = this.email.getText().toString();
        if (username_str.equals("")) {
            this.username.setError("Empty field!");
            return;
        }
        if (email_str.equals("")) {
            this.email.setError("Empty field!");
            return;
        }
        if (!email_str.contains("@")) {
            this.email.setError("Must be an email address!");
            return;
        }
        if (!this.contact_list.isUsernameAvailable(username_str)) {
            this.username.setError("Username already taken!");
            return;
        }
        Contact contact = new Contact(username_str, email_str, null);
        this.contact_list.addContact(contact);
        this.contact_list.saveContacts(this.context);
        // End AddContactActivity
        finish();
    }
}

