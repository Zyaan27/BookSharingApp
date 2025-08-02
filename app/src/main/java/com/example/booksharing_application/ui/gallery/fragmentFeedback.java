package com.example.booksharing_application.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.booksharing_application.R;

public class fragmentFeedback extends Fragment {

    private EditText feedbackInput;
    private RatingBar ratingBar;
    private Button submitFeedbackButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        feedbackInput = view.findViewById(R.id.feedbackInput);
        ratingBar = view.findViewById(R.id.ratingBar);
        submitFeedbackButton = view.findViewById(R.id.submitFeedbackButton);

        submitFeedbackButton.setOnClickListener(v -> {
            String feedback = feedbackInput.getText().toString().trim();
            float rating = ratingBar.getRating();

            if (feedback.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter your feedback.", Toast.LENGTH_SHORT).show();
            } else if (rating == 0) {
                Toast.makeText(getActivity(), "Please provide a rating.", Toast.LENGTH_SHORT).show();
            } else {
                String message = "Thank you for your feedback!\nRating: " + rating + "\nFeedback: " + feedback;
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                feedbackInput.setText("");
                ratingBar.setRating(0);
            }
        });

        return view;
    }
}
