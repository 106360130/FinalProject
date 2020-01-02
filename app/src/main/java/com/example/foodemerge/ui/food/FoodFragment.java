package com.example.foodemerge.ui.food;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodemerge.Database.DatabaseForm;
import com.example.foodemerge.Database.DatabaseFunction;
import com.example.foodemerge.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FoodFragment extends Fragment {

    private EditText ed_name_home,ed_price_home;
    private Button change_home;

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


        //DATABASE_HOME_FOOD，讀取資料
        ArrayList<DatabaseForm> test_database_money2 = DatabaseFunction.getInstance().getDatabaseMoney();  //取得剛剛儲存的資料
        Log.e("TEST_MONEY2 : " , "data : " + String.format("%d" , test_database_money2.size()));

        //DatabaseForm dailog_food = dialog_foods.get(0);  //取第一筆資料
        DatabaseForm test_database_money22 = test_database_money2.get(0);  //取第一筆資料

        Log.e("TEST_MONEY2 : ", "budget : " + test_database_money22.budget);
        Log.e("TEST_MONEY2 : ", "cost : " + test_database_money22.cost);
        Log.e("TEST_MONEY2 : ", "balance : " + test_database_money22.balance);
        //DATABASE_MONEY，讀取資料

        //taking care of the list view
            listView_homeFood = root.findViewById(R.id.listView);
            adapter_homeFood = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items_homeFood);//adapter for handling the database
            listView_homeFood.setAdapter(adapter_homeFood);

        FloatingActionButton edit_home_food = root.findViewById(R.id.edit_home_food);
        FloatingActionButton add_home_food = root.findViewById(R.id.add_home_food);

        edit_home_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog dialog_home = new AlertDialog.Builder(getActivity()).create();
                dialog_home.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_home.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog_home.show();

                View toast_home_change = View.inflate(getActivity(),R.layout.dialog_home_change,null);
                if (toast_home_change.getParent()!=null) {
                    ((ViewGroup)toast_home_change.getParent()).removeView(toast_home_change);
                }

                ed_name_home = toast_home_change.findViewById(R.id.ed_name_home);
                ed_price_home = toast_home_change.findViewById(R.id.ed_price_home);
                change_home = toast_home_change.findViewById(R.id.change_home);
                /*
                        if(ed_name_home.length()<1|| ed_price_home.length() < 1)

                            Toast.makeText(getActivity(), "欄位請勿留空",Toast.LENGTH_SHORT).show();
                        else{
                            try{
                                shopping_list1. = ed_name_home.getText().toString();//use the database functions to update addDatabaseShoppingList()
                                shopping_list1.food_price = ed_price_home.getText().toString();
                                items.add("名字: "+ shopping_list1.food_name+"   價格: "+ shopping_list1.food_price+ "元");
                                Toast.makeText(getActivity(),"更新"+ed_name_home.getText().toString()+"      價格"+ed_price_home.getText().toString(),Toast.LENGTH_SHORT).show();

                                ed_name_home.setText("");
                                ed_price_home.setText("");
                                adapter_homeFood.notifyDataSetChanged();
                            }catch (Exception e){
                                Toast.makeText(getActivity(),"更新失敗:"+e.toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    */
            }
        });







        return root;

        }
}