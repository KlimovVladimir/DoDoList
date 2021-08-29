package com.example.dodolist.ui.calendar;

import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.dodolist.App;
import com.example.dodolist.R;
import com.example.dodolist.model.Note;

import java.util.Calendar;
import java.util.List;

public class DateViewModel extends ViewModel {

    //SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
    //Date date = new Date(System.currentTimeMillis());
    // editText.setText(formatter.format(date));

    // private LiveData<List<Note>> noteLiveData = App.getInstance().getNoteDao().getAllLiveData();

    // private LiveData<List<Note>> noteLiveData = App.getInstance().getNoteDao().getLiveData(date.getTime());

    /*public TodayViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }*/

    public LiveData<List<Note>> getNodeLiveData(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        LiveData<List<Note>> noteLiveData = App.getInstance().getNoteDao().getLiveData(date);

        return noteLiveData;
    }
}
