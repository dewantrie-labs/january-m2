package com.andre.trainingm2.app.fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import com.andre.trainingm2.app.adapter.AdapterDatabase;
import com.andre.trainingm2.app.database.DatabaseContact;
import com.andre.trainingm2.app.models.ModelData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class Contact extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        List<ModelData> listData = new DatabaseContact(getActivity()).getAllData();
        if (listData != null) {
            AdapterDatabase adapterDatabase = new AdapterDatabase(getActivity(), listData);
            setListAdapter(adapterDatabase);
        } else {
            Toast.makeText(getActivity(), "Database is null.", Toast.LENGTH_LONG).show();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}
