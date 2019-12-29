package com.example.foodemerge.ui.shopping_list;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import com.example.foodemerge.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {

    private ShoppingListViewModel shoppingListViewModel;
    //若蘭加第一次
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items = new ArrayList<>();
    private SQLiteDatabase dbrw;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shoppingListViewModel =
                ViewModelProviders.of(this).get(ShoppingListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        final TextView textView = root.findViewById(R.id.text_shopping_list);
        shoppingListViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //taking care of the list view
        listView = root.findViewById(R.id.listView);
        //adapter for handling the database
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);


        dbrw = new MyDBHelper(getActivity()).getWritableDatabase();//取得資料庫實體

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


                dialog.setView(ed_food);

                //dialog.setView(ed_price);

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
                                dbrw.execSQL("INSERT INTO myTable(food, price) VALUES(?,?)", new Object[]{ed_food.getText().toString()/*, ed_price.getText().toString()*/});
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
                        Cursor c;
                        if(ed_food.length()<1)
                            c = dbrw.rawQuery("SELECT * FROM myTable", null);
                        else
                            c = dbrw.rawQuery("SELECT * FROM  myTable WHERE book LIKE '"+ed_food.getText().toString()+"'",null);

                        c.moveToFirst();
                        items.clear();
                        Toast.makeText(getActivity(),"共有" + c.getCount() + "筆資料", Toast.LENGTH_SHORT).show();

                        for (int i = 0; i<c.getCount();i++){

                            items.add("食物:"+ c.getString(0)+"\t\t\t\t價格:"+ c.getString(1));
                            c.moveToNext();
                        }

                        adapter.notifyDataSetChanged();

                        c.close();
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
        return root;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        dbrw.close();//資料庫不適用記得關閉
    }
}