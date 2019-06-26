package com.example.attendancemanager;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.attendancemanager.MainActivity.subjects;
import static com.example.attendancemanager.MainActivity.symbol;

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

        final LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.item_layout,null,true);

        final  TextView name=(TextView)rowView.findViewById(R.id.name);
        name.setText(symbol.get(position));

        final TextView nameTextFiled=(TextView)rowView.findViewById(R.id.nameTextView);
        nameTextFiled.setText(nameArray.get(position));

        final TextView percentageDisp=(TextView)rowView.findViewById(R.id.rating);
        percentageDisp.setText(status.get(position));

        Button present=(Button)rowView.findViewById(R.id.present);
        Button absent=(Button)rowView.findViewById(R.id.absent);



        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change(getContext().getFilesDir()+"/"+nameArray.get(position)+"/Present.txt");
                history(getContext().getFilesDir()+"/"+nameArray.get(position)+"/History.txt","Present");

                MainActivity.presentPer=MainActivity.percentage(getContext().getFilesDir()+"/"+nameArray.get(position)+"/Present.txt");
                MainActivity.percentage=(MainActivity.presentPer*100/(MainActivity.presentPer+MainActivity.absentPer));
                try {
                    MainActivity.status.set(position, String.valueOf(MainActivity.percentage));
                    //percentageDisp.setText(MainActivity.status.get(position));
                    percentageDisp.setText(String.valueOf(MainActivity.percentage));
                }catch (Exception e){Toast.makeText(context.getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();}
            }
        });


        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change(getContext().getFilesDir()+"/"+nameArray.get(position)+"/Absent.txt");
                history(getContext().getFilesDir()+"/"+nameArray.get(position)+"/History.txt","Absent");

                MainActivity.absentPer=MainActivity.percentage(getContext().getFilesDir()+"/"+nameArray.get(position)+"/Absent.txt");
                MainActivity.percentage=(MainActivity.presentPer*100/(MainActivity.presentPer+MainActivity.absentPer));
                try {
                    MainActivity.status.set(position, String.valueOf(MainActivity.percentage));
                    //percentageDisp.setText(MainActivity.status.get(position));
                    percentageDisp.setText(String.valueOf(MainActivity.percentage));
                }catch (Exception e){Toast.makeText(context.getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();}

            }
        });


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,history.class);
                String file= null;
                try {
                    file = getHistory(getContext().getFilesDir()+"/"+nameArray.get(position)+"/History.txt");
                    intent.putExtra("file",file);
                    context.startActivity(intent);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
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
            }
            pre=Integer.parseInt(k);
            k=String.valueOf(++pre);

            FileOutputStream fout=new FileOutputStream(f);
            fout.write(k.getBytes());
        }catch (Exception e)
        {
            Toast.makeText(context.getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }

    }

    public  void history(String folder,String text)
    {
        File f=new File(folder);
        try
        {
            FileOutputStream fos=new FileOutputStream(f,true);
            String line=System.getProperty("line.separator");

            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())+"\t"+text;

            fos.write(date.getBytes());
            fos.write(line.getBytes());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public  String getHistory(String folder) throws IOException
    {
        String data="";
        File f=new File(folder);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String st;
        while ((st = br.readLine()) != null)
            data+=st+"\n";

        return  data;
    }


}