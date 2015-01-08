package com.andre.trainingm2.app.fragment;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import com.andre.trainingm2.app.R;
import com.andre.trainingm2.app.adapter.AdapterContact;
import com.andre.trainingm2.app.adapter.AdapterFavorites;
import com.andre.trainingm2.app.dao.DaoContact;
import com.andre.trainingm2.app.database.DatabaseContact;
import com.andre.trainingm2.app.models.ModelData;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class Favorite extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstance) {
        final DaoContact daoContact = new DaoContact(getActivity());

        try {
            daoContact.open();
            ArrayList<ModelData> listFavorites = daoContact.getFavorite();
            try{
                if (listFavorites!=null) {
                    AdapterFavorites adapterFavorites = new AdapterFavorites(getActivity(), listFavorites);
                    setListAdapter(adapterFavorites);
                    getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(),"empty data",Toast.LENGTH_SHORT).show();
                }
            }finally {
                daoContact.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
