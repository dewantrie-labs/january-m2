package com.andre.trainingm2.app;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.andre.trainingm2.app.dao.DaoContact;
import com.andre.trainingm2.app.models.OtherSet;
import com.andre.trainingm2.app.models.ModelData;

import java.sql.SQLException;


public class EditContactActivity extends ActionBarActivity implements View.OnClickListener {
    private ModelData modelData;
    private EditText editNameData;
    private EditText editPhoneData;
    private ImageView imageEdit;
    private Button saveDataEdit;
    private OtherSet otherSet =new OtherSet();
    private static int RESULT_LOAD_IMAGE = 1;
    private Button addFav;
    private Bundle bundle;
    private DaoContact daoContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        editNameData = (EditText)findViewById(R.id.editEditName);
        editPhoneData = (EditText)findViewById(R.id.editEditNumber);

        saveDataEdit = (Button)findViewById(R.id.save_buttonEdit);
        saveDataEdit.setOnClickListener(EditContactActivity.this);
        imageEdit = (ImageView)findViewById(R.id.editImageContact);
        imageEdit.setOnClickListener(EditContactActivity.this);

        addFav = (Button)findViewById(R.id.button_add_fav);
        addFav.setOnClickListener(EditContactActivity.this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.editContact));

        bundle = getIntent().getExtras();
        String nameData = bundle.getString("nama");
        String PhoneData = bundle.getString("phone");
        String imageData = bundle.getString("image");
        Boolean option = bundle.getBoolean("setEdit");

        if (option==true){
            addFav.setVisibility(View.VISIBLE);
        }

        if (bundle != null){
            editNameData.setText(nameData);
            editPhoneData.setText(PhoneData);

            if (bundle.getString("image")!=null){
                Bitmap bitmap= Bitmap.createScaledBitmap(BitmapFactory.decodeFile(bundle.getString("image")),50,50,false);
                imageEdit.setImageBitmap(bitmap);
                otherSet.setImageSet(imageData);
            }
            else
                imageEdit.setImageResource(R.drawable.default_thumb);

        }

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.save_buttonEdit:
                modelData = new ModelData();
                bundle=getIntent().getExtras();
                daoContact=new DaoContact(EditContactActivity.this);

                modelData.setId(bundle.getInt("id"));
                modelData.setName(editNameData.getText().toString());
                modelData.setNumber(editPhoneData.getText().toString());
                modelData.setPict(otherSet.getImageSet());

                try {
                    daoContact.open();
                    try{
                        daoContact.update(modelData);
                    }finally {
                        daoContact.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent backMenu = new Intent(EditContactActivity.this,MainActivity.class);
                startActivity(backMenu);
                EditContactActivity.this.finish();
                break;
            case R.id.editImageContact:
                Intent exploreImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(exploreImage, RESULT_LOAD_IMAGE);
                break;

            case R.id.button_add_fav:
                modelData=new ModelData();
                daoContact=new DaoContact(EditContactActivity.this);

                modelData.setFavorite(1);
                modelData.setId(bundle.getInt("id"));
                try {
                    daoContact.open();
                    try{
                        daoContact.toFavorites(modelData);
                    }finally {
                        daoContact.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent backToMenu = new Intent(EditContactActivity.this,MainActivity.class);
                startActivity(backToMenu);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data){
            Uri selectImage = data.getData();

            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectImage,filePath,null,null,null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePath[0]);
            String pathPicture = cursor.getString(columnIndex);

            Bitmap bitmap = BitmapFactory.decodeFile(pathPicture);
            imageEdit.setImageBitmap(bitmap);
            otherSet.setImageSet(pathPicture);
        }
    }
}
