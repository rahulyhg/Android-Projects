package com.example.uj.mebar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MyAdaptor extends RecyclerView.Adapter<MyviewHolder> {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Cart");
    DatabaseReference myCart,myName;

    ArrayAdapter<Integer> adapter ;
    List<Data> listdata = new ArrayList<>();
    private Context context;

    public MyAdaptor(List<Data> listdata, Context context){
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list, parent,false);
        MyviewHolder holder  = new MyviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, int position) {
        final Data current = listdata.get(position);
        holder.title.setText(current.title);
//        holder.icon.setImageResource(current.id);
        holder.desc.setText(current.desc);
        holder.price.setText(""+current.price);
        final String uid = user.getPhoneNumber();
        holder.mSoldBy.setText("Sold By: "+current.mSoldBy);
        Picasso.get().load(current.getId()).into(holder.imageView);

        Integer[] items = new Integer[]{0,1,2,3,4,5};
        adapter = new ArrayAdapter<Integer>(context,android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(adapter);
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            myCart = mDatabase.child(uid);
                            myName = myCart.child(current.title);
                            myName.child("Name").setValue(current.title);
                            myName.child("Quan").setValue(adapterView.getItemAtPosition(i));
                            myName.child("Price").setValue(current.price);
                            myName.child("Desc").setValue(current.desc);
                            myName.child("Img").setValue(current.img);
                            myName.child("SoldBy").setValue(current.getmSoldBy());
                            myName.child("Img").setValue(current.getId());
                    }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }
    }

class MyviewHolder extends RecyclerView.ViewHolder{
    public TextView title;
    public ImageView icon;
    public TextView desc;
    public TextView price;
    public TextView quan;
    public Spinner spinner;
    public ImageView imageView;
    public TextView mSoldBy;

    public MyviewHolder(View itemView) {
        super(itemView);
        title =itemView.findViewById(R.id.item_name);
        icon = itemView.findViewById(R.id.product_thumb);
        desc = itemView.findViewById(R.id.item_short_desc);
        price = itemView.findViewById(R.id.item_price);
        spinner = itemView.findViewById(R.id.quan_spinner);
        imageView = itemView.findViewById(R.id.product_thumb);
        mSoldBy = itemView.findViewById(R.id.soldby);
    }
}
