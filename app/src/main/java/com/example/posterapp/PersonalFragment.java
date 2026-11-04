package com.example.posterapp;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//page to hold picture of my dog and a short description
public class PersonalFragment extends Fragment {

    // constructor to connect to the fragment_personal xml
    public PersonalFragment(){
        super(R.layout.fragment_personal);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
