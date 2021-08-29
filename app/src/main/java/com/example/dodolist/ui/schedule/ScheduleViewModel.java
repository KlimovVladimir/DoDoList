package com.example.dodolist.ui.schedule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.dodolist.App;
import com.example.dodolist.model.Note;
import com.example.dodolist.model.Schedule;

import java.util.Calendar;
import java.util.List;

public class ScheduleViewModel extends ViewModel {

    public LiveData<List<Schedule>> getNodeLiveData(int day, int week) {
        LiveData<List<Schedule>> noteLiveData = App.getInstance().getScheduleDao().getLiveData(day, week);

        return noteLiveData;
    }
}