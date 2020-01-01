package com.example.foodemerge.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodemerge.Database.DatabaseForm;
import com.example.foodemerge.Database.DatabaseFunction;
import com.example.foodemerge.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //測試資料庫提取資料，測試成功
        /*
        ArrayList<DatabaseForm> dialog_foods = DatabaseFunction.getInstance().getDatabase();  //取得剛剛儲存的資料
        Log.e("dialog_foods ; " , String.format("%d" , dialog_foods.size()));
        //DatabaseForm dailog_food = dialog_foods.get(0);  //取第一筆資料
        DatabaseForm test_database2 = dialog_foods.get(dialog_foods.size()-1);  //取最後一筆資料

        Log.e("TEST_DATEBASE2 : ", "food name : " + test_database2.food_neme);
        Log.e("TEST_DATEBASE2 : ", "food cals : " + test_database2.food_cals);
        Log.e("TEST_DATEBASE2 : ", "food protein : " + test_database2.food_protein);
        Log.e("TEST_DATEBASE2 : ", "food fat : " + test_database2.food_fat);
        Log.e("TEST_DATEBASE2 : ", "food carbs : " + test_database2.food_carbs);
        */
        //測試資料庫提取資料，測試成功


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}