package com.example.sharingapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ContactList {

    private final List<Contact> contacts = new ArrayList<>();
    private static final String FILENAME = "contacts.sav";

    public void setContacts(final List<Contact> newContacts) {
        this.contacts.clear();
        this.contacts.addAll(newContacts);
    }

    public List<Contact> getContacts() {
        return Collections.unmodifiableList(this.contacts);
    }

    public List<String> getAllUsernames() {
        return this.contacts.stream()
                .map(Contact::getUsername)
                .collect(Collectors.toList());
    }

    public void addContact(final Contact toAdd) {
        Optional.ofNullable(toAdd).ifPresent(this.contacts::add);
    }

    public void deleteContact(final Contact toDelete) {
        Optional.ofNullable(toDelete).ifPresent(this.contacts::remove);
    }

    public Contact getContact(final int contactIndex) {
        if (contactIndex > this.contacts.size()) {
            throw new IllegalArgumentException("Given index is bigger than contact list.");
        } else if (contactIndex < 0) {
            throw new IllegalArgumentException("Given index have to be greater or equal 0.");
        } else {
            return this.contacts.get(contactIndex);
        }
    }

    public int getSize() {
        return this.contacts.size();
    }

    public int getIndex(final Contact contact) {
        return Optional.ofNullable(contact).map(this.contacts::indexOf).orElse(-1);
    }

    public boolean hasContact(final Contact contact) {
        return Optional.ofNullable(contact).map(this.contacts::contains).orElse(false);
    }

    // it would be more correct to return Optional here
    public Contact getContactByUsername(final String username) {
        return this.contacts.stream()
                .filter(ct -> username.equals(ct.getUsername()))
                .findFirst()
                .orElse(null);
    }

    public void loadContacts(final Context context) {
        try (final FileInputStream fis = context.openFileInput(FILENAME);
             final InputStreamReader isr = new InputStreamReader(fis)) {
            final Gson gson = new Gson();
            final Type listType = new TypeToken<List<Contact>>() {
            }.getType();
            this.contacts.clear();
            this.contacts.addAll(gson.fromJson(isr, listType)); // temporary
        } catch (final IOException e) {
            this.contacts.clear();
        }
    }

    public void saveContacts(final Context context) {
        try (final FileOutputStream fos = context.openFileOutput(FILENAME, 0);
             final OutputStreamWriter osw = new OutputStreamWriter(fos)) {
            final Gson gson = new Gson();
            gson.toJson(this.contacts, osw);
            osw.flush();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameAvailable(final String username) {
        return this.contacts.stream()
                .map(Contact::getUsername)
                .noneMatch(username::equals);
    }


}
