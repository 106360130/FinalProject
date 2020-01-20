//刪除後listview的沒有更新，是一個bug
//刪除功能，按一下，變灰色的字，且一條線
//按"購物車"時，灰色字的清單消失，轉移到home_food的資料庫(要出清單資訊，要"雙重確認"才送出)
//點清單可以直接編輯食物內容
//"修改"的功能
//list的排版
//空陣列的bug已經解決


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
    private ArrayAdapter<String> arrayAdapter;

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

        //第一個arrayAdapter
        View trans_list = inflater.inflate(R.layout.trans_list, container, false);
        arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.trans_list,items);  //不能用"final"
        //taking care of the list view
        listView = root.findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        //第一個arrayAdapter


        ArrayList<DatabaseForm> shopping_list_now = DatabaseFunction.getInstance().getDatabaseShoppingList();  //取得剛剛儲存的資料


        if(shopping_list_now == null)  //一開始為空陣列，所以要先有判斷式
        {
            Log.e("shopping_list_now : " , "0");  //看現在有幾筆資料
        }
        else
        {
            Log.e("shopping_list_now : " , String.format("%d" , shopping_list_now.size()));  //看現在有幾筆資料

            for (int i = 0; i < shopping_list_now.size(); i++) {
                DatabaseForm shopping_list_now2 = shopping_list_now.get(i);  //取每一筆資料

                if (shopping_list_now2.food_price != null) {
                    items.add("名字: " + shopping_list_now2.food_name + "   價格: " + shopping_list_now2.food_price);
                } else {
                    items.add("名字: " + shopping_list_now2.food_name );
                }

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


                btn_create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseForm shopping_list_create = new DatabaseForm();  //宣告儲存的陣列

                        if (ed_name.length() < 1 /*|| ed_price.length() < 1*/) {
                            Toast.makeText(getActivity(), "欄位請勿留空", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                //增加食物的功能

                                if (ed_price.length()>0){  //有"食物名稱" 和 "食物價格"

                                    shopping_list_create.food_name = ed_name.getText().toString();
                                    shopping_list_create.food_price = ed_price.getText().toString();

                                    DatabaseFunction.getInstance().addDatabaseShoppingList(shopping_list_create);  //add
                                    DatabaseFunction.getInstance().saveDatabaseShoppingList();  //save

                                    Log.e("shopping_list_create : ","food name : " + shopping_list_create.food_name);

                                    //listView顯示
                                    items.add("名字: "+ shopping_list_create.food_name+"   價格: "+ shopping_list_create.food_price+ "元");

                                    //預算的部分計算
                                    cost_record = cost_record + Integer.parseInt(ed_price.getText().toString());//加食物價格時,cost_record增加
                                    balance_record = Integer.parseInt(budget_num.getText().toString()) - cost_record;
                                    cost_num.setText(Integer.toString(cost_record));
                                    balance_num.setText(Integer.toString(balance_record));
                                    //預算的部分計算


                                } else {  //只有"食物名稱"
                                    shopping_list_create.food_name = ed_name.getText().toString();
                                    items.add("名字: " + shopping_list_create.food_name);
                                }

                                Toast.makeText(getActivity(), "新增食物" + ed_name.getText().toString(), /*+ "      價格" + ed_price.getText().toString(),*/ Toast.LENGTH_SHORT).show();

                                //ed_name.setText("");
                                //ed_price.setText("");
                                dialog.dismiss();  //退出"dialog"
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "新增失敗" + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }

                        arrayAdapter.notifyDataSetChanged();  //表示"陣列有改變"，要存儲在"arrayAdapter"裡


                        //最後顯示增加有沒有成功
                        ArrayList<DatabaseForm> shopping_list_changed = DatabaseFunction.getInstance().getDatabaseShoppingList();  //取得剛剛儲存的資料
                        Log.e("SHOPPING_LIST_NOW : " , String.format("%d" , shopping_list_changed.size()));  //看現在有幾筆資料
                        //最後顯示增加有沒有成功
                    }


                });


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


                //刪除資料，還沒寫完
                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       //先看要刪掉甚麼

                        if (ed_name.length() < 1 ) {
                            Toast.makeText(getActivity(), "欄位請勿留空", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                DatabaseFunction.getInstance().removeDatabaseShoppingList(ed_name.getText().toString());
                                DatabaseFunction.getInstance().saveDatabaseShoppingList();

                                Toast.makeText(getActivity(), "刪除食物" + ed_name.getText().toString(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();  //退出"dialog"

                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "刪除失敗" + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }

                        arrayAdapter.notifyDataSetChanged();  //表示"陣列有改變"，要存儲在"arrayAdapter"裡

                        //最後顯示增加有沒有成功
                        ArrayList<DatabaseForm> shopping_list_changed = DatabaseFunction.getInstance().getDatabaseShoppingList();  //取得剛剛儲存的資料
                        Log.e("SHOPPING_LIST_NOW : " , String.format("%d" , shopping_list_changed.size()));  //看現在有幾筆資料
                        //最後顯示增加有沒有成功


                        /*
                        if(要刪掉的東西.edit_price != null){
                                cost_record = cost_record - Integer.parseInt(ed_price.getText().toString());//減掉要刪掉的東西的價格
                                balance_record = Integer.parseInt(budget_num.getText().toString()) - cost_record;
                                cost_num.setText(Integer.toString(cost_record));
                                balance_num.setText(Integer.toString(balance_record));
                        }
                        */

                        //刪掉東西


                    }
                });
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