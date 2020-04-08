package com.example.homerental;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button reg,log;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reg= (Button)findViewById(R.id.create_user);


        sharedPreferences = getSharedPreferences("shareddetails", Context.MODE_PRIVATE);
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //getSharedPreferences("shareddetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(sharedPreferences.getBoolean("loggedin",false)==true)
        {
            Intent intent1 = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent1);
        }

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent1 = new Intent(MainActivity.this, Register.class);
                    startActivity(intent1);
            }
        });


    }
}
