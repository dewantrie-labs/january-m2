package com.andre.trainingm2.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.andre.trainingm2.app.R;
import com.andre.trainingm2.app.models.ModelData;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Andree on 1/6/2015.
 */
public class AdapterContact extends BaseAdapter {
    Context context;
    ArrayList<ModelData> listData;

    public AdapterContact(Context context, ArrayList<ModelData> objects) {
        this.context = context;
        listData = objects;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.listcontact, viewGroup, false);

        TextView rowNameContact;
        TextView rowNumberContact;
        ImageView imageContactList;

        imageContactList = (ImageView) view.findViewById(R.id.imageList);
        rowNameContact = (TextView) view.findViewById(R.id.rowName);
        rowNumberContact = (TextView) view.findViewById(R.id.rowNumber);

        if (listData.get(i).getPict()!= null){
        Bitmap imageNew = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(listData.get(i).getPict()), 100, 100, false);
        imageContactList.setImageBitmap(imageNew);}
        else {
            imageContactList.setImageResource(R.drawable.default_thumb);
        }
        rowNameContact.setText(listData.get(i).getName());
        rowNumberContact.setText(listData.get(i).getNumber());

        return view;
    }
}
