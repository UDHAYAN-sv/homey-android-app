package com.example.homerental;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {
    DatabaseReference ep,ep2;
    SharedPreferences sharedPreferences;
    private Long mm;
    private EditText name4,email4;
    private Button updd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sharedPreferences = getSharedPreferences("shareddetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        mm=sharedPreferences.getLong("mobile",0);

        updd=(Button)findViewById(R.id.update_ep);
        name4=(EditText)findViewById(R.id.name2);
        email4=(EditText)findViewById(R.id.valemail);

        ep= FirebaseDatabase.getInstance().getReference("Register").child(String.valueOf(mm));

        ep2= FirebaseDatabase.getInstance().getReference("Register");


        ep.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nam=dataSnapshot.child("name").getValue().toString();
                String emai_l=dataSnapshot.child("email_id").getValue().toString();
                name4.setText(nam);
                email4.setText(emai_l);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final String name=name4.getText().toString();
        final String email=email4.getText().toString();

        updd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=name4.getText().toString();
                final String email=email4.getText().toString();

                dbuser obj2=new dbuser(name,mm,email);
                ep2.child(String.valueOf(mm)).setValue(obj2);


                Intent intent2 = new Intent(EditProfile.this, Profile.class);
                startActivity(intent2);
            }
        });
    }
}
