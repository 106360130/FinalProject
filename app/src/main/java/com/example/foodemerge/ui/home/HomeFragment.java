//可以切換fragment，但動畫需要修改

package com.example.foodemerge.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.foodemerge.Database.DatabaseForm;
import com.example.foodemerge.Database.DatabaseFunction;
import com.example.foodemerge.MainActivity;
import com.example.foodemerge.R;
import com.example.foodemerge.ui.shopping_list.ShoppingListFragment;

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
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final View cardview_shopping_list = root.findViewById(R.id.shoppingList);
        View cardview_home_food = root.findViewById(R.id.haveFood);
        View cardview_food_info = root.findViewById(R.id.foodInfo);
        View cardview_setting = root.findViewById(R.id.setting);
        View cardview_instructions = root.findViewById(R.id.instructions);

        cardview_shopping_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_shopping_list);


            }
        });

        cardview_home_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_food);


            }
        });

        cardview_food_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_food_info);


            }
        });

        cardview_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_setting);

            }
        });

        cardview_instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_instructions);

            }
        });

        return root;
    }



}