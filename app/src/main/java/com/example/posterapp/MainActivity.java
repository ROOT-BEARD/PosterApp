package com.example.posterapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements posterListener{

    private Button buttonAddToWatchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //find the list and button
        RecyclerView posterRecyclerView = findViewById(R.id.posterRecyclerView);
        buttonAddToWatchList = findViewById(R.id.buttonAddToWatchList);

        List<Posters> posters = new ArrayList<>();

        Posters the100 = new Posters();
        the100.image = R.drawable.coraline;
        the100.name = "Name";
        the100.rating = 4.5f;
        the100.createdBy = "Created by";
        the100.story = "Story";
        posters.add(the100);

        final PostersAdpater postersAdpater = new PostersAdpater(posters, (posterListener) this);
        posterRecyclerView.setAdapter(postersAdpater);

        buttonAddToWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Posters> selectedPosters = postersAdpater.getSelectedPosters();

                StringBuilder names = new StringBuilder();
                for(int i = 0; i < selectedPosters.size(); i++){
                    if(i > 0){
                        names.append("\n");
                    }
                    names.append(selectedPosters.get(i).name);
                }
                Toast.makeText(MainActivity.this, names.toString(), Toast.LENGTH_SHORT).show();
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