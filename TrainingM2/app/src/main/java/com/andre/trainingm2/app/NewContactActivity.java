package com.andre.trainingm2.app;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.andre.trainingm2.app.database.DatabaseContact;
import com.andre.trainingm2.app.models.ModelData;


public class NewContactActivity extends ActionBarActivity {
    private ImageView imagePhone;
    private EditText name, phone;
    private static int RESULT_LOAD_IMAGE=1;
    private DatabaseContact dbContact;
    private String ImageSet;
    private ModelData modelData;
    private TabHost tabHost;
    private Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        imagePhone = (ImageView) findViewById(R.id.imageContact);
        name = (EditText) findViewById(R.id.editName);
        phone = (EditText) findViewById(R.id.editNumber);
        saveButton = (Button)findViewById(R.id.save_button);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelData = new ModelData();
                modelData.setPict(getImageSet());
                modelData.setName(name.getText().toString());
                modelData.setNumber(phone.getText().toString());
                dbContact = new DatabaseContact(NewContactActivity.this);
                try {
                    try{
                        dbContact.addRow(modelData);
                        Toast.makeText(NewContactActivity.this, "success" + getImageSet(), Toast.LENGTH_SHORT).show();}
                    finally {
                        dbContact.close();
                    }
                } catch (Exception e) {
                    Toast.makeText(NewContactActivity.this, "gagal" + e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });

        imagePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exploreImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(exploreImage, RESULT_LOAD_IMAGE);
            }
        });
    }
    @Override
    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri SelectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(SelectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap setImg = BitmapFactory.decodeFile((picturePath));
            imagePhone.setImageBitmap(setImg);
            setImageSet(picturePath);
        }
    }

    private void setImageSet(String string) {
        ImageSet = string;
    }

    private String getImageSet() {
        return ImageSet;
    }

}
