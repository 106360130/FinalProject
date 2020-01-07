package com.example.foodemerge.ui.shopping_list;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodemerge.Database.DatabaseForm;
import com.example.foodemerge.Database.DatabaseFunction;
import com.example.foodemerge.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {

    private ShoppingListViewModel shoppingListViewModel;

    private ListView listView;
    private ArrayList<String> items = new ArrayList<String>();
    private EditText ed_name, ed_price;
    private Button btn_create, btn_delete, btn_change, cancel_delete, delete;
    private TextView textSelected, budget_num, cost_num, balance_num;
    private int cost_record = 0, balance_record = 0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }

    @UiThread
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

        final View trans_list = inflater.inflate(R.layout.trans_list, container, false);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.trans_list,items);
        //taking care of the list view
        listView = root.findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);

        //adapter for changing the text color
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, items){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                final TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25.0F);

                // Generate ListView Item using TextView
                return view;
            }
        };

        // DataBind ListView with items from ArrayAdapter
        listView.setAdapter(colorAdapter);

        ArrayList<DatabaseForm> shopping_list_now = DatabaseFunction.getInstance().getDatabaseShoppingList();  //取得剛剛儲存的資料
        Log.e("shopping_list_now : " , String.format("%d" , shopping_list_now.size()));  //看現在有幾筆資料


        for (int i = 0; i < shopping_list_now.size(); i++) {
            DatabaseForm shopping_list_now2 = shopping_list_now.get(i);  //取每一筆資料

            if (shopping_list_now2.food_price != null) {
                items.add("名字: " + shopping_list_now2.food_name + "   價格: " + shopping_list_now2.food_price);
            } else {
                items.add("名字: " + shopping_list_now2.food_name );
            }

        }


        FloatingActionButton add_shopping_item = root.findViewById(R.id.add_shopping_item);
        add_shopping_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();

                View toast1 = View.inflate(getActivity(),R.layout.newfood_toast,null);
                if (toast1.getParent()!=null) {
                    ((ViewGroup)toast1.getParent()).removeView(toast1);
                }


                dialog.setContentView(toast1);

                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

                ed_name = toast1.findViewById(R.id.ed_name);
                ed_price = toast1.findViewById(R.id.ed_price);
                btn_create = toast1.findViewById(R.id.create);
                btn_delete = toast1.findViewById(R.id.btn_delete);
                btn_change = toast1.findViewById(R.id.change);
                budget_num = root.findViewById(R.id.budget_num);
                cost_num = root.findViewById(R.id.cost_num);
                balance_num = root.findViewById(R.id.balance_num);




                final DatabaseForm shopping_list1 = new DatabaseForm();
                btn_create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ed_name.length() < 1 /*|| ed_price.length() < 1*/)
                            Toast.makeText(getActivity(), "欄位請勿留空", Toast.LENGTH_SHORT).show();
                        else {
                            try {
                                //增加食物的功能

                                if (ed_price.length()>0){

                                    shopping_list1.food_name = ed_name.getText().toString();
                                    shopping_list1.food_price = ed_price.getText().toString();
                                    //add
                                    DatabaseFunction.getInstance().addDatabaseShoppingList(shopping_list1);
                                    //save
                                    DatabaseFunction.getInstance().saveDatabaseShoppingList();

                                    Log.e("新增東西了嗎","food name : " + shopping_list1.food_name);
                                    items.add("名字: "+ shopping_list1.food_name+"   價格: "+ shopping_list1.food_price+ "元");
                                    cost_record = cost_record + Integer.parseInt(ed_price.getText().toString());//加食物價格時,cost_record增加
                                    balance_record = Integer.parseInt(budget_num.getText().toString()) - cost_record;
                                    cost_num.setText(Integer.toString(cost_record));
                                    balance_num.setText(Integer.toString(balance_record));



                                }else{
                                    shopping_list1.food_name = ed_name.getText().toString();
                                    items.add("名字: "+ shopping_list1.food_name);
                                }

                                arrayAdapter.notifyDataSetChanged();


                                Toast.makeText(getActivity(), "新增食物" + ed_name.getText().toString(), /*+ "      價格" + ed_price.getText().toString(),*/ Toast.LENGTH_SHORT).show();

                                ed_name.setText("");
                                ed_price.setText("");
                                dialog.dismiss();
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "新增失敗" + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    
                });

                //修改資料，還沒寫完，變成是增加的功能，有Bug需要維修
                btn_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ed_name.length()<1|| ed_price.length() < 1)

                            Toast.makeText(getActivity(), "欄位請勿留空",Toast.LENGTH_SHORT).show();
                        else{
                            try{
                                ArrayList<DatabaseForm> get_shopping_list1 = DatabaseFunction.getInstance().getDatabaseShoppingList();

                                shopping_list1.food_name = ed_name.getText().toString();//use the database functions to update addDatabaseShoppingList()
                                shopping_list1.food_price = ed_price.getText().toString();
                                items.add("名字: "+ shopping_list1.food_name+"   價格: "+ shopping_list1.food_price+ "元");
                                Toast.makeText(getActivity(),"更新"+ed_name.getText().toString()+"      價格"+ed_price.getText().toString(),Toast.LENGTH_SHORT).show();
                                cost_record = cost_record + Integer.parseInt(ed_price.getText().toString());//加食物價格時,cost_record增加
                                balance_record = Integer.parseInt(budget_num.getText().toString()) - cost_record;
                                cost_num.setText(Integer.toString(cost_record));
                                balance_num.setText(Integer.toString(balance_record));


                                ed_name.setText("");
                                ed_price.setText("");
                                arrayAdapter.notifyDataSetChanged();
                            }catch (Exception e){
                                Toast.makeText(getActivity(),"更新失敗:"+e.toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                //修改資料，還沒寫完，變成是增加的功能，有Bug需要維修

                //刪除資料，還沒寫完
                /*btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       //先看要刪掉甚麼

                        if(要刪掉的東西.edit_price != null){
                                cost_record = cost_record - Integer.parseInt(ed_price.getText().toString());//減掉要刪掉的東西的價格
                                balance_record = Integer.parseInt(budget_num.getText().toString()) - cost_record;
                                cost_num.setText(Integer.toString(cost_record));
                                balance_num.setText(Integer.toString(balance_record));
                        }

                        //刪掉東西

                    }
                });*/
                //刪除資料，還沒寫完

            }
        });
        
        return root;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}