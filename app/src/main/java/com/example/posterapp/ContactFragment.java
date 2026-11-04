package com.example.posterapp;

import androidx.fragment.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ContactFragment extends Fragment {

    //button used to change the about us description
    Button changeButton;
    // about us description
    TextView desc;

    public ContactFragment(){
        super(R.layout.fragment_contact);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.layoutEmail).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openEmailApp(getString(R.string.contact_email));
            }
        });

        //connects components
        changeButton = view.findViewById(R.id.butonChange);
        desc = view.findViewById(R.id.textAbout);
        //set on click listen for the button
        changeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // when clicked change the text of the about us description
                desc.setText("This is the example I chose to use. This example shows that I completed requirement 3 of the assignment");
            }
        });
    }

    private void openEmailApp(String email){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e){
            Toast.makeText(requireContext(), R.string.no_email_app, Toast.LENGTH_SHORT).show();
        }
    }
}
