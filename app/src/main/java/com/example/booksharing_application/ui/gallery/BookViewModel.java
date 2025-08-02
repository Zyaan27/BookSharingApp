package com.example.booksharing_application.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookViewModel extends ViewModel {
    private final MutableLiveData<List<String>> sharedBooks = new MutableLiveData<>();

    public BookViewModel() {
        sharedBooks.setValue(new ArrayList<>()); // Initialize with an empty list
    }

    public void addBook(String title, String author) {
        List<String> currentBooks = sharedBooks.getValue();
        if (currentBooks != null) {
            currentBooks.add("Title: " + title + "\nAuthor: " + author);
            sharedBooks.setValue(currentBooks);
        }
    }

    public LiveData<List<String>> getSharedBooks() {
        return sharedBooks;
    }
}

