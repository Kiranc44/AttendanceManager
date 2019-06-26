package com.example.attendancemanager;


import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class history extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);

        TextView text=(TextView) findViewById(R.id.Display);

        Intent intent=getIntent();
        String f=intent.getStringExtra("file");
        text.setText(f);
    }
}
