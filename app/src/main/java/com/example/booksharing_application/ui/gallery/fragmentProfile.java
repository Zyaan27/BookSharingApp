package com.example.booksharing_application.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.booksharing_application.R;

public class fragmentProfile extends Fragment {

    private ImageView profileImage;
    private TextView userName, userEmail, userFirstName, userLastName, userAge, userMobile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = view.findViewById(R.id.profileImage);
        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        userFirstName = view.findViewById(R.id.userFirstName);
        userLastName = view.findViewById(R.id.userLastName);
        userAge = view.findViewById(R.id.userAge);
        userMobile = view.findViewById(R.id.userMobile);

        populateProfileData();

        return view;
    }

    private void populateProfileData() {
        profileImage.setImageResource(R.drawable.login);

        userName.setText("John Doe");
        userEmail.setText("john@example.com");
        userFirstName.setText("John");
        userLastName.setText("Doe");
        userAge.setText("20");
        userMobile.setText("7483992");
    }
}
