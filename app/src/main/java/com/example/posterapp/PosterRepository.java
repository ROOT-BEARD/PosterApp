package com.example.posterapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PosterRepository {
    public PosterRepository(List<Posters> allPosters, FavoriteStore favoritesStore) {
        this.allPosters = allPosters;
        this.favoritesStore = favoritesStore;
    }

    // Notified whenever favorites are added or removed (Favorite List, badge).
    public interface FavoritesListener{
        void onFavoritesChanged();
    }

    private static PosterRepository instance;

    private final List<Posters> allPosters;
    private final FavoriteStore favoritesStore;
    private final List<FavoritesListener> favoritesListeners = new ArrayList<>();

    // Creates the single instance on first use. It keeps the application context,
    // never an Activity: holding an Activity in a singleton would leak it.
    public static synchronized PosterRepository getInstance(Context context) {
        if (instance == null) {
            instance = new PosterRepository(context.getApplicationContext());
        }
        return instance;
    }

    private PosterRepository(Context appContext) {
        allPosters = Collections.unmodifiableList(createPosters());
        favoritesStore = new FavoriteStore(appContext);

        // Forget saved favorites whose poster no longer exists in the app.
        List<String> validIds = new ArrayList<>();
        for (Posters poster : allPosters) {
            validIds.add(poster.id);
        }
        favoritesStore.retainOnly(validIds);
    }

    public List<Posters> getAllPosters(){
        return allPosters;
    }

    public Posters findById(String id){
        for(Posters posters:allPosters){
            if(posters.id.equals(id)){
                return posters;
            }
        }
        return null;
    }

    // deselects every poster
    public void clearSelection(){
        for(Posters poster : allPosters){
            poster.isSelected = false;
        }
    }

    //get favorite posters, in the order they were added
    public List<Posters> getFavorites(){
        List<Posters> favorites = new ArrayList<>();
        for (String id : favoritesStore.getIds()) {
            Posters poster = findById(id);
            if (poster != null) {
                favorites.add(poster);
            }
        }
        return favorites;
    }

    public int getFavoriteCount(){
        return favoritesStore.getIds().size();
    }

    // Adds posters to favorites, skipping ones already there.
    // Returns how many were new, so the page can show the right message.
    public int addFavorites(List<Posters> posters) {
        List<String> ids = new ArrayList<>();
        for (Posters poster : posters) {
            ids.add(poster.id);
        }
        int added = favoritesStore.addAll(ids);
        if (added > 0) {
            notifyFavoritesChanged();
        }
        return added;
    }

    // Removes one favorite. Returns true if it was a favorite.
    public boolean removeFavorite(String id) {
        boolean removed = favoritesStore.remove(id);
        if (removed) {
            notifyFavoritesChanged();
        }
        return removed;
    }

    // The listener (observer) pattern: pages register to hear about changes.
    // Whoever adds a listener must remove it again (onDestroyView / onDestroy).
    public void addFavoritesListener(FavoritesListener listener) {
        if (!favoritesListeners.contains(listener)) {
            favoritesListeners.add(listener);
        }
    }

    public void removeFavoritesListener(FavoritesListener listener) {
        favoritesListeners.remove(listener);
    }

    private void notifyFavoritesChanged() {
        for(FavoritesListener listener : new ArrayList<>(favoritesListeners)){
            listener.onFavoritesChanged();
        }
    }


    private List<Posters> createPosters() {
        List<Posters> posters = new ArrayList<>();

        Posters coraline = new Posters();
        coraline.id = "coraline";
        coraline.image = R.drawable.coraline;
        coraline.name = "Coraline";
        coraline.rating = 4.5f;
        coraline.createdBy = "Henry Selick";
        coraline.story = "Stop motion movie that is really good";
        posters.add(coraline);

        Posters wallace = new Posters();
        wallace.id = "Wallace and Gromit";
        wallace.image = R.drawable.wallace;
        wallace.name = "Wallace and Gromit";
        wallace.rating = 5f;
        wallace.createdBy = "Nick Park, Steve Box";
        wallace.story = "In this movie Wallace turns into a ware rabbit";
        posters.add(wallace);

        Posters up = new Posters();
        up.id = "UP";
        up.image = R.drawable.up;
        up.name = "UP";
        up.rating = 4f;
        up.createdBy = "Pete Docter";
        up.story = "House flies away on an adventure";
        posters.add(up);

        Posters toy = new Posters();
        toy.id = "Toy Story 3";
        toy.image = R.drawable.toy;
        toy.name = "Toy Story 3";
        toy.rating = 4.2f;
        toy.createdBy = "Lee Unkrich";
        toy.story = "My personal favorite of all the Toy Story movies";
        posters.add(toy);

        Posters Nausicaa = new Posters();
        Nausicaa.id = "nausicaa";
        Nausicaa.image = R.drawable.nausicaa;
        Nausicaa.name = "Nausicaa";
        Nausicaa.rating = 4.7f;
        Nausicaa.createdBy = "Hayao Miyazaki";
        Nausicaa.story = "My favorite Studio Ghibli movie";
        posters.add(Nausicaa);

        return posters;
    }
}
