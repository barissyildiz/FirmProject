package com.example.bar.firmss;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ATİKER");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment_Main fragment_main = new Fragment_Main();
        fragmentTransaction.replace(R.id.fragment,fragment_main);
        fragmentTransaction.commit();

        //Alınan yada satılan ürünle ilgili olarak bildirim geldiği zaman bildirime tıklandığında ilgili fragmenta erişim sağlar
        Intent intent = getIntent();
        String firm = intent.getStringExtra("firm");

        if(firm != null) {

            FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
            Fragment_F fragment_f = new Fragment_F();
            fragmentTransaction1.replace(R.id.fragment,fragment_f);
            Bundle bundle = new Bundle();
            bundle.putString("string",firm);
            fragment_f.setArguments(bundle);
            fragmentTransaction1.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.add(1,1,1,"PERSONEL EKLE");
        menu.add(1,2,2,"PERSONEL SİL");

        SharedPreferences sharedPreferences = context.getSharedPreferences("USERS",Context.MODE_PRIVATE);
        String girişyapanpersonel = sharedPreferences.getString("personalname","0");

        if(girişyapanpersonel.equalsIgnoreCase("admin")) {

            menu.setGroupVisible(1,true);
        }
        else {

            menu.setGroupVisible(1,false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(context,QueryPanel.class);
            startActivity(intent);
        }
        else if(id == 1) {

            Intent intent = new Intent(context,PersonnelSave.class);
            startActivity(intent);
        }
        else if(id == 2) {

            Intent intent = new Intent(context,Personnel.class);
            intent.putExtra("menuitem",item.getTitle().toString());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getFragmentManager();
        Fragment_A fragment_a = new Fragment_A();
        Fragment_B fragment_b = new Fragment_B();
        Fragment_C fragment_c = new Fragment_C();
        Fragment_D fragment_d = new Fragment_D();
        Fragment_E fragment_e = new Fragment_E();
        Fragment_G fragment_g = new Fragment_G();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (id == R.id.nav_firmsave) {
            // Handle the camera action

            fragmentTransaction.replace(R.id.fragment,fragment_a,"A");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_productsave) {

            fragmentTransaction.replace(R.id.fragment,fragment_b,"B");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_firms) {

            fragmentTransaction.replace(R.id.fragment,fragment_c,"C");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_stokes) {

            fragmentTransaction.replace(R.id.fragment,fragment_d,"D");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_money) {

            fragmentTransaction.replace(R.id.fragment,fragment_e,"E");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_suggest) {

            fragmentTransaction.replace(R.id.fragment,fragment_g,"G");
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
