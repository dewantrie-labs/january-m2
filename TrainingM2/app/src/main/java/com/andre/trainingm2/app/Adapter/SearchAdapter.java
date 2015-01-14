package com.andre.trainingm2.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.andre.trainingm2.app.R;
import com.andre.trainingm2.app.fragment.Contact;
import com.andre.trainingm2.app.models.ModelData;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Andree on 1/6/2015.
 */
public class SearchAdapter extends BaseAdapter implements Filterable {
    ContactFilter contactFilter;
    Context context;
    ArrayList<ModelData> listData;
    ArrayList<ModelData> filterList;

    public SearchAdapter(Context context, ArrayList<ModelData> objects) {
        this.context = context;
        listData = objects;
        filterList = objects;

        getFilter();
    }

    @Override
    public int getCount() {
        return filterList.size();
    }

    @Override
    public Object getItem(int i) {
        return filterList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        final ModelData modelData = (ModelData)getItem(i);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
           // view = inflater.inflate(R.layout.holderview, viewGroup, false);

            holder = new ViewHolder();
            //holder.textHolder = (TextView)view.findViewById(R.id.textHolder);
            view.setTag(holder);
        }

        else{
             holder = (ViewHolder)view.getTag();
        }
            holder.textHolder.setText(modelData.getName());
            return view;

    }

    @Override
    public Filter getFilter() {
        if (contactFilter == null){
            contactFilter = new ContactFilter();
        }
        return contactFilter;
    }
static class ViewHolder{
    TextView textHolder;
}

    public class ContactFilter extends Filter {
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
                filterResults.count = filterList.size();
                filterResults.values = filterList;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filterList = (ArrayList<ModelData>) filterResults.values;
            notifyDataSetChanged();
        }
    }


}
