package com.example.aarav.pizzasandbitsapplication;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PastaMaterialFragment extends Fragment {


    public PastaMaterialFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView pastaRecycler=(RecyclerView)inflater.inflate(R.layout.fragment_pasta_material,container,false);
        String[] pastaNames=new String[Pasta.pastas.length];
        for(int i=0; i<pastaNames.length;i++){
            pastaNames[i]=Pasta.pastas[i].getName();
        }
        int[] pastaImages=new int[Pasta.pastas.length];
        for(int i=0; i<pastaNames.length;i++){
            pastaImages[i]=Pasta.pastas[i].getImageResourceId();
        }
        CaptionedImagesAdapter adapter=new CaptionedImagesAdapter(pastaNames,pastaImages);
        pastaRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        pastaRecycler.setLayoutManager(layoutManager);
        return pastaRecycler;
    }

}
