package com.example.uj.mebarvendor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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


    private DatabaseReference mDatabase;
    private StorageReference storageReference;

    private RecyclerView recyclerView;
    private MyAdaptor myAdaptor;
    private RecyclerView.LayoutManager layoutManager;
    private List<Data> listdata = new ArrayList<Data>();

    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<Integer> mstock = new ArrayList<>();
    private ArrayList<Integer> mprice = new ArrayList<>();
    private ArrayList<Integer> mquan = new ArrayList<>();
    private ArrayList<String> mdesc = new ArrayList<>();
    private ArrayList<String> mImg = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer);

        storageReference = FirebaseStorage.getInstance().getReference();
        recyclerView = findViewById(R.id.drawer_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mDatabase = FirebaseDatabase.getInstance().getReference("Beer");

        myAdaptor = new MyAdaptor(getData(),this);
        recyclerView.setAdapter(myAdaptor);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dm : dataSnapshot.getChildren()) {
                    if(dm.child("user").getValue(String.class).equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        String name = dm.child("title").getValue(String.class);
                        int price = dm.child("price").getValue(Integer.class);
                        String desc = dm.child("desc").getValue(String.class);
                        String image = dm.child("imageUrl").getValue(String.class);
                        title.add(name);
                        mdesc.add(desc);
                        mprice.add(price);
                        mImg.add(image);
                        Data d = dm.getValue(Data.class);
                        d.setKey(dm.getKey());
                    }
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
            listdata.add(new Data(mImg.get(i), title.get(i), mprice.get(i), mdesc.get(i),"heniken"));
        }
        return listdata;

    }


}


