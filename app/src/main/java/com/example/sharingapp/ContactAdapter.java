package com.example.sharingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * ContactAdapter is responsible for what information is displayed in ListView entries.
 */
public final class ContactAdapter extends ArrayAdapter<Contact> {

    private final LayoutInflater inflater;
    private final Context context;

    public ContactAdapter(final Context context, final List<Contact> contacts) {
        super(context, 0, contacts);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // getItem(position) gets the "contact" at "position" in the "contacts" ArrayList
        // (where "contacts" is a parameter in the ContactAdapter creator as seen above ^^)
        final Contact contact = getItem(position);
        final String username = "Username: " + contact.getUsername();
        final String email = "Email: " + contact.getEmail();
        // Check if an existing view is being reused, otherwise inflate the view.
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.contactlist_contact,
                    parent, false);
        }
        final TextView username_tv = convertView.findViewById(R.id.username_tv);
        final TextView email_tv = convertView.findViewById(R.id.email_tv);
        final ImageView photo = convertView.findViewById(R.id.contacts_image_view);
        photo.setImageResource(android.R.drawable.ic_menu_gallery);
        username_tv.setText(username);
        email_tv.setText(email);
        return convertView;
    }
}
