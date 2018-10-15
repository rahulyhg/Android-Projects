package com.example.uj.sos;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class addcontactActivity extends AppCompatActivity {

    String mobilenumber = null;
    ListView listview;
    EditText taskEditText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontact);

        taskEditText = new EditText(this);
        listview =findViewById(R.id.contacttist);
        updateui();

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                String str = listview.getItemAtPosition(i).toString();
                AlertDialog.Builder dialog = new AlertDialog.Builder(addcontactActivity.this);
                dialog.setTitle("Want to delete "+str+" number");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                       deleteelement(i);
                    }
                });
                dialog.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = dialog.create();
                alert.show();
//                Toast.makeText(addcontactActivity.this, str, Toast.LENGTH_SHORT).show();
                return true;

            }
        });
    }

    public void addcontact(View v){
        LayoutInflater li = this.getLayoutInflater();
        View promptsView = li.inflate(R.layout.addcontactdialog, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Add emergency contact number");
                dialog.setView(promptsView);
                final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mobilenumber = String.valueOf(userInput.getText());
                        Toast.makeText(addcontactActivity.this, mobilenumber, Toast.LENGTH_SHORT).show();
                        storedata(mobilenumber);
                        updateui();
                    }
                });
                dialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                dialog.create();
                dialog.show();
    }


    public void storedata(String mobilenumber){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String temp = sharedPreferences.getString("Contact", null);
        if (temp == null){
            temp = mobilenumber;
        }else if(temp != null){
            temp = temp+","+mobilenumber;
        }
        editor.putString("Contact",temp);
        editor.apply();
        Toast.makeText(this, mobilenumber, Toast.LENGTH_SHORT).show();
    }

    public void deleteelement(int j){

        String[] contact =  getContactList();
        List<String> list = new ArrayList<String>(Arrays.asList(contact));
        list.remove(contact[j]);
        contact = list.toArray(new String[0]);

        String updated = convertarrayintostring(contact);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Contact",updated);
        editor.apply();
        updateui();
    }

    public String convertarrayintostring(String[] contact){
        String updatedStr ="";
        if (contact != null) {
            for (int i = 0; i < contact.length;i++) {
                if (contact[i] != "") {
                    updatedStr = updatedStr + contact[i] + ",";
                }else {

                }
            }
        }else {
            Toast.makeText(this, "List is Empty", Toast.LENGTH_SHORT).show();
        }
        return updatedStr;
    }

    public void updateui(){
        ArrayList <String> cno = new ArrayList<>();
        String[] contact;
        contact = getContactList();
        if (contact != null){
            for(int i = 0;i<contact.length;i++){
                cno.add(contact[i]);
            }
            ArrayAdapter adapter = new ArrayAdapter(this, R.layout.textfield, R.id.textView3, cno);
            listview.setAdapter(adapter);

        }else
        {
            Toast.makeText(this, "Empty List", Toast.LENGTH_SHORT).show();
        }
    }


    public String[] getContactList(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String contactno = sharedPreferences.getString("Contact",null);
        if (contactno == null){
            return null;
        }
        return convertStringToArray(contactno);
    }

    private static String[] convertStringToArray(String str){

        String[] arr = str.split(",");
        return arr;
    }
}