package com.example.homerental;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class Register extends AppCompatActivity {
    private EditText nam,mobile,verification_code,emailid;
    private Button send_code,verif_code;
    FirebaseDatabase database;
    DatabaseReference ref;
    int randomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        nam=findViewById(R.id.user_name);
        mobile=findViewById(R.id.mobile_no);
        verification_code=findViewById(R.id.ver_code);
        send_code=findViewById(R.id.send_ver_code);
        verif_code=findViewById(R.id.verify_code);
        emailid=findViewById(R.id.email_id);

        send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {



                    String apiKey = "apikey=" + "YlkLq8PNxqw-8oejESWo5n0zAw58hILK6m3K4gZ5ks";
                    Random random= new Random();
                    randomNumber=random.nextInt(999999);
                    String message = "&message=" + " Hey " + nam.getText().toString()+ " Your OTP Is " + randomNumber;
                    String sender = "&sender=" + "TXTLCL";
                    String numbers = "&numbers=" +mobile.getText().toString();

                    // Send data
                    HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
                    String data = apiKey + numbers + message + sender;
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                    conn.getOutputStream().write(data.getBytes("UTF-8"));
                    final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    final StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        stringBuffer.append(line);
                        Toast.makeText(getApplicationContext(),"OTP SENT SUCCESSFULLY",Toast.LENGTH_LONG).show();

                    }
                    rd.close();


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"ERROR IN SENDING SMS"+e,Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"ERROR"+e,Toast.LENGTH_LONG).show();

                }


            }
        });


        verif_code.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                if (randomNumber == Integer.valueOf(verification_code.getText().toString())) {


                    database = FirebaseDatabase.getInstance();
                    ref = database.getReference("Register");


                    final String name = nam.getText().toString();
                    final Long mobile_number = Long.parseLong(mobile.getText().toString());
                    final String email = emailid.getText().toString();


                    SharedPreferences sharedPreferences = getSharedPreferences("shareddetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("loggedin", true);
                    editor.putLong("mobile", mobile_number);
                    editor.putInt("homenumber", 1);
                    editor.commit();


                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(String.valueOf(mobile_number))) {
                                Toast.makeText(Register.this, "User already exists", Toast.LENGTH_SHORT).show();

                            } else {
                                dbuser obj1 = new dbuser(name, mobile_number, email);
                                ref.child(String.valueOf(mobile_number)).setValue(obj1);
                                Toast.makeText(getApplicationContext(), "You are loggedin successfully", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    Intent intent2 = new Intent(Register.this, HomePage.class);
                    startActivity(intent2);


                }
                else{
                    Toast.makeText(getApplicationContext(),"You have entered an incorrect OTP",Toast.LENGTH_LONG).show();
                }

            }
        });
        StrictMode.ThreadPolicy st=new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(st);

    }


}
