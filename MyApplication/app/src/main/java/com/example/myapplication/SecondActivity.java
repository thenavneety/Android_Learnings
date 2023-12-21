package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent_second = getIntent();
        Log.d("in_val", intent_second.getAction());

        TextView edittxt;
        Button getCall,getSms,getWeb;
        edittxt = findViewById(R.id.edittxt);


        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            String name = bundle.getString("name");
            String rollno = bundle.getString("rollno");

            String ans = "Hi " + name + " your rollno is " + rollno;
            edittxt.setText(ans);
        }



        getCall = findViewById(R.id.getCall);
        getCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Call = new Intent(Intent.ACTION_SEND);
//                Call.setData(Uri.parse("mailto: navneetyadav2591@gmail.com"));
                Intent s = Intent.createChooser(Call,"Select app");
                startActivity(s);
            }
        });

        getSms = findViewById(R.id.btnSMS);
        getSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sms = new Intent(Intent.ACTION_SENDTO);
                sms.setData(Uri.parse("smsto: +918833446623"));
                startActivity(sms);
            }
        });

        //Implicit Intent
        getWeb = findViewById(R.id.btnWeb);
        getWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent web = new Intent(Intent.ACTION_VIEW);
                web.setData(Uri.parse("https://www.google.com"));
                startActivity(web);
            }
        });
    }
}