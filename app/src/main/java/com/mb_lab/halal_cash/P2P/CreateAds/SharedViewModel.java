package com.mb_lab.halal_cash.P2P.CreateAds;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<HashMap<String, String>> sharedData = new MutableLiveData<>();
    HashMap<String, String> hashMap;

    SharedViewModel() {
        hashMap = new HashMap<>();
    }

    public void setSharedData(String key, String data) {
        hashMap.put(key, data);
        sharedData.setValue(hashMap);
    }

    public LiveData<HashMap<String, String>> getSharedData() {
        return sharedData;
    }
}
