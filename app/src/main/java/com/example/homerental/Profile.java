package com.example.homerental;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private Long mob;
    DatabaseReference db;
    private Button logout,profile_edit;
    private TextView name3,mobile3,email3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sharedPreferences = getSharedPreferences("shareddetails", Context.MODE_PRIVATE);
        mob=sharedPreferences.getLong("mobile",0);

        db = FirebaseDatabase.getInstance().getReference("Register").child(String.valueOf(mob));

        profile_edit=(Button)findViewById(R.id.proofileupd);
        logout= (Button)findViewById(R.id.log);
        name3=(TextView)findViewById(R.id.name2);
        mobile3=(TextView)findViewById(R.id.mobile2);
        email3=(TextView)findViewById(R.id.valemail);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nam=dataSnapshot.child("name").getValue().toString();
                String mobi=dataSnapshot.child("mb").getValue().toString();
                String emai_l=dataSnapshot.child("email_id").getValue().toString();
                name3.setText(nam);
                mobile3.setText(mobi);
                email3.setText(emai_l);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("loggedin", false);
                editor.putLong("mobile", 0);
                editor.putInt("homenumber", 1);
                editor.commit();

                Intent intent1 = new Intent(Profile.this, Splash.class);
                startActivity(intent1);
            }
        });

        profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Profile.this, EditProfile.class);
                startActivity(intent1);
            }
        });



    }
}
