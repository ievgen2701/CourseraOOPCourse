package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Superclass of AvailableItemsFragment, BorrowedItemsFragment and AllItemsFragment
 */
public abstract class ItemsFragment extends Fragment {

    ItemList item_list = new ItemList();
    View rootView = null;
    private ListView list_view = null;
    private ArrayAdapter<Item> adapter = null;
    private ArrayList<Item> selected_items;
    private LayoutInflater inflater;
    private ViewGroup container;
    private Context context;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        this.context = getContext();
        this.item_list.loadItems(this.context);
        this.inflater = inflater;
        this.container = container;
        return this.rootView;
    }

    public void setVariables(final int resource, final int id ) {
        this.rootView = this.inflater.inflate(resource, this.container, false);
        this.list_view = this.rootView.findViewById(id);
        this.selected_items = filterItems();
    }

    public void setAdapter(final Fragment fragment){
        this.adapter = new ItemAdapter(this.context, this.selected_items, fragment);
        this.list_view.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();

        // When item is long clicked, this starts EditItemActivity
        this.list_view.setOnItemLongClickListener((parent, view, pos, id) -> {

            final Item item = ItemsFragment.this.adapter.getItem(pos);

            final int meta_pos = ItemsFragment.this.item_list.getIndex(item);
            if (meta_pos >= 0) {

                final Intent edit = new Intent(ItemsFragment.this.context, EditItemActivity.class);
                edit.putExtra("position", meta_pos);
                startActivity(edit);
            }
            return true;
        });
    }

    /**
     * filterItems is implemented independently by AvailableItemsFragment, BorrowedItemsFragment and AllItemsFragment
     * @return selected_items
     */
    public abstract ArrayList<Item> filterItems();

}
