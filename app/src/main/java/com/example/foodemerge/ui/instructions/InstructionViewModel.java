package com.example.foodemerge.ui.instructions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InstructionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InstructionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is instructions fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}