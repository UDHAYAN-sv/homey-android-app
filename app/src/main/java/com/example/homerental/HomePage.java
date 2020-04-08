package com.example.homerental;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout draw;
    private ActionBarDrawerToggle toggle;
    private Long m= Long.valueOf(0);

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        draw=(DrawerLayout)findViewById(R.id.drawerLayout);
        toggle=new ActionBarDrawerToggle(this,draw,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        draw.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigation_view=(NavigationView)findViewById(R.id.nav_view);
        navigation_view.setNavigationItemSelectedListener(this);

       sharedPreferences = getSharedPreferences("shareddetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        m=sharedPreferences.getLong("mobile",m);


        Intent intent2 = getIntent();


    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        int id=item.getItemId();
        if(id==R.id.create_homey)
        {int unique_count=sharedPreferences.getInt("homenumber",0);
            unique_count=unique_count+1;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("homenumber",unique_count);
            editor.commit();

            Intent intent_create = new Intent(HomePage.this, CreateHome.class);
            startActivity(intent_create);

        }

        if(id==R.id.list_homey)
        {  Intent intent_list = new Intent(HomePage.this, ListHome.class);
            startActivity(intent_list);

        }
        if(id==R.id.profile)
        {  Intent intent_create = new Intent(HomePage.this, Profile.class);
            startActivity(intent_create);

        }


        draw.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed(){
        if (draw.isDrawerOpen(GravityCompat.START)) {
            draw.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
