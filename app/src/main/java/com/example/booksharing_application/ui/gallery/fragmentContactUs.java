package com.example.booksharing_application.ui.gallery;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.booksharing_application.R;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.Collections;

public class fragmentContactUs extends Fragment {

    private static final String TAG = "fragmentContactUs";
    private EditText nameInput, emailInput, messageInput, dialInput;
    private Button sendFeedbackButton, dialButton;

    private MongoClient mongoClient;
    private MongoCollection<Document> feedbackCollection;

    private static final int CALL_PERMISSION_REQUEST_CODE = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactus, container, false);

        nameInput = view.findViewById(R.id.nameInput);
        emailInput = view.findViewById(R.id.emailInput);
        messageInput = view.findViewById(R.id.messageInput);
        dialInput = view.findViewById(R.id.dialInput);
        sendFeedbackButton = view.findViewById(R.id.sendFeedback_btn);
        dialButton = view.findViewById(R.id.dialButton);

        // Initialize MongoDB connection
        initializeMongoDB();

        sendFeedbackButton.setOnClickListener(v -> sendFeedback());
        dialButton.setOnClickListener(v -> checkCallPermission());

        return view;
    }

    private void initializeMongoDB() {
        try {
            mongoClient = MongoClients.create(
                    MongoClientSettings.builder()
                            .applyToClusterSettings(builder ->
                                    builder.hosts(Collections.singletonList(new ServerAddress("10.0.2.2", 27017))))
                            .build()
            );

            MongoDatabase mongoDatabase = mongoClient.getDatabase("bookSharingApp");
            feedbackCollection = mongoDatabase.getCollection("feedback");

            Log.d(TAG, "MongoDB Connected: " + mongoDatabase.getName());
            Toast.makeText(getActivity(), "Connected to MongoDB successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Error initializing MongoDB: " + e.getMessage(), e);
            Toast.makeText(getActivity(), "Error connecting to MongoDB.", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendFeedback() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String message = messageInput.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "Name: " + name);
        Log.d(TAG, "Email: " + email);
        Log.d(TAG, "Message: " + message);

        Document feedbackDoc = new Document("name", name)
                .append("email", email)
                .append("message", message);

        new Thread(() -> {
            try {
                if (feedbackCollection != null) {
                    feedbackCollection.insertOne(feedbackDoc);
                    Log.d(TAG, "Feedback saved: " + feedbackDoc.toJson());
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getActivity(), "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
                        clearInputs();
                    });
                } else {
                    Log.e(TAG, "Feedback collection is null. Check MongoDB connection.");
                    requireActivity().runOnUiThread(() ->
                            Toast.makeText(getActivity(), "Feedback submission failed. Database issue.", Toast.LENGTH_SHORT).show()
                    );
                }
            } catch (MongoException e) {
                Log.e(TAG, "Error saving feedback: " + e.getMessage(), e);
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getActivity(), "Error submitting feedback.", Toast.LENGTH_SHORT).show()
                );
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error: " + e.getMessage(), e);
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getActivity(), "An unexpected error occurred.", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }

    private void clearInputs() {
        nameInput.setText("");
        emailInput.setText("");
        messageInput.setText("");
    }

    private void checkCallPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
        } else {
            makePhoneCall();
        }
    }

    private void makePhoneCall() {
        String phoneNumber = dialInput.getText().toString().trim();

        if (phoneNumber.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        } catch (SecurityException e) {
            Toast.makeText(getActivity(), "Permission denied for making calls.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(getActivity(), "Call permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}

