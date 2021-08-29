package com.example.dodolist.ui.addsubj;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.dodolist.App;
import com.example.dodolist.model.Subject;

import java.util.Calendar;
import java.util.List;

public class SubjectViewModel extends ViewModel {

    public LiveData<List<Subject>> getNodeLiveData() {
        LiveData<List<Subject>> subjectLiveData = App.getInstance().getSubjectDao().getAllLiveData();

        return subjectLiveData;
    }
}
