package com.andre.trainingm2.app;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.andre.trainingm2.app.dao.DaoContact;
import com.andre.trainingm2.app.models.OtherSet;
import com.andre.trainingm2.app.models.ModelData;

import java.sql.SQLException;


public class EditContactActivity extends ActionBarActivity implements View.OnClickListener {
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
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue400)));
        setTitle(getString(R.string.editContact));

        ModelData modelData = (ModelData) getIntent().getSerializableExtra("editData");
        String nameData = modelData.getName();
        String PhoneData = modelData.getNumber();
        String imageData = modelData.getPict();
        Boolean option = bundle.getBoolean("setEdit");

        if (option==true){
            addFav.setVisibility(View.VISIBLE);
            option = false;
        }

        if (bundle != null){
            editNameData.setText(nameData);
            editPhoneData.setText(PhoneData);

            if (modelData.getPict() != null){
                Bitmap bitmap= Bitmap.createScaledBitmap(BitmapFactory.decodeFile(modelData.getPict()),50,50,false);
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
                bundle=getIntent().getExtras();

                ModelData modelData = (ModelData) bundle.getSerializable("editData");
                daoContact=new DaoContact(EditContactActivity.this);

                modelData.setName(editNameData.getText().toString());
                modelData.setNumber(editPhoneData.getText().toString());
                modelData.setPict(otherSet.getImageSet());


                if (modelData.getName()!= null
                    || modelData.getNumber().toString()!=null
                    || otherSet.getImageSet()!=null) {

                    try {
                        daoContact.open();
                        try {
                            daoContact.update(modelData);
                        } finally {
                            daoContact.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else{

                    try {
                        daoContact.open();
                        try{

                            modelData.setId(modelData.getId());
                            daoContact.deleteRow(modelData);

                        }finally {
                            daoContact.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
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
                modelData= (ModelData) getIntent().getSerializableExtra("editData");
                daoContact=new DaoContact(EditContactActivity.this);
                if (modelData.isFavorite() == 1){
                    Toast.makeText(getApplicationContext(),getString(R.string.fav),Toast.LENGTH_SHORT).show();
                }
                else {
                    modelData.setFavorite(1);
                    try {
                        daoContact.open();
                        try {
                            daoContact.toFavorites(modelData);
                        } finally {
                            daoContact.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    Intent backToMenu = new Intent(EditContactActivity.this, MainActivity.class);
                    startActivity(backToMenu);
                }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_edit_contact,menu);

        bundle = getIntent().getExtras();
        Boolean option = bundle.getBoolean("setEdit");

        MenuItem item = menu.findItem(R.id.delete);
        if (option != true) {
            item.setVisible(true);
            option=false;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
       switch(item.getItemId()) {

           case R.id.delete :
               editPhoneData.setText(null);
               editNameData.setText(null);
               imageEdit.setImageResource(R.drawable.default_thumb);
               otherSet.setImageSet(null);
           break;
       }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent toBack = new Intent(EditContactActivity.this,MainActivity.class);
        startActivity(toBack);
    }
}
