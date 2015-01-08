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

import java.util.ArrayList;

/**
 * Created by Andree on 1/8/2015.
 */
public class AdapterFavorites extends BaseAdapter{
    Context context;
    ArrayList<ModelData> listFavorites;
    public AdapterFavorites(Context context, ArrayList<ModelData> listFavorites) {
        this.context=context;
        this.listFavorites=listFavorites;
    }

    @Override
    public int getCount() {
        return listFavorites.size();
    }

    @Override
    public Object getItem(int i) {
        return listFavorites.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view1=inflater.inflate(R.layout.listcontact,viewGroup,false);

        TextView rowNameFav;
        TextView rowNumberFav;
        ImageView listImageFav;

        rowNameFav = (TextView)view1.findViewById(R.id.rowName);
        rowNumberFav = (TextView)view1.findViewById(R.id.rowNumber);
        listImageFav = (ImageView)view1.findViewById(R.id.imageList);

        rowNameFav.setText(listFavorites.get(i).getName());
        rowNumberFav.setText(listFavorites.get(i).getNumber());

        if (listFavorites.get(i).getPict()!=null){
            Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(listFavorites.get(i).getPict()), 50, 50, false);
            listImageFav.setImageBitmap(bitmap);
        }
        else{
            listImageFav.setImageResource(R.drawable.default_thumb);
        }

        return view1;
    }
}
