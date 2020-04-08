package com.example.homerental;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListHome extends AppCompatActivity {

    RecyclerView rv;
    DatabaseReference nm;
    private RecyclerAdapter adapter;
    ArrayList<dbcreatehome>list1;
    private Long m;
    private  int u;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_home);

        rv=(RecyclerView)findViewById(R.id.recyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));



       sharedPreferences = getSharedPreferences("shareddetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        m=sharedPreferences.getLong("mobile",0);
        u=sharedPreferences.getInt("homenumber",0);


        nm = FirebaseDatabase.getInstance().getReference("Homes").child(String.valueOf(m));

        list1=new ArrayList<dbcreatehome>();

        nm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                        String cname=npsnapshot.child("community_name").getValue().toString();
                        String aflat=npsnapshot.child("address_flat").getValue().toString();
                        String astreet=npsnapshot.child("street").getValue().toString();
                        String acity=npsnapshot.child("city").getValue().toString();
                        String astate=npsnapshot.child("state").getValue().toString();
                        String apin=npsnapshot.child("pincode").getValue().toString();
                        String nbed=npsnapshot.child("no_of_bedrooms").getValue().toString();
                        String sqft=npsnapshot.child("square_ft").getValue().toString();
                        String ren=npsnapshot.child("rent").getValue().toString();
                        String adv=npsnapshot.child("advance").getValue().toString();
                        String des=npsnapshot.child("description").getValue().toString();
                        String a=npsnapshot.child("image1").getValue().toString();
                        String b=npsnapshot.child("image2").getValue().toString();
                        String c=npsnapshot.child("image3").getValue().toString();
                        String d=npsnapshot.child("image4").getValue().toString();
                        String idd=npsnapshot.child("unique_id").getValue().toString();


                        dbcreatehome l=new dbcreatehome(cname,aflat,astreet,acity,astate,apin,sqft,ren,adv,des,nbed,a,b,c,d,idd);
                        list1.add(l);
                    }

                    adapter=new RecyclerAdapter(ListHome.this,list1,m);
                    rv.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}