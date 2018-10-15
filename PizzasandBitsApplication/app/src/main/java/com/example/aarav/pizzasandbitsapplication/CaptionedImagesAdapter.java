package com.example.aarav.pizzasandbitsapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {
private String[] captions;
private int[] imageIds;
private Listener listener;

    public CaptionedImagesAdapter(String[] captions, int[] imageIds){
          this.captions=captions;
          this.imageIds=imageIds;
    }

public static interface Listener{
        public void onClick(int position);
}

public void setListener(Listener listener){
        this.listener=listener;
}

public static class ViewHolder extends RecyclerView.ViewHolder{
    private CardView cardView;
    public ViewHolder(CardView c){
        super(c);
        cardView=c;
    }
}
    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       CardView cv= (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image,parent,false);

        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(CaptionedImagesAdapter.ViewHolder holder, final int position) {
     CardView cardView=holder.cardView;
        ImageView imageView=cardView.findViewById(R.id.info_image);
        Drawable drawable=cardView.getResources().getDrawable(imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions[position]);
        TextView textView=cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onClick(position);
                }
                Intent intent=new Intent(v.getContext(),PizzaDetailActivity.class);
                intent.putExtra("EXTRA_PIZZANO",position);
                v.getContext().startActivity(intent);
}
        });
    }

    @Override
    public int getItemCount() {
        return captions.length;
    }
}
