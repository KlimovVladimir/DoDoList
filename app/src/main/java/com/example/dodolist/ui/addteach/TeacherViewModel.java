package com.example.dodolist.ui.addteach;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.dodolist.App;
import com.example.dodolist.model.Subject;
import com.example.dodolist.model.Teacher;

import java.util.List;

public class TeacherViewModel extends ViewModel {

    public LiveData<List<Teacher>> getNodeLiveData() {
        LiveData<List<Teacher>> teacherLiveData = App.getInstance().getTeacherDao().getAllLiveData();

        return teacherLiveData;
    }
}

