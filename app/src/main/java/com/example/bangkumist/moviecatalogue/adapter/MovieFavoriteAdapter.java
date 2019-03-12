package com.example.bangkumist.moviecatalogue.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bangkumist.moviecatalogue.R;
import com.example.bangkumist.moviecatalogue.activity.DetailActivity;
import com.example.bangkumist.moviecatalogue.model.Results;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.bangkumist.moviecatalogue.activity.DetailActivity.EXTRA_DATA;
import static com.example.bangkumist.moviecatalogue.utils.Utils.POSTER_URL;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.CategoryViewHolder> {

    private Cursor cursor;

    public MovieFavoriteAdapter(Context context) {
        Context context1 = context;
    }

    public void setListMovie(Cursor listMovie) {
        this.cursor = listMovie;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_items, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, int position) {
        final Results result = getItem(position);
        Picasso.get()
                .load(POSTER_URL + result.getmPosterPath())
                .placeholder(R.drawable.ic_broken_image)
                .error(R.drawable.ic_broken_image)
                .into(holder.imgPhoto);
        holder.vote.setText(result.getmVoteC());
        holder.lang.setText(result.getmLanguage());
        holder.over.setText(result.getmOverview());
        holder.tvTitle.setText(result.getmTitle());
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra(EXTRA_DATA, result);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    private Results getItem(int position){
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Results(cursor);
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_item_photo)
        ImageView imgPhoto;
        @BindView(R.id.btnDetail)
        Button btnDetail;
        @BindView(R.id.vote)
        TextView vote;
        @BindView(R.id.tv_lang)
        TextView lang;
        @BindView(R.id.tv_over)
        TextView over;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
