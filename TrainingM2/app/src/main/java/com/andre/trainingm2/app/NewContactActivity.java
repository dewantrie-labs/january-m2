package com.andre.trainingm2.app;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.andre.trainingm2.app.dao.DaoContact;
import com.andre.trainingm2.app.models.OtherSet;
import com.andre.trainingm2.app.models.ModelData;

import java.sql.SQLException;


public class NewContactActivity extends ActionBarActivity {
    private ImageView imagePhone;
    private EditText name, phone;
    private static int RESULT_LOAD_IMAGE=1;
    private DaoContact dbContact;
    private ModelData modelData;
    private Button saveButton;
    private OtherSet otherSet = new OtherSet();
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
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue400)));
        setTitle(getString(R.string.newContact));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelData = new ModelData();
                modelData.setPict(otherSet.getImageSet());
                modelData.setName(name.getText().toString());
                modelData.setNumber(phone.getText().toString());

                dbContact = new DaoContact(NewContactActivity.this);


                if (modelData.getName()!= null
                        && modelData.getNumber().toString()!= null
                        && otherSet.getImageSet()!= null) {

                    try {
                        dbContact.open();
                        try {
                            dbContact.CreateContact(modelData);
                            Toast.makeText(NewContactActivity.this, "success", Toast.LENGTH_SHORT).show();
                        } finally {
                            dbContact.close();
                        }
                    } catch (Exception e) {
                        Toast.makeText(NewContactActivity.this, "failed" + e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                Intent backMenu = new Intent(NewContactActivity.this,MainActivity.class);
                startActivity(backMenu);
                NewContactActivity.this.finish();

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
            otherSet.setImageSet(picturePath);
        }
    }

@Override
public void onBackPressed(){
    Intent back = new Intent(NewContactActivity.this,MainActivity.class);
    startActivity(back);
}

}
