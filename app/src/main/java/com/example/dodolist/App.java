package com.example.dodolist;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationManager;

import androidx.room.Room;

import com.example.dodolist.data.DodoDatabase;
import com.example.dodolist.data.NoteDao;
import com.example.dodolist.data.NoteDatabase;
import com.example.dodolist.data.ScheduleDao;
import com.example.dodolist.data.SubjectDao;
import com.example.dodolist.data.TeacherDao;

import com.example.dodolist.model.Schedule;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class App extends Application {

    private DodoDatabase database;
    private NoteDatabase databaseNote;
    private NoteDao noteDao;

    private ScheduleDao scheduleDao;

    private SubjectDao subjectDao;

    private TeacherDao teacherDao;

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        database = Room.databaseBuilder(getApplicationContext(), DodoDatabase.class,"DodoDB")
                .allowMainThreadQueries()
                .build();

        databaseNote = Room.databaseBuilder(getApplicationContext(), NoteDatabase.class,"DodoDBNote")
                .allowMainThreadQueries()
                .build();

        noteDao = databaseNote.noteDao();

        scheduleDao = database.scheduleDao();

        subjectDao = database.subjectDao();

        teacherDao = database.teacherDao();
    }

    public DodoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(DodoDatabase database) {
        this.database = database;
    }

    public NoteDao getNoteDao() {
        return noteDao;
    }

    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    public ScheduleDao getScheduleDao() {
        return scheduleDao;
    }

    public void setScheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    public SubjectDao getSubjectDao() { return subjectDao; }

    public TeacherDao getTeacherDao() {
        return teacherDao;
    }
}
