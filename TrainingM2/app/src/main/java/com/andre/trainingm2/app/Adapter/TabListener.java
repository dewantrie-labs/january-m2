package com.andre.trainingm2.app.Adapter;

import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import com.andre.trainingm2.app.R;

/**
 * Created by Andree on 1/6/2015.
 */
public class TabListener implements ActionBar.TabListener{
    private Fragment fragment;
    public TabListener(Fragment fragment) {
        this.fragment=fragment;
    }


    @Override
    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.replace(R.id.Utama,fragment);
    }

    @Override
    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.remove(fragment);
    }

    @Override
    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
