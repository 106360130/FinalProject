//資料庫存取有問題

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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        /*
        //DATABASE_FOOD_INFO，存取資料
        DatabaseForm test_database = new DatabaseForm();
        test_database.food_name = "chicken";
        test_database.food_cals = "100";
        test_database.food_protein = "30";
        test_database.food_fat = "0";
        test_database.food_carbs = "5.1";

        DatabaseFunction.getInstance().addDatabase(test_database);//add para agregar algo en donde ya habia algo
        DatabaseFunction.getInstance().saveDatabase();//guardar lo q se agrego

        Log.e("TEST_DATEBASE : ", "food name : " + test_database.food_name);
        Log.e("TEST_DATEBASE : ", "food cals : " + test_database.food_cals);
        Log.e("TEST_DATEBASE : ", "food protein : " + test_database.food_protein);
        Log.e("TEST_DATEBASE : ", "food fat : " + test_database.food_fat);
        Log.e("TEST_DATEBASE : ", "food carbs : " + test_database.food_carbs);

        //DATABASE_FOOD_INFO，存取資料

        //DATABASE_FOOD_INFO，讀取資料
        ArrayList<DatabaseForm> dialog_foods = DatabaseFunction.getInstance().getDatabase();  //取得剛剛儲存的資料
        Log.e("dialog_foods ; " , String.format("%d" , dialog_foods.size()));

        //DatabaseForm dailog_food = dialog_foods.get(0);  //取第一筆資料
        DatabaseForm test_database2 = dialog_foods.get(dialog_foods.size()-1);  //取最後一筆資料

        Log.e("TEST_DATEBASE2 : ", "food name : " + test_database2.food_name);
        Log.e("TEST_DATEBASE2 : ", "food cals : " + test_database2.food_cals);
        Log.e("TEST_DATEBASE2 : ", "food protein : " + test_database2.food_protein);
        Log.e("TEST_DATEBASE2 : ", "food fat : " + test_database2.food_fat);
        Log.e("TEST_DATEBASE2 : ", "food carbs : " + test_database2.food_carbs);
        //DATABASE_FOOD_INFO，讀取資料
        */




        /*
        //DATABASE_SHOPPING_LIST，存取資料
        DatabaseForm test_database_shopping = new DatabaseForm();
        test_database_shopping.food_name = "456";
        test_database_shopping.food_price = "200";
        test_database_shopping.food_EXP = "2011212";

        DatabaseFunction.getInstance().addDatabaseShoppingList(test_database_shopping);
        DatabaseFunction.getInstance().saveDatabaseShoppingList();

        Log.e("TEST_SHOPPING_LIST : ", "food name : " + test_database_shopping.food_name);
        Log.e("TEST_SHOPPING_LIST : ", "food price : " + test_database_shopping.food_price);
        Log.e("TEST_SHOPPING_LIST : ", "food EXP : " + test_database_shopping.food_EXP);
        //DATABASE_SHOPPING_LIST，存取資料

        //DATABASE_SHOPPING_LIST，讀取資料
        ArrayList<DatabaseForm> test_database_shopping2 = DatabaseFunction.getInstance().getDatabaseShoppingList();  //取得剛剛儲存的資料
        Log.e("TEST_SHOPPING_LIST2 : " , "data : " + String.format("%d" , test_database_shopping2.size()));

        //DatabaseForm dailog_food = dialog_foods.get(0);  //取第一筆資料
        DatabaseForm test_database_shopping22 = test_database_shopping2.get(test_database_shopping2.size()-1);  //取最後一筆資料

        Log.e("TEST_SHOPPING_LIST2 : ", "food name : " + test_database_shopping22.food_name);
        Log.e("TEST_SHOPPING_LIST2 : ", "food price : " + test_database_shopping22.food_price);
        Log.e("TEST_SHOPPING_LIST2 : ", "food EXP : " + test_database_shopping22.food_EXP);
        //DATABASE_SHOPPING_LIST，讀取資料


        //DATABASE_SHOPPING_LIST，刪除指定名字的字串，可以用
        ArrayList<DatabaseForm> look_food = DatabaseFunction.getInstance().getDatabaseShoppingList();
        Log.e("NOW_SHOPPING_LIST : ", String.format("%d", look_food.size()));
        String remove_food = "123";
        DatabaseFunction.getInstance().removeDatabaseShoppingList(remove_food);
        //look_food.remove(0);
        //DatabaseFunction.getInstance().setDatabase(look_food);
        DatabaseFunction.getInstance().saveDatabaseShoppingList();
        Log.e("NOW_SHOPPING_LIST : ", String.format("%d", look_food.size()));
        //DATABASE_SHOPPING_LIST，刪除指定名字的字串，可以用

        */



        /*
        //DATABASE_HOME_FOOD，存取資料
        DatabaseForm test_database_home_food = new DatabaseForm();
        test_database_home_food.food_name = "456";
        test_database_home_food.food_EXP = "2011212";
        DatabaseFunction.getInstance().addDatabaseHomeFood(test_database_home_food);
        DatabaseFunction.getInstance().saveDatabaseHomeFood();

        Log.e("TEST_HOME_FOOD : ", "food name : " + test_database_home_food.food_name);
        Log.e("TEST_HOME_FOOD : ", "food EXP : " + test_database_home_food.food_EXP);
        //DATABASE_HOME_FOOD，存取資料

        //DATABASE_HOME_FOOD，讀取資料
        ArrayList<DatabaseForm> test_database_home_food2 = DatabaseFunction.getInstance().getDatabaseHomeFood();  //取得剛剛儲存的資料
        Log.e("TEST_HOME_FOOD2 : " , "data : " + String.format("%d" , test_database_home_food2.size()));

        //DatabaseForm dailog_food = dialog_foods.get(0);  //取第一筆資料
        DatabaseForm test_database_home_food22 = test_database_home_food2.get(test_database_home_food2.size()-1);  //取最後一筆資料

        Log.e("TEST_HOME_FOOD2 : ", "food name : " + test_database_home_food22.food_name);
        Log.e("TEST_HOME_FOOD2 : ", "food EXP : " + test_database_home_food22.food_EXP);
        //DATABASE_HOME_FOOD，讀取資料
        */



        


        /*
        //DATABASE_MONEY，存取資料
        DatabaseForm test_database_money = new DatabaseForm();
        test_database_money.budget = "456";
        test_database_money.cost = "123";
        test_database_money.balance = "89";
        DatabaseFunction.getInstance().addDatabaseMoney(test_database_money);
        DatabaseFunction.getInstance().saveDatabaseMoney();

        Log.e("TEST_MONEY : ", "budget : " + test_database_money.budget);
        Log.e("TEST_MONEY : ", "cost : " + test_database_money.cost);
        Log.e("TEST_MONEY : ", "balance : " + test_database_money.balance);
        //DATABASE_HOME_FOOD，存取資料

        //DATABASE_HOME_FOOD，讀取資料
        ArrayList<DatabaseForm> test_database_money2 = DatabaseFunction.getInstance().getDatabaseMoney();  //取得剛剛儲存的資料
        Log.e("TEST_MONEY2 : " , "data : " + String.format("%d" , test_database_money2.size()));

        //DatabaseForm dailog_food = dialog_foods.get(0);  //取第一筆資料
        DatabaseForm test_database_money22 = test_database_money2.get(test_database_money2.size()-1);  //取最後一筆資料

        Log.e("TEST_MONEY2 : ", "budget : " + test_database_money22.budget);
        Log.e("TEST_MONEY2 : ", "cost : " + test_database_money22.cost);
        Log.e("TEST_MONEY2 : ", "balance : " + test_database_money22.balance);

        //DATABASE_MONEY，讀取資料
        */



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
