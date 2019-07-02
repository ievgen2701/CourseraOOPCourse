package com.example.sharingapp;

import java.util.Objects;
import java.util.UUID;

public final class Contact {

    private String username;
    private String email;
    private String id;

    public Contact(final String username, final String email) {
        this(username, email, null);
    }

    public Contact(final String username, final String email, final String id) {
        this.username = username;
        this.email = email;
        if (id == null) {
            setId();
        } else {
            updateId(id);
        }
    }

    private void setId() {
        this.id = UUID.randomUUID().toString();
    }

    private void updateId(final String newId) {
        this.id = newId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Contact contact = (Contact) o;
        return this.id.equals(contact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
