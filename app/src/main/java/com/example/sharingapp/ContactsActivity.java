package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Displays a list of all contacts
 */
public final class ContactsActivity extends AppCompatActivity {

    private final ContactList contact_list = new ContactList();
    private ListView my_contacts;
    private ArrayAdapter<Contact> adapter;
    private Context context;
    private final ItemList item_list = new ItemList();
    private final ContactList active_borrowers_list = new ContactList();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        this.context = getApplicationContext();
        this.contact_list.loadContacts(this.context);
        this.item_list.loadItems(this.context);
        this.my_contacts = findViewById(R.id.my_contacts);
        this.adapter = new ContactAdapter(ContactsActivity.this, this.contact_list.getContacts());
        this.my_contacts.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();
        // When contact is long clicked, this starts EditContactActivity
        this.my_contacts.setOnItemLongClickListener((parent, view, pos, id) -> {
            final Contact contact = ContactsActivity.this.adapter.getItem(pos);
            final ArrayList<Contact> active_borrowers = ContactsActivity.this.item_list.getActiveBorrowers();
            ContactsActivity.this.active_borrowers_list.setContacts(active_borrowers);
            // Prevent contact from editing an "active" borrower.
            if (ContactsActivity.this.active_borrowers_list.hasContact(contact)) {
                final CharSequence text = "Cannot edit or delete active borrower!";
                final int duration = Toast.LENGTH_SHORT;
                Toast.makeText(ContactsActivity.this.context, text, duration).show();
                return true;
            }
            ContactsActivity.this.contact_list.loadContacts(ContactsActivity.this.context); // Must load contacts again here
            final int meta_pos = ContactsActivity.this.contact_list.getIndex(contact);
            final Intent intent = new Intent(ContactsActivity.this.context, EditContactActivity.class);
            intent.putExtra("position", meta_pos);
            startActivity(intent);
            return true;
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.context = getApplicationContext();
        this.contact_list.loadContacts(this.context);
        this.my_contacts = findViewById(R.id.my_contacts);
        this.adapter = new ContactAdapter(ContactsActivity.this, this.contact_list.getContacts());
        this.my_contacts.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();
    }

    public void addContactActivity(final View view){
        final Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }

}
