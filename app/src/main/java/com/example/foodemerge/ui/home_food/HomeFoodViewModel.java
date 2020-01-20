package com.example.foodemerge.ui.home_food;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeFoodViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeFoodViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is food fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}