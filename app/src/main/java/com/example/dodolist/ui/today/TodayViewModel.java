package com.example.dodolist.ui.today;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dodolist.App;
import com.example.dodolist.model.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

public class TodayViewModel extends ViewModel {

    //private LiveData<List<Note>> noteLiveData = App.getInstance().getNoteDao().getAllLiveData();


    /*public TodayViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }*/

    public LiveData<List<Note>> getNodeLiveData() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        LiveData<List<Note>> noteLiveData = App.getInstance().getNoteDao().getLiveData(calendar.getTimeInMillis());
        //LiveData<List<Note>> noteLiveData = App.getInstance().getNoteDao().getNotDoneLiveData(false);
        return noteLiveData;
    }
}