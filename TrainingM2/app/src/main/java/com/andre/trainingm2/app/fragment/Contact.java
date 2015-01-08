package com.andre.trainingm2.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Toast;
import com.andre.trainingm2.app.EditContactActivity;
import com.andre.trainingm2.app.MainActivity;
import com.andre.trainingm2.app.R;
import com.andre.trainingm2.app.adapter.AdapterContact;
import com.andre.trainingm2.app.dao.DaoContact;
import com.andre.trainingm2.app.models.ModelData;
import com.andre.trainingm2.app.models.OtherSet;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class Contact extends ListFragment {

    private static boolean boolFav;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstance){
        final DaoContact daoContact = new DaoContact(getActivity());

        try {
            daoContact.open();
            final ArrayList<ModelData> listData = daoContact.getAllContact();
            try{
                if (listData!=null){
                    AdapterContact adapterContact = new AdapterContact(getActivity(),listData);
                    setListAdapter(adapterContact);
                    getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                if (boolFav == true) {
                                    ModelData modelData = new ModelData();
                                    modelData.setFavorite(1);
                                    modelData.setId(listData.get(i).getId());

                                    try {
                                        daoContact.open();
                                        try {
                                            daoContact.toFavorites(modelData);
                                        }finally {
                                            daoContact.close();
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }

                                else {
                                    Intent edit = new Intent(getActivity(), EditContactActivity.class);


                                    try {
                                        daoContact.open();
                                        try {
                                            ModelData modelData = daoContact.getContact(listData.get(i).getId());
                                            Bundle editData = new Bundle();

                                            editData.putInt("id", modelData.getId());
                                            editData.putString("nama", modelData.getName());
                                            editData.putString("phone", modelData.getNumber());
                                            editData.putString("image", modelData.getPict());
                                            editData.putBoolean("setEdit", true);
                                            edit.putExtras(editData);

                                            startActivity(edit);
                                            getActivity().finish();
                                        } finally {
                                            daoContact.close();
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        });

                        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Toast.makeText(getActivity(), "long press", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        });
                    }
                else
                    Toast.makeText(getActivity(), "Database empty", Toast.LENGTH_SHORT).show();
            }finally {
                daoContact.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void newInstance(boolean test) {
        boolFav =test;
    }
}
