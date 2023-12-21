package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText  name,roll;
        Button btnSubmit,btntoast;

        name = findViewById(R.id.name);
        roll = findViewById(R.id.roll);
        btnSubmit = findViewById(R.id.btnSubmit);
        btntoast = findViewById(R.id.btntoast);

        btntoast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Hi I'm toast",Toast.LENGTH_LONG).show();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent submit = new Intent(MainActivity.this,SecondActivity.class);
                String getname = name.getText().toString();
                String getroll = roll.getText().toString();

                Bundle know = new Bundle();
                know.putString("name",getname);
                know.putString("rollno",getroll);
                submit.putExtras(know);
                startActivity(submit);
            }
        });

    }
}