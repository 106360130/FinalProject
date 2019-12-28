package com.example.foodemerge.ui.food_info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodemerge.R;

public class FoodInfoFragment extends Fragment {

    private FoodInfoViewModel foodInfoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodInfoViewModel =
                ViewModelProviders.of(this).get(FoodInfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_food_info, container, false);
        final TextView textView = root.findViewById(R.id.text_food_info);
        foodInfoViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}