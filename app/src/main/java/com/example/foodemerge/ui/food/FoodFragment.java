package com.example.foodemerge.ui.food;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodemerge.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FoodFragment extends Fragment {
    //本來就有的
    private FoodViewModel foodViewModel;

    private ListView listView_homeFood;
    private ArrayAdapter<String > adapter_homeFood;
    private ArrayList<String> items_homeFood = new ArrayList<>();

        public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodViewModel =
                ViewModelProviders.of(this).get(FoodViewModel.class);
        View root = inflater.inflate(R.layout.fragment_food, container, false);
        final TextView textView = root.findViewById(R.id.text_food);
        foodViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //taking care of the list view
            listView_homeFood = root.findViewById(R.id.listView);
            adapter_homeFood = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items_homeFood);//adapter for handling the database
            listView_homeFood.setAdapter(adapter_homeFood);

        FloatingActionButton add_shopping_item = root.findViewById(R.id.add_shopping_item);
        FloatingActionButton add_food = root.findViewById(R.id.add_food);




        return root;

        }
}