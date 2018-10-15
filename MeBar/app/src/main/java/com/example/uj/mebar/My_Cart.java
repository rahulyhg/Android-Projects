package com.example.uj.mebar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class My_Cart extends AppCompatActivity {

    private DatabaseReference mDatabase,myUser;
    private StorageReference storageReference;

    private RecyclerView recyclerView;
    private MyCartAdaptor myCartAdaptor;
    private RecyclerView.LayoutManager layoutManager;
    private List<Data> listdata = new ArrayList<Data>();

    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<Integer> mstock = new ArrayList<Integer>();
    private ArrayList<Integer> mprice = new ArrayList<>();
    private ArrayList<Integer> mquan = new ArrayList<>();
    private ArrayList<String> mdesc = new ArrayList<String>();
    private ArrayList<String> mImg = new ArrayList<String>();
    private ArrayList<String> mSoldBy = new ArrayList<String>();
    private TextView total;
    private static int count = 0;
    private int sum = 0;

    private Button proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__cart);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        total = findViewById(R.id.totalamount);

        storageReference = FirebaseStorage.getInstance().getReference();
        proceed = findViewById(R.id.proceed);
        recyclerView = findViewById(R.id.drawer_list_cart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(count == 0) {
            myCartAdaptor = new MyCartAdaptor(getData(),this);
            recyclerView.setAdapter(myCartAdaptor);
        }
        mDatabase = FirebaseDatabase.getInstance().getReference("Cart");
        final String uid = user.getPhoneNumber();
        myUser = mDatabase.child(uid);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CheckoutActivity.class));
            }
        });
        myUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                title.clear();
                mdesc.clear();
                mquan.clear();
                mprice.clear();
                mstock.clear();
                mImg.clear();
                myCartAdaptor = null;
                for (DataSnapshot dm : dataSnapshot.getChildren()) {
                    String name = dm.child("Name").getValue(String.class);
                    int quan = dm.child("Quan").getValue(Integer.class);
                    int price = dm.child("Price").getValue(Integer.class);
                    String desc = dm.child("Desc").getValue(String.class);
                    String image = dm.child("Img").getValue(String.class);
                    String soldBy = dm.child("SoldBy").getValue(String.class);
                    if(quan !=0) {
                        title.add(name);
                        mdesc.add(desc);
                        mprice.add(price);
                        mquan.add(quan);
                        mImg.add(image);
                        mSoldBy.add(soldBy);
                        sum += quan*price;
                    }
                    count =1;
                }
                updateUI(sum);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(My_Cart.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(int a) {
        myCartAdaptor = new MyCartAdaptor(getData(),this);
        recyclerView.setAdapter(myCartAdaptor);
        total.setText("Rs. "+a);
    }

    public List<Data> getData(){
        for (int i = 0; i < title.size(); i++) {
            listdata.add(new Data(mImg.get(i), title.get(i), mquan.get(i), mprice.get(i), mdesc.get(i),"heniken",mSoldBy.get(i)));
        }
        return listdata;

    }

}


