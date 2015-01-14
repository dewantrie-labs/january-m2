package com.andre.trainingm2.app.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
public class Contact extends Fragment {
    private static boolean boolFav;
    private ListView listDataContact;
    private EditText editSearch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contactview,container,false);
        listDataContact = (ListView)view.findViewById(R.id.list);
        editSearch = (EditText)view.findViewById(R.id.editTextSearchContact);
        final DaoContact daoContact = new DaoContact(getActivity());

        try {
            daoContact.open();
            final ArrayList<ModelData> listData = daoContact.getAllContact();
            try{
                if (listData!=null){
                    final AdapterContact adapterContact = new AdapterContact(getActivity(),listData);
                    listDataContact.setAdapter(adapterContact);
                    listDataContact.setChoiceMode(listDataContact.CHOICE_MODE_SINGLE);

                    editSearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                adapterContact.filter(charSequence);
                            //disini?
                            // kl disini lu input langsung filter
                            //nanti yg di method tadi gw buat else nya buat filter ya? iya betul
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            // disini aja setelah text di input
                        }
                    });


                    listDataContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            if (boolFav == true) {
                                ModelData modelData = new ModelData();

                                try {
                                    daoContact.open();
                                    try {
                                        if (listData.get(i).isFavorite() == 1) {
                                            Toast.makeText(getActivity(), getString(R.string.fav), Toast.LENGTH_SHORT).show();
                                        } else {
                                            modelData.setFavorite(1);
                                            modelData.setId(listData.get(i).getId());
                                            daoContact.toFavorites(modelData);
                                        }
                                        Intent back = new Intent(getActivity(), MainActivity.class);
                                        startActivity(back);
                                    } finally {
                                        daoContact.close();
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                boolFav = false;
                            } else {

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
                                        editData.putInt("favorite", modelData.isFavorite());
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

                    listDataContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int pos, long l) {

                            final AlertDialog.Builder dialogAlert = new AlertDialog.Builder(getActivity());
                            dialogAlert.setMessage(getString(R.string.alert));
                            dialogAlert.setCancelable(true);

                            dialogAlert.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        daoContact.open();
                                        try {
                                            ModelData modelData = new ModelData();
                                            modelData.setId(listData.get(pos).getId());
                                            daoContact.deleteRow(modelData);
                                            getActivity().recreate();

                                        } finally {
                                            daoContact.close();
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                            dialogAlert.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            AlertDialog alert = dialogAlert.create();
                            alert.show();
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
        return view;
    }

    public static void newInstance(boolean test) {
        boolFav = test;
    }



   /* @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.searchContact);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        DaoContact daoContact = new DaoContact(getActivity());
        ArrayList<ModelData> listResult = daoContact.getAllContact();
        SearchAdapter adapter = new SearchAdapter(getActivity(),listResult);
        adapter.getFilter().filter(s);
        return false;
    }*/
}
