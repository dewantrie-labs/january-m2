package com.andre.trainingm2.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.andre.trainingm2.app.models.ModelData;

import java.io.ByteArrayInputStream;


public class EditContactActivity extends ActionBarActivity {
    private ModelData modelData;
    private EditText editNameData,editPhoneData;
    ImageView imageEdit;
    private Button saveDataEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        editNameData = (EditText)findViewById(R.id.editEditName);
        editPhoneData = (EditText)findViewById(R.id.editEditNumber);
        saveDataEdit = (Button)findViewById(R.id.save_buttonEdit);
        imageEdit = (ImageView)findViewById(R.id.editImageContact);

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            editNameData.setText(bundle.getString("nama"));
            editPhoneData.setText(bundle.getString("phone"));
            int idData=bundle.getInt("id");
            if (bundle.getString("image")!=null){
                Bitmap bitmap= Bitmap.createScaledBitmap(BitmapFactory.decodeFile(bundle.getString("image")),50,50,false);
                imageEdit.setImageBitmap(bitmap);
            }else
                imageEdit.setImageResource(R.drawable.default_thumb);

        }
    }



}
