package com.example.rssreader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.rssreader.API.Data.News;
import com.example.rssreader.Web.WebViewActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<News> list;

    public CardAdapter(List<News> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(
                LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.news_card, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(list.get(i).getTitle());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.URL, list.get(i).getLink());
                Navigation.findNavController(viewHolder.itemView).navigate(R.id.action_fragmentNews_to_webViewActivity, bundle);
            }
        });
        Picasso.get()
                .load(list.get(i).getImg(viewHolder.img.getWidth()))
                .fit()
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.img);

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView img;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            img = itemView.findViewById(R.id.img);

        }
    }
}
