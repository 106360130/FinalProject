package com.example.foodemerge.ui.food;

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

    //本來就有的
    private FoodViewModel foodViewModel;
    private ArrayAdapter<String> adapter_homefood;
    private ListView listView_homeFood;
    private ArrayList<String> items_homeFood = new ArrayList<>();
    private EditText ed_name, ed_price;
    private Button btn_create, btn_delete, btn_change, cancel_delete, delete;


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

        //listView顯示要用
        final View trans_list = inflater.inflate(R.layout.trans_list, container, false);
        adapter_homefood = new ArrayAdapter<>(getActivity(),R.layout.trans_list,items_homeFood);
        listView_homeFood = root.findViewById(R.id.home_food_list_view);
        listView_homeFood.setAdapter(adapter_homefood);

        FloatingActionButton add_food = root.findViewById(R.id.add_home_food);
        add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                final DatabaseForm shopping_list_homeFood = new DatabaseForm();
                btn_create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ed_name.length() < 1 /*|| ed_price.length() < 1*/)
                            Toast.makeText(getActivity(), "欄位請勿留空", Toast.LENGTH_SHORT).show();
                        else {
                            try {
                                //增加食物的功能


                                if (ed_price.length() > 0) {

                                    shopping_list_homeFood.food_name = ed_name.getText().toString();
                                    shopping_list_homeFood.food_price = ed_price.getText().toString();
                                    Log.e("新增東西了嗎", "food name : " + shopping_list_homeFood.food_name);
                                    items_homeFood.add("名字: " + shopping_list_homeFood.food_name + "   價格: " + shopping_list_homeFood.food_price + "元");
                                    DatabaseFunction.getInstance().addDatabaseHomeFood(shopping_list_homeFood);
                                    DatabaseFunction.getInstance().saveDatabaseHomeFood();

                                } else {
                                    shopping_list_homeFood.food_name = ed_name.getText().toString();
                                    items_homeFood.add("名字: " + shopping_list_homeFood.food_name);
                                }

                                Toast.makeText(getActivity(), "新增食物" + ed_name.getText().toString(), /*+ "      價格" + ed_price.getText().toString(),*/ Toast.LENGTH_SHORT).show();


                                dialog.dismiss();
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "新增失敗" + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }

                        adapter_homefood.notifyDataSetChanged();

                    }
                });

            }
        });

        return root;

    }
}