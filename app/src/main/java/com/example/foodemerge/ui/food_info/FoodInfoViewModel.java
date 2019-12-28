package com.example.foodemerge.ui.food_info;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FoodInfoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FoodInfoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is food info fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}