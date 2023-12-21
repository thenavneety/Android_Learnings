package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public final String TAG  = "crash:APP -> ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button_null_Pointer = findViewById(R.id.null_point_button);
        button_null_Pointer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    throw new NullPointerException();
                }

                finally {
                    Log.d(TAG, "<@-@>"+Thread.currentThread().getId()+"<@-@>");
                }

            }
        });
    }
}