package com.andre.trainingm2.app.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;

import android.widget.*;
import com.andre.trainingm2.app.EditContactActivity;
import com.andre.trainingm2.app.MainActivity;
import com.andre.trainingm2.app.R;
import com.andre.trainingm2.app.adapter.AdapterContact;
import com.andre.trainingm2.app.adapter.SearchAdapter;
import com.andre.trainingm2.app.dao.DaoContact;
import com.andre.trainingm2.app.models.ModelData;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class Contact extends Fragment implements TextWatcher, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    private static boolean boolFav;
    private ListView listDataContact;
    private EditText editSearch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contactview, container, false);
        listDataContact = (ListView) view.findViewById(R.id.list);
        editSearch = (EditText) view.findViewById(R.id.editTextSearchContact);

        editSearch.addTextChangedListener(Contact.this);
        listDataContact.setOnItemClickListener(Contact.this);
        listDataContact.setOnItemLongClickListener(Contact.this);

        final DaoContact daoContact = new DaoContact(getActivity());


            new AsyncTask<Void, Void, ArrayList<ModelData>>() {
                @Override
                protected ArrayList<ModelData> doInBackground(Void... voids) {

                    try {
                        daoContact.open();
                            return daoContact.getAllContact();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }finally {
                        daoContact.close();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(ArrayList<ModelData> listModelData) {
                    if (listModelData != null) {
                        AdapterContact adapterContact = new AdapterContact(getActivity(), listModelData);
                        listDataContact.setAdapter(adapterContact);
                    }
                }
            }.execute();

            return view;

    }

    public static void newInstance(boolean test) {
        boolFav = test;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
       AdapterContact adapterContact = (AdapterContact) listDataContact.getAdapter();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


//on item list click
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ModelData modelData = (ModelData)listDataContact.getAdapter().getItem(i);
        DaoContact daoContact = new DaoContact(getActivity());
        if (boolFav == true){

            try {
                daoContact.open();
                try{
                    modelData.setFavorite(1);
                    daoContact.toFavorites(modelData);
                }finally {
                    daoContact.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            boolFav = false;
        }
        else{

            Intent editData = new Intent(getActivity(),EditContactActivity.class);
            try {
                daoContact.open();
                try{
                    Bundle edit = new Bundle();
                    edit.putSerializable("data edit",modelData);
                    editData.putExtras(edit);

                    startActivity(editData);
                    getActivity().finish();
                }finally {
                    daoContact.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       }

    //on item long click to delete item
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setCancelable(true);
        alert.setMessage(getString(R.string.alert));

        alert.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DaoContact daoContact = new DaoContact(getActivity());
                try {
                    daoContact.open();
                    try{
                        ModelData modelData = (ModelData) listDataContact.getAdapter().getItem(position);
                        daoContact.deleteRow(modelData);
                        getActivity().recreate();
                    }finally {
                        daoContact.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        alert.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
        return false;


    }






}
