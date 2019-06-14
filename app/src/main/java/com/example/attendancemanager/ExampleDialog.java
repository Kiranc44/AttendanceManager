package com.example.attendancemanager;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;


public class ExampleDialog extends AppCompatDialogFragment {
    private EditText editTextUsername;
    Context context;
    //private EditText editTextPassword;
    //private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Add subject name")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = editTextUsername.getText().toString();
                        //FileOutputStream fos=null;
                        try{
                            File dir=new File(getContext().getFilesDir(),username);
                            if(!dir.exists())
                                dir.mkdir();

                            File absent=new File(getContext().getFilesDir()+"/"+username+"/","Absent.txt");
                            File present=new File(getContext().getFilesDir()+"/"+username+"/","Present.txt");
                            File history=new File(getContext().getFilesDir()+"/"+username+"/","History.txt");

                            FileOutputStream absentwrite=new FileOutputStream(absent);
                            FileOutputStream presentwrite=new FileOutputStream(present);
                            FileOutputStream historywrite=new FileOutputStream(history,true);

                            try{
                                absentwrite.write("0".getBytes());
                            }catch (Exception e){}
                            try{
                                presentwrite.write("0".getBytes());
                            }catch (Exception e){}
                            try{
                                historywrite.write("0".getBytes());
                            }catch (Exception e){}

                        }catch (Exception e){}

                        MainActivity.nameArray.add(username);
                        MainActivity.status.add("0");
                        // CustomListAdapter whatever = new CustomListAdapter(MainActivity, MainActivity.nameArray);




                    }
                });

        editTextUsername = view.findViewById(R.id.edit_username);


        return builder.create();
    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
         //void applyTexts(String username, String password);
    }*/
}
