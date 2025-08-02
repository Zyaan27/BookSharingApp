package com.example.booksharing_application.ui.gallery;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.booksharing_application.R;

public class fragmentShareBook extends Fragment {

    private EditText bookTitleInput;
    private EditText bookAuthorInput;
    private Button shareBookButton;
    private BookViewModel bookViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sharebook, container, false);

        bookViewModel = new ViewModelProvider(requireActivity()).get(BookViewModel.class);

        bookTitleInput = view.findViewById(R.id.bookTitleInput);
        bookAuthorInput = view.findViewById(R.id.bookAuthorInput);
        shareBookButton = view.findViewById(R.id.shareBookButton);

        shareBookButton.setOnClickListener(v -> shareBook());

        return view;
    }

    private void shareBook() {
        String bookTitle = bookTitleInput.getText().toString().trim();
        String bookAuthor = bookAuthorInput.getText().toString().trim();

        if (TextUtils.isEmpty(bookTitle)) {
            Toast.makeText(getContext(), "Please enter the book title.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(bookAuthor)) {
            Toast.makeText(getContext(), "Please enter the author's name.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add the book details to the ViewModel
        bookViewModel.addBook(bookTitle, bookAuthor);

        Toast.makeText(getContext(), "Book shared successfully!", Toast.LENGTH_LONG).show();

        bookTitleInput.setText("");
        bookAuthorInput.setText("");
    }
}

