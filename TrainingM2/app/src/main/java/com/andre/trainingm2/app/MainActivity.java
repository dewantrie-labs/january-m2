package com.andre.trainingm2.app;

import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.Toast;
import com.andre.trainingm2.app.adapter.AdapterContact;
import com.andre.trainingm2.app.dao.DaoContact;
import com.andre.trainingm2.app.fragment.Contact;
import com.andre.trainingm2.app.fragment.Favorite;
import com.andre.trainingm2.app.models.ModelData;
import com.andre.trainingm2.app.models.OtherSet;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    public FragmentTabHost tabHost;
    boolean addFav;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue400)));
        setTitle(getString(R.string.Favorite));
        tabHost=(FragmentTabHost)findViewById(R.id.tab_host);


        tabHost.setup(this, getSupportFragmentManager(), R.id.utamaFrame);
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.Tab1)).setIndicator(getString(R.string.Favorite)), Favorite.class, null);
        tabHost.addTab(tabHost.newTabSpec(getString(R.string.Tab2)).setIndicator(getString(R.string.Contact)), Contact.class, null);
        tabHost.setCurrentTabByTag("tag2");

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

       @Override
       public void onTabChanged(String s) {

           if (s.equalsIgnoreCase(getString(R.string.Tab1))){
               setTitle(getString(R.string.Favorite));
           }
           else if (s.equalsIgnoreCase(getString(R.string.Tab2))) {
               setTitle(getString(R.string.Contact));
               addFav = false;
               Contact.newInstance(addFav);}
           }
     });
    }

@Override
    public boolean onCreateOptionsMenu(Menu menu){
    MenuInflater inflater=getMenuInflater();
    inflater.inflate(R.menu.menu_main,menu);

    /*MenuItem searchMenu = menu.findItem(R.id.searchContact);

    SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
    searchView = (SearchView) MenuItemCompat.getActionView(searchMenu);

    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    searchView.setSubmitButtonEnabled(true);*/

    return super.onCreateOptionsMenu(menu);
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    switch (item.getItemId()) {
        case R.id.add :
            if (getSupportActionBar().getTitle().toString().equalsIgnoreCase(getString(R.string.Contact))) {
                Intent nContact = new Intent(this, NewContactActivity.class);
                startActivity(nContact);
                MainActivity.this.finish();
            } else {
                tabHost.onTabChanged(getString(R.string.Tab2));
                addFav = true;
                Contact.newInstance(addFav);
            }
            break;

    }
    return super.onOptionsItemSelected(item);
    }


}