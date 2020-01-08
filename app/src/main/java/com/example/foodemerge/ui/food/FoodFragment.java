package com.example.foodemerge.ui.food;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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

    private ListView home_food_listView;
    private ArrayList<String> items = new ArrayList<>();

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


        /*
        //DATABASE_HOME_FOOD，讀取資料
        ArrayList<DatabaseForm> test_database_money2 = DatabaseFunction.getInstance().getDatabaseMoney();  //取得剛剛儲存的資料
        Log.e("TEST_MONEY2 : " , "data : " + String.format("%d" , test_database_money2.size()));

        //DatabaseForm dailog_food = dialog_foods.get(0);  //取第一筆資料
        DatabaseForm test_database_money22 = test_database_money2.get(0);  //取第一筆資料

        Log.e("TEST_MONEY2 : ", "budget : " + test_database_money22.budget);
        Log.e("TEST_MONEY2 : ", "cost : " + test_database_money22.cost);
        Log.e("TEST_MONEY2 : ", "balance : " + test_database_money22.balance);
        //DATABASE_MONEY，讀取資料
        */

            //listView顯示要用
            final View trans_list = inflater.inflate(R.layout.trans_list, container, false);
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.trans_list,items);
            home_food_listView = root.findViewById(R.id.home_food_list_view);
            home_food_listView.setAdapter(arrayAdapter);


            ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, items){
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    // Get the Item from ListView
                    View view = super.getView(position, convertView, parent);

                    // Initialize a TextView for ListView each Item
                    final TextView tv = (TextView) view.findViewById(android.R.id.text1);

                    // Set the text color of TextView (ListView Item)
                    tv.setTextColor(Color.WHITE);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.0F);

                    // Generate ListView Item using TextView
                    return view;
                }
            };
            home_food_listView.setAdapter(colorAdapter);
            //listView顯示要用

            //listView顯示要用
            ArrayList<DatabaseForm> home_food_now = DatabaseFunction.getInstance().getDatabaseHomeFood();  //取得剛剛儲存的資料
            Log.e("home_food_now : " , String.format("%d" , home_food_now.size()));  //看現在有幾筆資料

            for (int i = 0; i < home_food_now.size(); i++) {
                DatabaseForm home_food_now2 = home_food_now.get(i);  //取每一筆資料

                if (home_food_now2.food_name != null) {
                    items.add("名字 : " + home_food_now2.food_name  + "    有效期限 : " + home_food_now2.food_EXP);
                }
            }
            //listView顯示要用


            //taking care of the list view
            //home_food_listView = root.findViewById(R.id.listView);
            //arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);//adapter for handling the database
           // home_food_listView.setAdapter(arrayAdapter);

            FloatingActionButton edit_home_food = root.findViewById(R.id.edit_home_food);
            FloatingActionButton add_home_food = root.findViewById(R.id.add_home_food);

            edit_home_food.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog dialog_home = new AlertDialog.Builder(getActivity()).create();
                    dialog_home.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog_home.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog_home.show();

                    View toast_home_change = View.inflate(getActivity(), R.layout.dialog_home_change, null);
                    if (toast_home_change.getParent() != null) {
                        ((ViewGroup) toast_home_change.getParent()).removeView(toast_home_change);
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