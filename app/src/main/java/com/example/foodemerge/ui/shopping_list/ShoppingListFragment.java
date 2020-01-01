package com.example.foodemerge.ui.shopping_list;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
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
    //è‹¥è˜­åŠ ç¬¬ä¸€æ¬¡
    private ListView listView;
    private ArrayAdapter<String > adapter;
    private ArrayList<String> items = new ArrayList<>();
    private EditText ed_name, ed_price;
    private Button create, cancel, change, cancel_delete, delete;


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

        /*
        //creating the editText for the different  AlertDialog's
        final EditText ed_food = new EditText(getActivity());//æ–°EDIT TEXTè®“ä½¿ç”¨è€…å¯«æ–°é£Ÿç‰©çš„å��å­—
        ed_food.setInputType(InputType.TYPE_CLASS_TEXT);//è¼¸å…¥æ˜¯TEXT

        final EditText ed_price = new EditText(getActivity());//æ–°EDIT TEXTè®“ä½¿ç”¨è€…å¯«æ–°é£Ÿç‰©çš„åƒ¹æ ¼
        ed_price.setInputType(InputType.TYPE_CLASS_NUMBER);//è¼¸å…¥æ˜¯å€‹æ•¸å­—*/

        // Set an item click listener for ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);
                final AlertDialog delete_dialog = new AlertDialog.Builder(getActivity()).create();
                delete_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                delete_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                delete_dialog.show();

                View toast2 = View.inflate(getActivity(),R.layout.delete_food_toast,null);
                if (toast2.getParent()!=null) {
                    ((ViewGroup)toast2.getParent()).removeView(toast2);
                }

                delete_dialog.setContentView(toast2);

                cancel_delete = delete_dialog.findViewById(R.id.cancel_delete);
                delete = delete_dialog.findViewById(R.id.delete);

                //lo del database
                /*delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });*/

                cancel_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete_dialog.dismiss();
                    }
                });

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
                create = toast1.findViewById(R.id.create);
                cancel = toast1.findViewById(R.id.cancel);
                change = toast1.findViewById(R.id.change);

                final DatabaseForm shopping_list1 = new DatabaseForm();
                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ed_name.length() < 1 /*|| ed_price.length() < 1*/)
                            Toast.makeText(getActivity(), "æ¬„ä½�è«‹å‹¿ç•™ç©º", Toast.LENGTH_SHORT).show();
                        else {
                            try {
                                //å¢žåŠ é£Ÿç‰©çš„åŠŸèƒ½


                                if (ed_price.length()>0){
                                    shopping_list1.food_name = ed_name.getText().toString();
                                    shopping_list1.food_price = ed_price.getText().toString();
                                    items.add("å��å­—: "+ shopping_list1.food_name+"   åƒ¹æ ¼: "+ shopping_list1.food_price+ "å…ƒ");
                                }else{
                                    shopping_list1.food_name = ed_name.getText().toString();
                                    items.add("å��å­—: "+ shopping_list1.food_name);
                                }


                                dialog.dismiss();
                                Toast.makeText(getActivity(), "æ–°å¢žé£Ÿç‰©" + ed_name.getText().toString(), /*+ "      åƒ¹æ ¼" + ed_price.getText().toString(),*/ Toast.LENGTH_SHORT).show();

                                ed_name.setText("");
                                ed_price.setText("");
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "æ–°å¢žå¤±æ•—" + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

               /* change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ed_name.length()<1|| ed_price.length() < 1)

                            Toast.makeText(getActivity(), "æ¬„ä½�è«‹å‹¿ç•™ç©º",Toast.LENGTH_SHORT).show();
                        else{
                            try{
                                //shopping_list1. = ed_name.getText().toString();//use the database functions to update addDatabaseShoppingList()
                                shopping_list1.food_price = ed_price.getText().toString();
                                items.add("å��å­—: "+ shopping_list1.food_name+"   åƒ¹æ ¼: "+ shopping_list1.food_price+ "å…ƒ");
                                Toast.makeText(getActivity(),"æ›´æ–°æ›¸å��"+ed_name.getText().toString()+"      åƒ¹æ ¼"+ed_price.getText().toString(),Toast.LENGTH_SHORT).show();

                                ed_name.setText("");
                                ed_price.setText("");
                            }catch (Exception e){
                                Toast.makeText(getActivity(),"æ›´æ–°å¤±æ•—:"+e.toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });*/

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "å�–æ¶ˆ", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });


            }
        });






        /* refresh button å�¯èƒ½ä¸�æœƒåŠ 
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


        //æ¸¬è©¦SHOPPING_LISTè³‡æ–™åº«æ��å�–è³‡æ–™ï¼Œæ¸¬è©¦æˆ�åŠŸ
        /*ArrayList<DatabaseForm> shopping_list2 = DatabaseFunction.getInstance().getDatabaseShoppingList();  //å�–å¾—å‰›å‰›å„²å­˜çš„è³‡æ–™
        Log.e("dialog_foods ; " , String.format("%d" , shopping_list2.size()));
        //DatabaseForm dailog_food = dialog_foods.get(0);  //å�–ç¬¬ä¸€ç­†è³‡æ–™
        DatabaseForm test_database_shopping_list = shopping_list2.get(shopping_list2.size()-1);  //å�–æœ€å¾Œä¸€ç­†è³‡æ–™

        Log.e("TEST_SHOPPING_LIST2 : ", "food name : " + test_database_shopping_list.food_name);
        Log.e("TEST_SHOPPING_LIST2 : ", "food cals : " + test_database_shopping_list.food_cals);
        Log.e("TEST_SHOPPING_LIST2 : ", "food protein : " + test_database_shopping_list.food_protein);
        Log.e("TEST_SHOPPING_LIST2 : ", "food fat : " + test_database_shopping_list.food_fat);
        Log.e("TEST_SHOPPING_LIST2 : ", "food carbs : " + test_database_shopping_list.food_carbs);*/
        //æ¸¬è©¦SHOPPING_LISTè³‡æ–™åº«æ��å�–è³‡æ–™ï¼Œæ¸¬è©¦æˆ�åŠŸ


        return root;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }




}