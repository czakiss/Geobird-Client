package com.example.geobirdclient.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {
    private MutableLiveData <String> gText;

    public GalleryViewModel(){
        gText = new MutableLiveData<>();
    }
    public LiveData<String> getText(){return gText;}
}
