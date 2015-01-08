package com.andre.trainingm2.app;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
import com.andre.trainingm2.app.fragment.Contact;
import com.andre.trainingm2.app.fragment.Favorite;
import com.andre.trainingm2.app.models.OtherSet;


public class MainActivity extends ActionBarActivity{
    public FragmentTabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        tabHost=(FragmentTabHost)findViewById(R.id.tab_host);

        tabHost.setup(this,getSupportFragmentManager(),R.id.utamaFrame);
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.Tab1)).setIndicator(getString(R.string.Favorite)), Favorite.class, null);
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.Tab2)).setIndicator(getString(R.string.Contact)),Contact.class,null);
        tabHost.setCurrentTabByTag("tag2");


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

       @Override
       public void onTabChanged(String s) {
/*       FragmentManager fragmentManager=getSupportFragmentManager();
       NewContact newContact = (NewContact)fragmentManager.findFragmentByTag("New Contact");
       android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();*/
           if (s.equalsIgnoreCase(getString(R.string.Tab1))){
               setTitle(getString(R.string.Favorite));
           }
           else if (s.equalsIgnoreCase(getString(R.string.Tab2))) {
           setTitle(getString(R.string.Contact));}
           }
     });
    }

@Override
    public boolean onCreateOptionsMenu(Menu menu){
    MenuInflater inflater=getMenuInflater();
    inflater.inflate(R.menu.menu_main,menu);

    return super.onCreateOptionsMenu(menu);
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    item.getItemId();

        if (getTitle().toString().equalsIgnoreCase(getString(R.string.Contact))){
        Intent nContact=new Intent(this,NewContactActivity.class);
        startActivity(nContact);
        }
        else{
            tabHost.onTabChanged(getString(R.string.Tab2));
            boolean addFav = true;
            Contact.newInstance(addFav);
        }
    return super.onOptionsItemSelected(item);
    }
}