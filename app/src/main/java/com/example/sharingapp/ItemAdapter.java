package com.example.sharingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Item Adapter is responsible for what information is displayed in ListView entries.
 */
public final class ItemAdapter extends ArrayAdapter<Item> {

    private final LayoutInflater inflater;
    private final Fragment fragment;
    private final Context context;

    public ItemAdapter(final Context context, final ArrayList<Item> items, final Fragment fragment) {
        super(context, 0, items);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        // getItem(position) gets the "item" at "position" in the "items" ArrayList
        // (where "items" is a parameter in the ItemAdapter creator as seen above ^^)
        // Note: getItem() is not a user-defined method in the Item/ItemList class!
        // The "Item" in the method name is a coincidence...
        final Item item = getItem(position);

        final String title = "Title: " + item.getTitle();
        final String description = "Description: " + item.getDescription();
        final Bitmap thumbnail = item.getImage();
        final String status = "Status: " + item.getStatus();

        // Check if an existing view is being reused, otherwise inflate the view.
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.itemlist_item, parent, false);
        }

        final TextView title_tv = convertView.findViewById(R.id.title_tv);
        final TextView status_tv = convertView.findViewById(R.id.status_tv);
        final TextView description_tv = convertView.findViewById(R.id.description_tv);
        final ImageView photo = convertView.findViewById(R.id.image_view);

        if (thumbnail != null) {
            photo.setImageBitmap(thumbnail);
        } else {
            photo.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        title_tv.setText(title);
        description_tv.setText(description);

        // AllItemFragments: itemlist_item shows title, description and status
        if (this.fragment instanceof AllItemsFragment ) {
            status_tv.setText(status);
        }

        // BorrowedItemsFragment/AvailableItemsFragment: itemlist_item shows title and description only
        if (this.fragment instanceof BorrowedItemsFragment || this.fragment instanceof AvailableItemsFragment) {
            status_tv.setVisibility(View.GONE);
        }

        return convertView;
    }
}
