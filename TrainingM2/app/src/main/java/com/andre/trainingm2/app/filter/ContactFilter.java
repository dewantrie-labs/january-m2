package com.andre.trainingm2.app.filter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import com.andre.trainingm2.app.models.ModelData;

import java.util.ArrayList;

/**
 * Created by Andre on 1/13/2015.
 */
public class ContactFilter extends Filter {
    Context context;
    ArrayList<ModelData> listData = new ArrayList<ModelData>();
    ArrayList<ModelData> filterList;

    public ContactFilter(Context context, ArrayList<ModelData> listData) {
        this.context = context;
        this.listData = listData;
        filterList = listData;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults filterResults = new FilterResults();
        if (charSequence != null && charSequence.length()>0){
            ArrayList<ModelData> tempList = new ArrayList<ModelData>();
            for (ModelData modelData : listData ){
                if (modelData.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                    tempList.add(modelData);
                }
            }
            filterResults.count = tempList.size();
            filterResults.values = tempList;
        }
        else{
            filterResults.count = listData.size();
            filterResults.values = listData;
        }
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
    filterList = (ArrayList<ModelData>) filterResults.values;
    }
}
