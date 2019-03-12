package com.bangkumist.bintang.favorite.adapter;

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

import com.bangkumist.bintang.favorite.DetailTvActivity;
import com.bangkumist.bintang.favorite.R;
import com.bangkumist.bintang.favorite.model.TvResults;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bangkumist.bintang.favorite.Utils.POSTER_URL;

public class TvFavoriteAdapter extends RecyclerView.Adapter<TvFavoriteAdapter.CategoryViewHolder> {

    private Cursor cursor;

    public TvFavoriteAdapter(Context context) {
        Context context1 = context;
    }

    public void setListMovie(Cursor listMovie) {
        this.cursor = listMovie;
    }

    @NonNull
    @Override
    public TvFavoriteAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_items, parent, false);
        return new TvFavoriteAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvFavoriteAdapter.CategoryViewHolder holder, int position) {
        final TvResults result = getItem(position);
        Picasso.get()
                .load(POSTER_URL + result.getmPosterPath())
                .placeholder(R.drawable.ic_broken_image)
                .error(R.drawable.ic_broken_image)
                .into(holder.imgPhoto);
        holder.vote.setText(result.getmVoteC());
        holder.lang.setText(result.getmLanguage());
        holder.over.setText(result.getmOverview());
        holder.tvName.setText(result.getmName());
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailTvActivity.class);
                intent.putExtra(DetailTvActivity.EXTRA_DATA_TV, result);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    private TvResults getItem(int position){
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new TvResults(cursor);
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
        TextView tvName;
        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

