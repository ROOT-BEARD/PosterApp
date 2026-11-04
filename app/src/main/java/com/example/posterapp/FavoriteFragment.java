package com.example.posterapp;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoriteFragment extends Fragment implements PosterRepository.FavoritesListener, PostersAdpater.OnRemoveFavoriteListener {

    private PosterRepository posterRepository;
    private PostersAdpater favoritesAdapter;
    private TextView textFavoritesCount;
    private RecyclerView favoritesRecyclerView;
    private View layoutFavoritesEmpty;

    public FavoriteFragment(){
        super(R.layout.fragment_favorites);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textFavoritesCount = view.findViewById(R.id.textFavoritesCount);
        favoritesRecyclerView = view.findViewById(R.id.favoritesRecyclerView);
        layoutFavoritesEmpty = view.findViewById(R.id.layoutFavoritesEmpty);

        posterRepository = PosterRepository.getInstance(requireContext());

        favoritesAdapter = new PostersAdpater(posterRepository.getFavorites(), this);
        favoritesRecyclerView.setAdapter(favoritesAdapter);

        posterRepository.addFavoritesListener(this);

        refresh();
    }

    @Override
    public void onDestroyView(){
        posterRepository.removeFavoritesListener(this);
        super.onDestroyView();
    }

    private void refresh() {
        List<Posters> favorites = posterRepository.getFavorites();
        favoritesAdapter.setPosters(favorites);

        int count = favorites.size();
        textFavoritesCount.setText(getResources().getQuantityString(R.plurals.favorites_count, count, count));

        boolean isEmpty = favorites.isEmpty();
        textFavoritesCount.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        favoritesRecyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        layoutFavoritesEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onFavoritesChanged() {
        refresh();
    }

    @Override
    public void onRemoveFavorite(Posters poster) {
        posterRepository.removeFavorite(poster.id);
    }
}
