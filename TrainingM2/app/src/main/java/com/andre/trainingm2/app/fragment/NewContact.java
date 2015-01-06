package com.andre.trainingm2.app.fragment;



import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.andre.trainingm2.app.database.DatabaseContact;
import com.andre.trainingm2.app.R;
import com.andre.trainingm2.app.models.ModelData;

import java.sql.SQLException;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class NewContact extends Fragment {
    private ImageView imagePhone;
    private EditText name, phone;
    private Button saveButton;
    private static int RESULT_LOAD_IMAGE=1;
    private DatabaseContact dbContact;
    private String ImageSet;
    private ModelData modelData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_contact, container, false);
                saveButton=(Button)view.findViewById(R.id.save_button);
        imagePhone=(ImageView)view.findViewById(R.id.imageContact);
        name =(EditText)view.findViewById(R.id.editName);
        phone =(EditText)view.findViewById(R.id.editNumber);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelData = new ModelData();
                modelData.setPict(getImageSet());
                modelData.setName(name.getText().toString());
                modelData.setNumber(phone.getText().toString());
                dbContact = new DatabaseContact(getActivity());
                try{
                dbContact.addRow(modelData);
                Toast.makeText(getActivity(),"success"+getImageSet(),Toast.LENGTH_SHORT).show();}
                catch (Exception e){
                    Toast.makeText(getActivity(),"gagal"+e.toString(),Toast.LENGTH_SHORT).show();
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
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==RESULT_LOAD_IMAGE&&resultCode==getActivity().RESULT_OK&&null!=data){
            Uri SelectedImage=data.getData();
            String[] filePathColumn={MediaStore.Images.Media.DATA};
            Cursor cursor=getActivity().getContentResolver().query(SelectedImage,filePathColumn,null,null,null);
            cursor.moveToFirst();

            int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
            String picturePath=cursor.getString(columnIndex);
            cursor.close();
            Bitmap setImg=BitmapFactory.decodeFile((picturePath));
            imagePhone.setImageBitmap(setImg);
            setImageSet(picturePath);
        }
    }
    private void setImageSet(String string){
        ImageSet=string;
    }
    private String getImageSet(){
        return ImageSet;
    }

}
