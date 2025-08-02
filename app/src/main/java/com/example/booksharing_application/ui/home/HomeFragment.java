package com.example.booksharing_application.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.booksharing_application.R;

public class HomeFragment extends Fragment {

    private TextView homeTitle;
    private TextView homeDescription;
    private TextView extraInfo;
    private ImageView featuredImage;
    private ImageView shareBookIcon;
    private ImageView sharedBooksIcon;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeTitle = view.findViewById(R.id.homeTitle);
        homeDescription = view.findViewById(R.id.homeDescription);
        extraInfo = view.findViewById(R.id.extraInfo);
        featuredImage = view.findViewById(R.id.featuredImage);
        shareBookIcon = view.findViewById(R.id.shareBook);
        sharedBooksIcon = view.findViewById(R.id.sharedBooks);

        homeTitle.setText("Welcome to Book Sharing App!");
        homeDescription.setText("Discover, share, and explore a variety of books in our collection. Stay connected with fellow book lovers and build your library.");
        extraInfo.setText("Join the community of book enthusiasts!");

        shareBookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_sharebook);
            }
        });

        sharedBooksIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_sharedbooks);
            }
        });
    }
}
