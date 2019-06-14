package com.example.attendancemanager;

import android.app.Activity;
import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;

import static com.example.attendancemanager.MainActivity.subjects;

public class CustomListAdapter extends ArrayAdapter {
    private final Activity context;
    private  final List<String> nameArray;
    private  final List<String> status;


    public CustomListAdapter(Activity context,List<String> nameArray,List<String> status)
    {
        super(context,R.layout.item_layout , nameArray);
        this.context=context;
        this.nameArray=nameArray;
        this.status=status;
    }


    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        //return super.getView(position, convertView, parent);


        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.item_layout,null,true);

        final TextView nameTextFiled=(TextView)rowView.findViewById(R.id.nameTextView);
        nameTextFiled.setText(nameArray.get(position));

        TextView percentageDisp=(TextView)rowView.findViewById(R.id.rating);
        percentageDisp.setText(status.get(position));

        Button present=(Button)rowView.findViewById(R.id.present);
        Button absent=(Button)rowView.findViewById(R.id.absent);



        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change(getContext().getFilesDir()+"/"+nameArray.get(position)+"/Present.txt");
                MainActivity.presentPer=MainActivity.percentage(getContext().getFilesDir()+"/"+nameArray.get(position)+"/Present.txt");
                MainActivity.presentPer+=1;
                MainActivity.percentage=(MainActivity.presentPer/(MainActivity.presentPer+MainActivity.absentPer))*100;
                try {
                    //Toast.makeText(context.getApplicationContext(),String.valueOf(v.getId()),Toast.LENGTH_SHORT).show();

                    MainActivity.status.add(position, String.valueOf(MainActivity.percentage));

                    MainActivity obj = new MainActivity();
                    obj.setStatus(nameArray, MainActivity.status);
                }catch (Exception e){Toast.makeText(context.getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();}
            }
        });


        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change(getContext().getFilesDir()+"/"+nameArray.get(position)+"/Absent.txt");
                MainActivity.absentPer=MainActivity.percentage(getContext().getFilesDir()+"/"+nameArray.get(position)+"/Absent.txt");
                MainActivity.absentPer+=MainActivity.absentPer;


            }
        });


        return  rowView;
    }


    public  void change(String folder)
    {
        File f=new File(folder);
        try {
            FileInputStream fin=new FileInputStream(f);

            String k="";
            int line;
            int  pre;
            while ((line=fin.read()) != -1) {
                k=String.valueOf((char)line);
                //Toast.makeText(context.getApplicationContext(),k,Toast.LENGTH_SHORT).show();
            }
            pre=Integer.parseInt(k);
            k=String.valueOf(++pre);

            FileOutputStream fout=new FileOutputStream(f);
            fout.write(k.getBytes());
            //Toast.makeText(context.getApplicationContext(),k,Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(context.getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }

    }

    /*public  int percentage(String folder)
    {
        int  pre=0;
        File f=new File(folder);
        try {
            FileInputStream fin = new FileInputStream(f);
            String k="";
            int line;

            while ((line=fin.read()) != -1) {
                k=String.valueOf((char)line);
                Toast.makeText(context.getApplicationContext(),k,Toast.LENGTH_SHORT).show();
            }
            pre=Integer.parseInt(k);

        }catch (Exception e)
        {
            Toast.makeText(context.getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
        return ++pre;
    }*/
}