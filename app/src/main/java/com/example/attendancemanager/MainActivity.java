package com.example.attendancemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

public class MainActivity extends AppCompatActivity {

    static int presentPer;
    static int absentPer;
    static int  percentage;

    Button add;
    static ListView subjects;
    static List<String> nameArray=new ArrayList<String>();
    static List <String> status=new ArrayList<>();
    static List<String> symbol=new ArrayList<>();
    static CustomListAdapter whatever;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSubjects();
        whatever = new CustomListAdapter(this, nameArray,status);
        subjects = (ListView) findViewById(R.id.subjects);
        subjects.setAdapter(whatever);

        add=(Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
        whatever = new CustomListAdapter(this, nameArray,status);
        subjects.setAdapter(whatever);
    }

    public  void getSubjects()
    {
        String folder;
        int index;
        nameArray=new ArrayList<>();

        File f=new File(String.valueOf(getApplicationContext().getFilesDir()));
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            index=files[i].toString().lastIndexOf("/");
            folder=files[i].toString().substring(index+1);

            symbol.add(String.valueOf(folder.charAt(0)));
            nameArray.add(folder);

            presentPer=percentage(getApplicationContext().getFilesDir()+"/"+folder+"/Present.txt");
            absentPer=percentage(getApplicationContext().getFilesDir()+"/"+folder+"/Absent.txt");
            try{
                percentage=(presentPer*100/(presentPer+absentPer));
                status.add(String.valueOf(percentage));
            }catch (ArithmeticException e){status.add("0");}

        }
    }

    public static int percentage(String folder)
    {
        int  pre=0;
        File f=new File(folder);
        try {
            FileInputStream fin = new FileInputStream(f);
            String k="";
            int line;

            while ((line=fin.read()) != -1) {
                k=String.valueOf((char)line);
            }
            pre=Integer.parseInt(k);

        }catch (Exception e)
        {

        }
        return pre;
    }
}
