package com.example.foodemerge;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.foodemerge.Database.DatabaseForm;
import com.example.foodemerge.Database.DatabaseFunction;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //測試資料庫存取資料，測試成功


        /*
        DatabaseForm test_database = new DatabaseForm();
        test_database.food_neme = "456";
        test_database.food_cals = "200";
        test_database.food_protein = "4.5";
        test_database.food_fat = "0";
        test_database.food_carbs = "55";

        DatabaseFunction.getInstance().addDatabase(test_database);
        DatabaseFunction.getInstance().saveDatabase();

        Log.e("TEST_DATEBASE : ", "food name : " + test_database.food_neme);
        Log.e("TEST_DATEBASE : ", "food cals : " + test_database.food_cals);
        Log.e("TEST_DATEBASE : ", "food protein : " + test_database.food_protein);
        Log.e("TEST_DATEBASE : ", "food fat : " + test_database.food_fat);
        Log.e("TEST_DATEBASE : ", "food carbs : " + test_database.food_carbs);
        */




        //測試資料庫存取資料，測試成功


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_shopping_list, R.id.nav_food_info,
                R.id.nav_food, R.id.nav_instructions, R.id.nav_setting)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
