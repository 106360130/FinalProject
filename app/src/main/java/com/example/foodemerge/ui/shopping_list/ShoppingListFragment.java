package com.example.foodemerge.ui.shopping_list;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.foodemerge.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {

    private ShoppingListViewModel shoppingListViewModel;
    //若蘭加第一次
    private ListView listView;
    private ArrayList<String> items = new ArrayList<String>();
    private EditText ed_name, ed_price;
    private Button btn_create, btn_delete, btn_change, cancel_delete, delete;
    TextView textSelected;

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


        final View trans_list = inflater.inflate(R.layout.trans_list, container, false);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.trans_list,items);
        //taking care of the list view
        listView = root.findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);

        /*
        //creating the editText for the different  AlertDialog's
        final EditText ed_food = new EditText(getActivity());//新EDIT TEXT讓使用者寫新食物的名字
        ed_food.setInputType(InputType.TYPE_CLASS_TEXT);//輸入是TEXT

        final EditText ed_price = new EditText(getActivity());//新EDIT TEXT讓使用者寫新食物的價格
        ed_price.setInputType(InputType.TYPE_CLASS_NUMBER);//輸入是個數字*/

        // Set an item click listener for ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                TextView item = (TextView) view.findViewById(R.id.textView);
                //item.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


                item.setTextColor(Color.rgb(105, 105, 105));



            }
        });


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
                                    Log.e("新增東西了嗎","food name : " + shopping_list1.food_name);
                                    items.add("名字: "+ shopping_list1.food_name+"   價格: "+ shopping_list1.food_price+ "元");

                                }else{
                                    shopping_list1.food_name = ed_name.getText().toString();
                                    items.add("名字: "+ shopping_list1.food_name);
                                }


                                dialog.dismiss();
                                Toast.makeText(getActivity(), "新增食物" + ed_name.getText().toString(), /*+ "      價格" + ed_price.getText().toString(),*/ Toast.LENGTH_SHORT).show();

                                ed_name.setText("");
                                ed_price.setText("");
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "新增失敗" + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

               /* btn_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ed_name.length()<1|| ed_price.length() < 1)

                            Toast.makeText(getActivity(), "欄位請勿留空",Toast.LENGTH_SHORT).show();
                        else{
                            try{
                                //shopping_list1. = ed_name.getText().toString();//use the database functions to update addDatabaseShoppingList()
                                shopping_list1.food_price = ed_price.getText().toString();
                                items.add("名字: "+ shopping_list1.food_name+"   價格: "+ shopping_list1.food_price+ "元");
                                Toast.makeText(getActivity(),"更新書名"+ed_name.getText().toString()+"      價格"+ed_price.getText().toString(),Toast.LENGTH_SHORT).show();

                                ed_name.setText("");
                                ed_price.setText("");
                            }catch (Exception e){
                                Toast.makeText(getActivity(),"更新失敗:"+e.toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });*/

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });


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