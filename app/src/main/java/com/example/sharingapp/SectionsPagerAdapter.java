package com.example.sharingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public final class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
            case 0:
                return new AllItemsFragment();
            case 1:
                return new AvailableItemsFragment();
            case 2:
                return new BorrowedItemsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3; // Three pages
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            case 0:
                return "All";
            case 1:
                return "Available";
            case 2:
                return "Borrowed";
        }
        return null;
    }
}