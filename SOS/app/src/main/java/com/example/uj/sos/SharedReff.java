package com.example.uj.sos;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;


public class SharedReff {

    public SharedPreferences sharedPreferences;
    Context ctx;

    public SharedReff(Context context){
        ctx = context;
        sharedPreferences=  context.getSharedPreferences("Myref",Context.MODE_PRIVATE);
    }

    public void saveData(String cno){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Contact",cno);
        editor.apply();
        Toast.makeText(ctx, " saved  "+cno, Toast.LENGTH_SHORT).show();
    }

    public String loadData(){
        String loadTask = sharedPreferences.getString("Contact","null");
        Toast.makeText(ctx, " load  "+loadTask, Toast.LENGTH_SHORT).show();
        return loadTask;
    }
}
