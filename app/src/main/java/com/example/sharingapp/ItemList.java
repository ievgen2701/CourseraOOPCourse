package com.example.sharingapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * ItemList class
 */
public final class ItemList {

    private static ArrayList<Item> items;
    private static final String FILENAME = "items.sav";

    public ItemList() {
        items = new ArrayList<>();
    }

    public void setItems(final ArrayList<Item> item_list) {
        items = item_list;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(final Item item) {
        items.add(item);
    }

    public void deleteItem(final Item item) {
        items.remove(item);
    }

    public Item getItem(final int index) {
        return items.get(index);
    }

    public int getIndex(final Item item) {
        int pos = 0;
        for (final Item i : items) {
            if (item.getId().equals(i.getId())) {
                return pos;
            }
            pos = pos + 1;
        }
        return -1;
    }

    public int getSize() {
        return items.size();
    }

    public void loadItems(final Context context) {
        try {
            final FileInputStream fis = context.openFileInput(FILENAME);
            final InputStreamReader isr = new InputStreamReader(fis);
            final Gson gson = new Gson();
            final Type listType = new TypeToken<ArrayList<Item>>() {
            }.getType();
            items = gson.fromJson(isr, listType); // temporary
            fis.close();
        } catch (final FileNotFoundException e) {
            items = new ArrayList<>();
        } catch (final IOException e) {
            items = new ArrayList<>();
        }
    }

    public void saveItems(final Context context) {
        try {
            final FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            final OutputStreamWriter osw = new OutputStreamWriter(fos);
            final Gson gson = new Gson();
            gson.toJson(items, osw);
            osw.flush();
            fos.close();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Item> filterItemsByStatus(final String status) {
        final ArrayList<Item> selected_items = new ArrayList<>();
        for (final Item i : items) {
            if (i.getStatus().equals(status)) {
                selected_items.add(i);
            }
        }
        return selected_items;
    }

    public ArrayList<Contact> getActiveBorrowers() {
        final ArrayList<Contact> active_borrowers = new ArrayList<>();
        for (final Item i : items) {
            final Contact borrower = i.getBorrower();
            if (borrower != null) {
                active_borrowers.add(borrower);
            }
        }
        return active_borrowers;
    }

}

