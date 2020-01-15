//思考要加怎麼加"說明文字"

package com.example.foodemerge.ui.instructions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodemerge.R;

public class InstructionFragment extends Fragment {

    private InstructionViewModel instructionViewModel;
    private TextView instructions_for_user;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        instructionViewModel =
                ViewModelProviders.of(this).get(InstructionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_instructions, container, false);
        final TextView textView = root.findViewById(R.id.text_instructions);
        instructionViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        instructions_for_user = root.findViewById(R.id.instructions_for_user);

        String instructions_for_user2 = "1.Shopping List\n" +
                "(1)記錄要買的食品\n" +
                "(2)點擊項目後跳出一個視窗輸入價錢和有效期限，輸入完之後該項目上會畫一條線，表示已經放進待結帳區了，再點一次該項目，線會消失，回到代買清單中\n" +
                "(3)按一下”推車”的按鈕，可將這些被畫線的項目連同輸入的金額以及有效期限移動到” Home Food”，並在購買清單中刪除\n" +
                "(4)動態顯示這個月剩下的金額扣掉被畫線的之後還剩多少錢\n" +
                "\n" +
                "2.Home Food\n" +
                "(1)依有效期限排列出家中所有的食物\n" +
                "(2)點擊項目顯示該食物的詳細資料(包括:有效期限、熱量、蛋白質、脂質、碳水化合物等含量)\n" +
                "(3)加入一些非購買的食物(因為畢竟並無是每個食物都是自己花錢買的嘛)\n" +
                "\n" +
                "3.FoodInformation\n" +
                "(1)建立一些常吃的食物，所含的熱量等等的營養成分，點擊項目可顯示該資訊\n" +
                "(2)搜尋建立過的食物清單\n" +
                "(3)營養素比例分布\n" +
                "\n" +
                "4.Setting\n" +
                "(1)預算控制\n" +
                "設定每個月可以花的金額，控制不當的花費";

        instructions_for_user.setText(instructions_for_user2);


        return root;
    }
}