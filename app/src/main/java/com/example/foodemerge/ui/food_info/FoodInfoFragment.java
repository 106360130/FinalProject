package com.example.foodemerge.ui.food_info;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.foodemerge.R;
import java.util.ArrayList;
import android.database.Cursor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.widget.Toast;
import android.content.Intent;

public class FoodInfoFragment extends Fragment {

    private AlertDialog dialog;
    private FoodInfoViewModel foodInfoViewModel;
    private FloatingActionButton add;
    private Button btn_save;
    private EditText foodName,caloriesAmount,proteinAmount,fatAmount;
    private TextView foodName_show,caloriesAmount_show,proteinAmount_show,fatAmount_show;
    private TextView text_food_info;
    private ListView search_list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();
    private String food_neme, food_cals,food_protein,food_fat,food_carbs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodInfoViewModel =
                ViewModelProviders.of(this).get(FoodInfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_food_info, container, false);
        text_food_info = root.findViewById(R.id.text_food_info);
        foodInfoViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                text_food_info.setText(s);
            }
        });

        add = root.findViewById(R.id.add);
        search_list = root.findViewById(R.id.search_list);


        TextView foodName_show=root.findViewById(R.id.foodName_show);
        TextView caloriesAmount_show=root.findViewById(R.id.caloriesAmount_show);
        TextView proteinAmount_show=root.findViewById(R.id.proteinAmount_show);
        TextView fatAmount_show=root.findViewById(R.id.fatAmount_show);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        search_list.setAdapter(adapter);

        text_food_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }//查詢資料庫
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AlertDialog.Builder(getActivity()).create();//切換到Food_data_toast.layout
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//切換到Food_data_toast.layout
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//切換到Food_data_toast.layout
                dialog.show();//切換到Food_data_toast.layout
                View view = View.inflate(getActivity(), R.layout.food_data_toast, null);//切換到Food_data_toast.layout
                dialog.setContentView(view);//切換到Food_data_toast.layout
                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);//讓鍵盤可以點一下就跳出來
                btn_save = view.findViewById(R.id.btn_save);//因為是alertDialog的view，所以要在這裡加，又因為是fragment的findViewById，所以前面要加自己分別的view
                foodName=view.findViewById(R.id.foodName);
                caloriesAmount=view.findViewById(R.id.caloriesAmount);
                proteinAmount=view.findViewById(R.id.proteinAmount);
                fatAmount=view.findViewById(R.id.fatAmount);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        food_neme=foodName.getText().toString();
                        food_cals=caloriesAmount.getText().toString();
                        food_protein=proteinAmount.getText().toString();
                        food_fat=fatAmount.getText().toString();//把輸入的資料存到database裡
                        //Log.e("INPUT :",food_neme);
                        //Log.e("INPUT :",food_cals);
                        //Log.e("INPUT :",food_protein);
                        //Log.e("INPUT :",food_fat);//除錯用，確認是否真的有存東西進去
                        Toast.makeText(getActivity(),"食物:"+food_neme+"  ,cals:"+food_cals+
                              "  ,protein"+food_protein+"  ,fat"+food_fat+"加入成功",Toast.LENGTH_SHORT).show();
                    }
                });//儲存資料
            }
        });//新增資料庫,




        return root;
    }

/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        if (requestCode == 1) {
            if (requestCode == 101) {
                Bundle b = data.getExtras();
                String foodData = b.getString("food");
                String caloriesAmount = b.getString("calories");
                String proteinAmount = b.getString("protein");
                String fatAmount = b.getString("fat");
//跳到純顯示的畫面
            }
        }
    }
*/

}