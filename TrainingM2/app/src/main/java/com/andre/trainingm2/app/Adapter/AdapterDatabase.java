package com.andre.trainingm2.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.andre.trainingm2.app.R;
import com.andre.trainingm2.app.models.ModelData;
import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andree on 1/6/2015.
 */
public class AdapterDatabase extends BaseAdapter {
    Context context;
    List<ModelData> listData;

    public AdapterDatabase(Context context, List<ModelData> objects) {
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
        TextView text1, text2;
        ImageView image1;
        image1 = (ImageView) view.findViewById(R.id.imageView);
        text1 = (TextView) view.findViewById(R.id.textView1);
        text2 = (TextView) view.findViewById(R.id.textView2);
        if (listData.get(i).getPict()!=null){
        Bitmap imageNew=Bitmap.createScaledBitmap(BitmapFactory.decodeFile(listData.get(i).getPict()),50,50,false);
        image1.setImageBitmap(imageNew);}
        else {
            image1.setImageResource(R.drawable.default_thumb);
        }
        text1.setText(listData.get(i).getName());
        text2.setText(listData.get(i).getNumber());
        return view;
    }
}
