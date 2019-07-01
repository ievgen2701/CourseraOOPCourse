package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Displays a list of all contacts
 */
public final class ContactsActivity extends AppCompatActivity {

    private ContactList contact_list = new ContactList();
    private ListView my_contacts;
    private ArrayAdapter<Contact> adapter;
    private Context context;
    private ItemList item_list = new ItemList();
    private ContactList active_borrowers_list = new ContactList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        this.my_contacts.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                Contact contact = ContactsActivity.this.adapter.getItem(pos);
                ArrayList<Contact> active_borrowers = ContactsActivity.this.item_list.getActiveBorrowers();
                ContactsActivity.this.active_borrowers_list.setContacts(active_borrowers);
                // Prevent contact from editing an "active" borrower.
                if (ContactsActivity.this.active_borrowers_list != null) {
                    if (ContactsActivity.this.active_borrowers_list.hasContact(contact)) {
                        CharSequence text = "Cannot edit or delete active borrower!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(ContactsActivity.this.context, text, duration).show();
                        return true;
                    }
                }
                ContactsActivity.this.contact_list.loadContacts(ContactsActivity.this.context); // Must load contacts again here
                int meta_pos = ContactsActivity.this.contact_list.getIndex(contact);
                Intent intent = new Intent(ContactsActivity.this.context, EditContactActivity.class);
                intent.putExtra("position", meta_pos);
                startActivity(intent);
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.context = getApplicationContext();
        this.contact_list.loadContacts(this.context);
        this.my_contacts = (ListView) findViewById(R.id.my_contacts);
        this.adapter = new ContactAdapter(ContactsActivity.this, this.contact_list.getContacts());
        this.my_contacts.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();
    }

    public void addContactActivity(View view){
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }

}
