package com.example.uj.mebar;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Beer extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private DatabaseReference mDatabase;
    private StorageReference storageReference;

    private RecyclerView recyclerView;
    private MyAdaptor myAdaptor;
    private RecyclerView.LayoutManager layoutManager;
    private List<Data> listdata = new ArrayList<Data>();

    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<Integer> mstock = new ArrayList<Integer>();
    private ArrayList<Integer> mprice = new ArrayList<>();
    private ArrayList<Integer> mquan = new ArrayList<>();
    private ArrayList<String> mdesc = new ArrayList<String>();
    private ArrayList<String> mImg = new ArrayList<String>();
    private ArrayList<String> mSoldBy = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);

        storageReference = FirebaseStorage.getInstance().getReference();

        recyclerView = findViewById(R.id.drawer_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdaptor = new MyAdaptor(getData(),this);
        recyclerView.setAdapter(myAdaptor);
        mDatabase = FirebaseDatabase.getInstance().getReference("Beer");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                title.clear();
                mdesc.clear();
                mquan.clear();
                mprice.clear();
                mstock.clear();
                mImg.clear();
                for (DataSnapshot dm : dataSnapshot.getChildren()) {
                    String name = dm.child("title").getValue(String.class);
                    int quan = dm.child("quan").getValue(Integer.class);
                    int price = dm.child("price").getValue(Integer.class);
                    String desc = dm.child("desc").getValue(String.class);
                    String image = dm.child("imageUrl").getValue(String.class);
                    String soldby = dm.child("seller").getValue(String.class);
                    title.add(name);
                    mdesc.add(desc);
                    mprice.add(price);
                    mquan.add(quan);
                    mImg.add(image);
                    mSoldBy.add(soldby);
                }
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Beer.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI() {
        myAdaptor = new MyAdaptor(getData(),this);
        recyclerView.setAdapter(myAdaptor);


    }


    public List<Data> getData(){
        for (int i = 0; i < title.size(); i++) {
            listdata.add(new Data(mImg.get(i), title.get(i), mquan.get(i), mprice.get(i), mdesc.get(i),"heniken",mSoldBy.get(i)));
        }
        return listdata;
    }

    public void myCart(View v){
        startActivity(new Intent(this, My_Cart.class));
    }
}


