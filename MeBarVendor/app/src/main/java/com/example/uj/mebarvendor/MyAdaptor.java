package com.example.uj.mebarvendor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdaptor extends RecyclerView.Adapter<MyviewHolder> {

    List<Data> listdata = new ArrayList<>();
    private Context context;

    public MyAdaptor(){

    }

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
        holder.desc.setText(current.desc);
        holder.price.setText(""+current.price);

        Picasso.get().load(current.getId()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    }

class MyviewHolder extends RecyclerView.ViewHolder{
    public TextView title;
    public TextView desc;
    public TextView price;
    public ImageView imageView;
    MyAdaptor mAdap = new MyAdaptor();

    public MyviewHolder(View itemView) {
        super(itemView);
        title =itemView.findViewById(R.id.item_name);
        desc = itemView.findViewById(R.id.item_short_desc);
        price = itemView.findViewById(R.id.item_price);
        imageView = itemView.findViewById(R.id.product_thumb);
    }

}

