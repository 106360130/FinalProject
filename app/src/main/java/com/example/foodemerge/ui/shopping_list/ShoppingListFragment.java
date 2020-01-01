package com.example.foodemerge.ui.shopping_list;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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
import com.example.foodemerge.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.BLACK;

public class ShoppingListFragment extends Fragment {

    private ShoppingListViewModel shoppingListViewModel;
    //若蘭加第一次
    private ListView listView;
    private ArrayAdapter<String > adapter;
    private ArrayList<String> items = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shoppingListViewModel =
                ViewModelProviders.of(this).get(ShoppingListViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        final TextView textView = root.findViewById(R.id.text_shopping_list);
        shoppingListViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //taking care of the list view
        listView = root.findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);//adapter for handling the database
        listView.setAdapter(adapter);

        //creating the editText for the different  AlertDialog's
        final EditText ed_food = new EditText(getActivity());//新EDIT TEXT讓使用者寫新食物的名字
        ed_food.setInputType(InputType.TYPE_CLASS_TEXT);//輸入是TEXT
        final EditText ed_price = new EditText(getActivity());//新EDIT TEXT讓使用者寫新食物的價格
        ed_price.setInputType(InputType.TYPE_CLASS_NUMBER);//輸入是個數字*/

        //adapter for changing the text color
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, items){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30.0F);

                // Generate ListView Item using TextView
                return view;
            }
        };

        // DataBind ListView with items from ArrayAdapter
        listView.setAdapter(colorAdapter);

        FloatingActionButton add_shopping_item = root.findViewById(R.id.add_shopping_item);
        add_shopping_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("新增食物");

                //Creating a linear layout for the dialog
                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(ed_food);
                layout.addView(ed_price);

                dialog.setView(layout);

                //新增的button.新增之後,listview會直接更新

                dialog.setPositiveButton("新增", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //btn_insert的東西 //price不一定要填
                        //en el list 點食物時,也會有個alertdialog para escribir el price
                        if (ed_food.length() < 1 /*|| ed_price.length() < 1*/)
                            Toast.makeText(getActivity(), "欄位請勿留空", Toast.LENGTH_SHORT).show();
                        else {
                            try {
                                //增加食物的功能
                                DatabaseForm shopping_list1 = new DatabaseForm();

                                shopping_list1.food_name = ed_food.getText().toString();  //少了"getTextView"

                                Log.e("FOOD_NAME : ", shopping_list1.food_name);
                                items.add("名字: "+ shopping_list1.food_name);
                                Toast.makeText(getActivity(), "新增食物" + ed_food.getText().toString(), /*+ "      價格" + ed_price.getText().toString(),*/ Toast.LENGTH_SHORT).show();

                                ed_food.setText("");
                                /*ed_price.setText("");*/
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "新增失敗" + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }

                        //Toast.makeText(MainActivity.this,"新增食物成功",Toast.LENGTH_LONG).show();

                        //btn_query的東西
                        //quizas el if, else no sea necesario porque lo que quiero es que
                        //la lista se actualice automaticamente


                    }
                });

                //取消的button.
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "取消", Toast.LENGTH_LONG).show();
                    }
                });


                dialog.show();
            }
        });



        /* refresh button 可能不會加
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


        //測試SHOPPING_LIST資料庫提取資料，測試成功
        /*ArrayList<DatabaseForm> shopping_list2 = DatabaseFunction.getInstance().getDatabaseShoppingList();  //取得剛剛儲存的資料
        Log.e("dialog_foods ; " , String.format("%d" , shopping_list2.size()));
        //DatabaseForm dailog_food = dialog_foods.get(0);  //取第一筆資料
        DatabaseForm test_database_shopping_list = shopping_list2.get(shopping_list2.size()-1);  //取最後一筆資料

        Log.e("TEST_SHOPPING_LIST2 : ", "food name : " + test_database_shopping_list.food_name);
        Log.e("TEST_SHOPPING_LIST2 : ", "food cals : " + test_database_shopping_list.food_cals);
        Log.e("TEST_SHOPPING_LIST2 : ", "food protein : " + test_database_shopping_list.food_protein);
        Log.e("TEST_SHOPPING_LIST2 : ", "food fat : " + test_database_shopping_list.food_fat);
        Log.e("TEST_SHOPPING_LIST2 : ", "food carbs : " + test_database_shopping_list.food_carbs);*/
        //測試SHOPPING_LIST資料庫提取資料，測試成功


        return root;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}