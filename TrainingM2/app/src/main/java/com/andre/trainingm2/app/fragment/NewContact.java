package com.andre.trainingm2.app.fragment;



import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.andre.trainingm2.app.Adapter.DatabaseContact;
import com.andre.trainingm2.app.R;
import com.andre.trainingm2.app.models.ModelData;

import java.sql.SQLException;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class NewContact extends Fragment {
    private ImageView imagePhone;
    private EditText Name,Phone;
    private Button saveButton;
    private static int RESULT_LOAD_IMAGE=1;
    private DatabaseContact dbHelper;
    private Bitmap ImageSet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_contact, container, false);
        ImageSet=BitmapFactory.decodeResource(getResources(),R.drawable.default_thumb);
                saveButton=(Button)view.findViewById(R.id.save_button);
        imagePhone=(ImageView)view.findViewById(R.id.imageContact);
        Name=(EditText)view.findViewById(R.id.editName);
        Phone=(EditText)view.findViewById(R.id.editNumber);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelData modelData = new ModelData(getImageSet(), Name.getText().toString(), Phone.getText().toString());
                dbHelper = new DatabaseContact(getActivity());
                try {
                    dbHelper.open();
                    try {
                        dbHelper.addRow(modelData);
                    } finally {
                        dbHelper.close();
                    }
                } catch (SQLException e) {
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
            imagePhone.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            setImageSet(BitmapFactory.decodeFile(picturePath));
        }
    }
    private void setImageSet(Bitmap bitmap){
        ImageSet=bitmap;
    }
    private Bitmap getImageSet(){
        return ImageSet;
    }

}
