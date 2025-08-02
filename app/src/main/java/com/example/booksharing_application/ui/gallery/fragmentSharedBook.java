package com.example.booksharing_application.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.booksharing_application.R;

import java.util.List;

public class fragmentSharedBook extends Fragment {

    private ListView sharedBooksListView;
    private BookViewModel bookViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sharedbook, container, false);

        bookViewModel = new ViewModelProvider(requireActivity()).get(BookViewModel.class);

        sharedBooksListView = view.findViewById(R.id.sharedBooksListView);

        bookViewModel.getSharedBooks().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> books) {
                // Update the ListView with the current list of books
                sharedBooksListView.setAdapter(new ArrayAdapter<>(
                        getContext(),
                        android.R.layout.simple_list_item_1,
                        books
                ));
            }
        });

        return view;
    }
}
