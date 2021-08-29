package com.example.dodolist.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dodolist.model.Note;
import com.example.dodolist.model.Schedule;
import com.example.dodolist.model.Subject;
import com.example.dodolist.model.Teacher;

@Database(entities = {/*Note.class,*/ Schedule.class, Subject.class, Teacher.class}, version = 1)
public abstract class DodoDatabase extends RoomDatabase {
    //public abstract NoteDao noteDao();
    public abstract ScheduleDao scheduleDao();
    public abstract SubjectDao subjectDao();
    public abstract TeacherDao teacherDao();
}
