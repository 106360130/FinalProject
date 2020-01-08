package com.example.foodemerge.ui.food_info;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodemerge.Database.DatabaseForm;
import com.example.foodemerge.Database.DatabaseFunction;
import com.example.foodemerge.MainActivity;
import com.example.foodemerge.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

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

    private AlertDialog dialog;
    private FoodInfoViewModel foodInfoViewModel;

    private Button search_on_net;  //網路上搜尋的按鈕
    String url;  //爬蟲的網址
    String want_search_food = "beef";
    private ListView food_info_listView;  //listView顯示要用
    private ArrayList<String> items = new ArrayList<String>();  //listView顯示要用
    private Integer want_num;
    private PieChart show_food_info_chart;
    private TextView dialog_food_name, dialog_food_cals;
    private TextView dialog_food_protein, dialog_food_fat, dialog_food_carbs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        foodInfoViewModel =
                ViewModelProviders.of(this).get(FoodInfoViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_food_info, container, false);
        final TextView textView = root.findViewById(R.id.text_food_info);
        

        //listView顯示要用
        final View trans_list = inflater.inflate(R.layout.trans_list, container, false);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.trans_list,items);
        food_info_listView = root.findViewById(R.id.serch_list);
        food_info_listView.setAdapter(arrayAdapter);  //將自定義的layout塞進Dialog




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

        food_info_listView.setAdapter(colorAdapter);  //字體顏色、大小設定
        //listView顯示要用

        //listView顯示要用
        ArrayList<DatabaseForm> food_info_now = DatabaseFunction.getInstance().getDatabase();  //取得剛剛儲存的資料
        Log.e("food_info_now : " , String.format("%d" , food_info_now.size()));  //看現在有幾筆資料


            for (int i = 0; i < food_info_now.size(); i++) {
                DatabaseForm food_info_now2 = food_info_now.get(i);  //取每一筆資料

                if (food_info_now2.food_name != null) {
                    items.add(food_info_now2.food_name  );
                }
            }

        //listView顯示要用

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



        food_info_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(getActivity(), arrayAdapter.getItem(position).toString(), Toast.LENGTH_LONG);
                //顯示Toast
                toast.show();

                //DATABASE_FOOD_INFO，讀取資料
                ArrayList<DatabaseForm> food_info_have = DatabaseFunction.getInstance().getDatabase();  //取得剛剛儲存的資料
                Log.e("food_info_have : " , String.format("%d" , food_info_have.size()));

                String search_food_info = arrayAdapter.getItem(position);

                for( int i = 0 ; i < food_info_have.size() ; i++)
                {
                    DatabaseForm food_info_have2 = food_info_have.get(i);

                    if( food_info_have2.food_name.compareTo(search_food_info) == 0)  //如果字串一樣就刪除
                    {
                        want_num = i;
                        break;
                    }
                }

                //DATABASE_FOOD_INFO，讀取資料
                DatabaseForm food_info_have2 = food_info_have.get(want_num);

                Log.e("TEST_DATEBASE2 : ", "food name : " + food_info_have2.food_name);
                Log.e("TEST_DATEBASE2 : ", "food cals : " + food_info_have2.food_cals);
                Log.e("TEST_DATEBASE2 : ", "food protein : " + food_info_have2.food_protein);
                Log.e("TEST_DATEBASE2 : ", "food fat : " + food_info_have2.food_fat);
                Log.e("TEST_DATEBASE2 : ", "food carbs : " + food_info_have2.food_carbs);
                //DATABASE_FOOD_INFO，讀取資料


                final AlertDialog builder = new AlertDialog.Builder(getActivity()).create();  //宣告
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                builder.show();

                View view2 = View.inflate(getActivity(),R.layout.show_food_info, null);
                if(view2.getParent() != null){
                    ((ViewGroup)view2.getParent()).removeView(view2);
                }

                builder.setContentView(view2);
                builder.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);


                //piechart的相關設定
                show_food_info_chart = view2.findViewById(R.id.food_pie_chart2);
                show_food_info_chart.setUsePercentValues(true);
                show_food_info_chart.getDescription().setEnabled(false);
                show_food_info_chart.setExtraOffsets(0,0,0,0);
                show_food_info_chart.setDrawHoleEnabled(true);
                show_food_info_chart.setHoleRadius(80);
                show_food_info_chart.setDrawEntryLabels(false);

                dialog_food_name = view2.findViewById(R.id.tv_food_name2);
                dialog_food_cals = view2.findViewById(R.id.tv_cals2);
                dialog_food_protein = view2.findViewById(R.id.tv_protein2);
                dialog_food_fat = view2.findViewById(R.id.tv_fat2);
                dialog_food_carbs = view2.findViewById(R.id.tv_carbs2);


                dialog_food_name.setText(String.format("%s", food_info_have2.food_name ));
                dialog_food_cals.setText(String.format("%s", food_info_have2.food_cals ));
                dialog_food_protein.setText(String.format("%s", food_info_have2.food_protein ));
                dialog_food_fat.setText(String.format("%s", food_info_have2.food_fat ));
                dialog_food_carbs.setText(String.format("%s", food_info_have2.food_carbs ));


                Log.e("LOOK_FOOD : ", food_info_have2.food_name);
                Log.e("LOOK_FOOD : ", food_info_have2.food_cals);
                Log.e("LOOK_FOOD : ", food_info_have2.food_protein);
                Log.e("LOOK_FOOD : ", food_info_have2.food_fat);
                Log.e("LOOK_FOOD : ", food_info_have2.food_carbs);


                //顯示要用"float"
                Float num_protein = Float.parseFloat(food_info_have2.food_protein);
                Float num_fat = Float.parseFloat(food_info_have2.food_fat);
                Float num_carbs = Float.parseFloat(food_info_have2.food_carbs);
                //顯示要用"float"

                ArrayList<PieEntry> food_info_num2 = new ArrayList<>();  //曲線的種類
                food_info_num2.add(new PieEntry( num_protein,"Protein"));  //"f"只是強制為"浮點數"而已
                food_info_num2.add(new PieEntry(num_fat, "Carbs"));
                food_info_num2.add(new PieEntry(num_carbs,"Fat"));

                show_food_info_chart.setCenterText(String.format("%s cal",food_info_have2.food_cals));
                show_food_info_chart.setCenterTextColor(Color.BLACK);
                show_food_info_chart.setCenterTextSize(20);
                show_food_info_chart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

                PieDataSet show_food_setting1 = new PieDataSet(food_info_num2, "food_data");
                show_food_setting1.setSliceSpace(3f);
                show_food_setting1.setSelectionShift(0f);
                ArrayList pie_chart_colors2 = new ArrayList();
                pie_chart_colors2.add(Color.rgb(46, 139, 87));
                pie_chart_colors2.add(Color.rgb(238, 44, 44));
                pie_chart_colors2.add(Color.rgb(24, 116, 205));
                show_food_setting1.setColors(pie_chart_colors2);

                PieData show_food_setting2 = new PieData(show_food_setting1);
                show_food_setting2.setValueTextSize(10f);
                show_food_setting2.setValueTextColor(Color.BLACK);
                show_food_setting2.setDrawValues(false);

                show_food_info_chart.setData(show_food_setting2);

                Legend show_food_info_legend = show_food_info_chart.getLegend();
                show_food_info_legend.setEnabled(false);
                //piechart的相關設定


                builder.show();  //顯示"AlertDialog"

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