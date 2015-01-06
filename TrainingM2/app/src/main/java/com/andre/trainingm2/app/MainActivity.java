package com.andre.trainingm2.app;

import android.support.v7.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import com.andre.trainingm2.app.Adapter.TabListener;
import com.andre.trainingm2.app.fragment.Contact;
import com.andre.trainingm2.app.fragment.NewContact;


public class MainActivity extends ActionBarActivity {
    ActionBar.Tab newContact,Favorite,Contact;
    Fragment fragmentNewContact=new NewContact();
    Fragment fragmenttest=new Contact();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        Favorite=actionBar.newTab().setText("Favorite");
        Contact=actionBar.newTab().setText("Contact");
        Favorite.setTabListener(new TabListener(fragmenttest));
        Contact.setTabListener(new TabListener(fragmentNewContact));
        actionBar.addTab(Favorite);
        actionBar.addTab(Contact);
    }


}
