package com.example.bangkumist.moviecatalogue.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.bangkumist.moviecatalogue.R;
import com.example.bangkumist.moviecatalogue.model.Results;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.bangkumist.moviecatalogue.utils.Utils.POSTER_URL;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.CategoryViewHolder> {
    private List<Results> results;

    public FilmAdapter(Context context) {
        Context context1 = context;
    }

    public void setFilmItems(List<Results> filmItems) {
        this.results = filmItems;
    }
    public List<Results> getList(){
        return results;
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_items, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int i) {
        final Results mResults = results.get(i);
        Picasso .get()
                .load(POSTER_URL + mResults.getmPosterPath())
                .placeholder(R.drawable.ic_broken_image)
                .error(R.drawable.ic_broken_image)
                .into(holder.imgPhoto);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_item_photo)
        ImageView imgPhoto;
        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
