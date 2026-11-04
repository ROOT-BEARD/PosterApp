package com.example.posterapp;


import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PostersFragment extends Fragment implements posterListener {

    private Button buttonAddToWatchList;

    private PosterRepository posterRepository;

    public PostersFragment(){
        super(R.layout.fragment_posters);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //find the list and button
        RecyclerView posterRecyclerView = view.findViewById(R.id.posterRecyclerView);
        buttonAddToWatchList = view.findViewById(R.id.buttonAddToWatchList);

        // the posters come from a shared repository
        posterRepository = PosterRepository.getInstance(requireContext());
        List<Posters> posters = posterRepository.getAllPosters();

        //connects the list to its adapter
        final PostersAdpater postersAdpater = new PostersAdpater(posters, (posterListener) this);
        posterRecyclerView.setAdapter(postersAdpater);

        //select live in the repo and survive rotation, so button shows up again
        OnPosterAction(!postersAdpater.getSelectedPosters().isEmpty());

        buttonAddToWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Posters> selectedPosters = postersAdpater.getSelectedPosters();

                int added = posterRepository.addFavorites(selectedPosters);

                posterRepository.clearSelection();
                postersAdpater.notifyDataSetChanged();
                OnPosterAction(false);

                String message;
                if(added>0){
                    message = getResources().getQuantityString(R.plurals.favorites_added,added,added);
                }else {
                    message = getString(R.string.favorites_already_added);
                }
                Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void OnPosterAction(Boolean isSelected){
        if(isSelected){
            buttonAddToWatchList.setVisibility(View.VISIBLE);
        } else {
            buttonAddToWatchList.setVisibility(View.GONE);
        }
    }
}
