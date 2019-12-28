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
        return root;
    }
}