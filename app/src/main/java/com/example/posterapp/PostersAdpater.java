package com.example.posterapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class PostersAdpater extends RecyclerView.Adapter<PostersAdpater.PosterViewHolder> {

    public enum Mode { SELECT, FAVORITE }

    public interface OnRemoveFavoriteListener {
        void onRemoveFavorite(Posters poster);
    }

    private Mode mode = Mode.SELECT;
    private OnRemoveFavoriteListener removeFavoriteListener;

    public PostersAdpater(List<Posters> posters, OnRemoveFavoriteListener removeFavoriteListener) {
        this.posters = posters;
        this.mode = Mode.FAVORITE;
        this.removeFavoriteListener = removeFavoriteListener;
    }

    public void setPosters(List<Posters> posters) {
        this.posters = posters;
        notifyDataSetChanged();
    }

    private List<Posters> posters;
    private posterListener posterListener;

    public PostersAdpater(List<Posters> posters, posterListener posterListener){
        this.posters = posters;
        this.posterListener = posterListener;
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PosterViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_container_poster,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {
        holder.bindPosters(posters.get(position));
    }

    @Override
    public int getItemCount() {
        return posters.size();
    }

    public List<Posters> getSelectedPosters(){
        List<Posters> selectedPosters = new ArrayList<>();
        for(Posters poster : posters){
            if(poster.isSelected){
                selectedPosters.add(poster);
            }
        }
        return selectedPosters;
    }


    class PosterViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout layoutPosters;
        View viewBackground;
        RoundedImageView imagePosters;
        TextView textName, textCreatedBy, textStory;
        RatingBar ratingBar;
        ImageView imageSelected;
        ImageView imageRemove;

        public PosterViewHolder(@NonNull View itemView){
            super(itemView);
            layoutPosters = itemView.findViewById(R.id.layoutPosters);
            viewBackground = itemView.findViewById(R.id.viewBackground);
            imagePosters = itemView.findViewById(R.id.imagePosters);
            textName = itemView.findViewById(R.id.textName);
            textCreatedBy = itemView.findViewById(R.id.textCreatedBy);
            textStory = itemView.findViewById(R.id.textStory);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            imageSelected = itemView.findViewById(R.id.imageSelected);
            imageRemove = itemView.findViewById(R.id.imageRemove);
        }

        void bindPosters(final Posters posters){
            imagePosters.setImageResource(posters.image);
            textName.setText(posters.name);
            textCreatedBy.setText(posters.createdBy);
            textStory.setText(posters.story);
            ratingBar.setRating(posters.rating);

            if(mode == Mode.FAVORITE){
                bindFavorite(posters);
                return;
            }
            imageRemove.setVisibility(View.GONE);

            //show if the poser is selected
            if(posters.isSelected){
                viewBackground.setBackgroundResource(R.drawable.poster_selected_background);
                imageSelected.setVisibility(View.VISIBLE);
            } else {
                viewBackground.setBackgroundResource(R.drawable.poster_background);
                imageSelected.setVisibility(View.GONE);
            }

            layoutPosters.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    if(posters.isSelected){
                        viewBackground.setBackgroundResource(R.drawable.poster_background);
                        imageSelected.setVisibility(View.GONE);
                        posters.isSelected = false;
                        if(getSelectedPosters().isEmpty()){
                            posterListener.OnPosterAction(false);
                        }
                    } else {
                        viewBackground.setBackgroundResource(R.drawable.poster_selected_background);
                        imageSelected.setVisibility(View.VISIBLE);
                        posters.isSelected = true;
                        posterListener.OnPosterAction(true);
                    }
                }
            });
        }

        private void bindFavorite(final Posters posters){
            viewBackground.setBackgroundResource(R.drawable.poster_background);
            imageSelected.setVisibility(View.GONE);
            layoutPosters.setOnClickListener(null);
            layoutPosters.setClickable(false);

            imageRemove.setVisibility(View.VISIBLE);
            imageRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeFavoriteListener.onRemoveFavorite(posters);
                }
            });
        }

    }
}
