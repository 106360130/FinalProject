package com.example.foodemerge.ui.food_info;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodemerge.Database.DatabaseForm;
import com.example.foodemerge.Database.DatabaseFunction;
import com.example.foodemerge.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FoodInfoFragment extends Fragment {

    private FoodInfoViewModel foodInfoViewModel;
    private Button search_on_net;  //網路上搜尋的按鈕
    String url;  //爬蟲的網址
    String want_search_food = "beef";
    private ListView food_info_listView;  //listView顯示要用
    private ArrayList<String> items = new ArrayList<String>();  //listView顯示要用

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foodInfoViewModel =
                ViewModelProviders.of(this).get(FoodInfoViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_food_info, container, false);
        final TextView textView = root.findViewById(R.id.text_food_info);
        foodInfoViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //listView顯示要用
        final View trans_list = inflater.inflate(R.layout.trans_list, container, false);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.trans_list,items);
        food_info_listView = root.findViewById(R.id.serch_list);
        food_info_listView.setAdapter(arrayAdapter);


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
        //listView顯示要用

        food_info_listView.setAdapter(colorAdapter);
        //taking care of the list view

        /*
        //資料庫提取資料，要顯示在listView
        ArrayList<DatabaseForm> dialog_foods = DatabaseFunction.getInstance().getDatabase();  //取得剛剛儲存的資料
        Log.e("dialog_foods ; " , String.format("%d" , dialog_foods.size()));

        //DatabaseForm dailog_food = dialog_foods.get(0);  //取第一筆資料
        DatabaseForm test_database2 = dialog_foods.get(dialog_foods.size()-1);  //取最後一筆資料

        Log.e("TEST_DATEBASE2 : ", "food name : " + test_database2.food_name);
        Log.e("TEST_DATEBASE2 : ", "food cals : " + test_database2.food_cals);
        Log.e("TEST_DATEBASE2 : ", "food protein : " + test_database2.food_protein);
        Log.e("TEST_DATEBASE2 : ", "food fat : " + test_database2.food_fat);
        Log.e("TEST_DATEBASE2 : ", "food carbs : " + test_database2.food_carbs);
        //資料庫提取資料，要顯示在listView
        */

        //listView顯示要用
        ArrayList<DatabaseForm> food_info_now = DatabaseFunction.getInstance().getDatabase();  //取得剛剛儲存的資料
        Log.e("food_info_now : " , String.format("%d" , food_info_now.size()));  //看現在有幾筆資料

        for (int i = 0; i < food_info_now.size(); i++) {
            DatabaseForm food_info_now2 = food_info_now.get(i);  //取每一筆資料

            if (food_info_now2.food_name != null) {
                items.add("名字 : " + food_info_now2.food_name  );
            }
        }
        //listView顯示要用



        /*
        //刪除指定名字的字串
        ArrayList<DatabaseForm> look_food = DatabaseFunction.getInstance().getDatabase();
        Log.e("LOOK_FOOD : ", String.format("%d", look_food.size()));
        String remove_food = "beef";
        DatabaseFunction.getInstance().removeDatabase(remove_food);
        //look_food.remove(0);
        //DatabaseFunction.getInstance().setDatabase(look_food);
        DatabaseFunction.getInstance().saveDatabase();
        Log.e("NOW_FOOD : ", String.format("%d", look_food.size()));
        //刪除指定名字的字串
        */


        search_on_net = root.findViewById(R.id.btn_search);
        search_on_net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("WANT_SEARCH_FOOD : ", want_search_food );

                url = String.format("https://api.myfitnesspal.com/public/nutrition?q=%s&page=1&per_page=1",want_search_food);
                Log.e("NET : ", url);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL ur = new URL(url);
                            HttpURLConnection key = (HttpURLConnection)ur.openConnection();
                            String searched_info = convertStreamToString(key.getInputStream(), "utf-8");
                            Log.e("HEY!", "here!");
                            JSONObject first = null;

                            Log.e("SEARCHED_DATA : ", searched_info);

                            try {
                                first = new JSONObject(searched_info);

                                JSONArray firstArray = first.getJSONArray("items");
                                Log.e("FIRST_ARRAY : ", "" + firstArray.length());  //只能顯示字串

                                JSONObject secondObject = firstArray.getJSONObject(0);
                                Log.e("SECOND_OBJECT : ", "second" + secondObject);

                                JSONObject thirdObject = secondObject.getJSONObject("item");

                                String description = thirdObject.getString("description");  //取得物件中的字串
                                JSONObject nutritional_contents = thirdObject.getJSONObject("nutritional_contents");  //取得物件中的物件，幹!這很重要，重要到爆炸
                                JSONObject energy = nutritional_contents.getJSONObject("energy");
                                String cals = energy.getString("value");  //取得熱量
                                String protein = nutritional_contents.getString("protein");
                                String fat = nutritional_contents.getString("fat");
                                String carbs = nutritional_contents.getString("carbohydrates");


                                //顯示到logcat裡
                                Log.e("FOOD_INFO : ", "brand_name : " + description);
                                Log.e("FOOD_INFO : ", "Energy : " + cals);
                                Log.e("FOOD_INFO : ", "Protein : " + protein);
                                Log.e("FOOD_INFO : ", "Fat : " + fat);
                                Log.e("FOOD_INFO : ", "Carbs : " + carbs);
                                //顯示到logcat裡




                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("ERROR : ", e.getMessage());

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("ERROR : ", e.getMessage());
                        }

                    }

                });
                thread.start();

            }
        });

        return root;
    }

    private static String convertStreamToString(InputStream is, String charsetName) throws IOException {
        InputStreamReader isr;
        StringBuilder buffer = new StringBuilder();
        isr = new InputStreamReader(is, charsetName);
        Reader in = new BufferedReader(isr);
        int ch;
        while ((ch = in.read()) != -1) {
            buffer.append((char) ch);
        }
        isr.close();
        is.close();
        return buffer.toString();
    }

}