<<<<<<< HEAD

//123
=======
//有在Jeff的電腦檢查過了，專案是可以run的

>>>>>>> 8736d1c6112b9963a44aae25a681cb6e98d3fc66
package com.example.foodemerge.ui.food_info;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.foodemerge.R;
import java.util.ArrayList;
import android.database.Cursor;
import com.example.foodemerge.ui.SQL.MyDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.widget.Toast;
import android.content.Intent;

public class FoodInfoFragment extends Fragment {

    private FoodInfoViewModel foodInfoViewModel;
    private FloatingActionButton add;
    private Button btn_asve;
    private EditText foodName,caloriesAmount,proteinAmount,fatAmount;
    private TextView foodName_show,caloriesAmount_show,proteinAmount_show,fatAmount_show;
    private TextView text_food_info;
    private ListView search_list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();

    public SQLiteDatabase dbrw;

    private Context context;

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
        btn_asve=root.findViewById(R.id.btn_save);
        foodName=root.findViewById(R.id.foodName_show);
        caloriesAmount=root.findViewById(R.id.caloriesAmount_show);
        proteinAmount=root.findViewById(R.id.proteinAmount_show);
        fatAmount=root.findViewById(R.id.fatAmount);
        foodName_show=root.findViewById(R.id.foodName_show);
        caloriesAmount_show=root.findViewById(R.id.caloriesAmount_show);
        proteinAmount_show=root.findViewById(R.id.proteinAmount_show);
        fatAmount_show=root.findViewById(R.id.fatAmount_show);


        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        search_list.setAdapter(adapter);

        dbrw = new MyDBHelper(getActivity()).getWritableDatabase();

        text_food_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c;
                if(text_food_info.length()<1)
                    c = dbrw.rawQuery("SELECT * FROM myTable", null);
                else
                    c = dbrw.rawQuery("SELECT * FROM  myTable WHERE book LIKE '"+text_food_info.getText().toString()+"'",null);

                c.moveToFirst();
                items.clear();
                Toast.makeText(context,"共有" + c.getCount() + "筆資料", Toast.LENGTH_SHORT).show();

                for (int i = 0; i<c.getCount();i++){

                    items.add("食物:"+ c.getString(0)
                            +"\t\t\t\tCalories:"+ c.getString(1)
                            +"proteinAmount:"+c.getString(2)
                            +"fatAmount"+c.getString(3));
                    c.moveToNext();
                }//取資料

                adapter.notifyDataSetChanged();

                c.close();

            }//查詢資料庫
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切換到Food_data_toast.layout



            }
        });//新增資料庫,

        btn_asve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food_name = foodName.getText().toString();





            }
        });//儲存資料

        return root;
    }

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
}