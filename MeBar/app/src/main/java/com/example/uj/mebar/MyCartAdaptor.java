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

public class MyCartAdaptor extends RecyclerView.Adapter<MyCartviewHolder> {

    List<Data> listdata = new ArrayList<>();
    private Context context;

    public MyCartAdaptor(List<Data> listdata, Context context){
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public MyCartviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cartitem_list, parent,false);
        MyCartviewHolder holder  = new MyCartviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyCartviewHolder holder, int position) {
        final Data current = listdata.get(position);
        holder.title.setText(current.title);
//        holder.icon.setImageResource(current.id);
        holder.desc.setText(current.desc);
        holder.price.setText(""+current.price);
        holder.quan.setText(""+current.quan);
        holder.mSoldBycart.setText("Sold By: "+current.mSoldBy);
        Picasso.get().load(current.getId()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }
}

class MyCartviewHolder extends RecyclerView.ViewHolder{
    public TextView title;
    public ImageView icon;
    public TextView desc;
    public TextView price;
    public TextView quan;
    public ImageView imageView;
    public TextView mSoldBycart;

    public MyCartviewHolder(View itemView) {
        super(itemView);
        title =itemView.findViewById(R.id.item_name);
        icon = itemView.findViewById(R.id.product_thumb);
        desc = itemView.findViewById(R.id.item_short_desc);
        quan = itemView.findViewById(R.id.quantity);
        price = itemView.findViewById(R.id.item_price);
        imageView = itemView.findViewById(R.id.product_thumb);
        mSoldBycart = itemView.findViewById(R.id.soldbycart);

    }
}
