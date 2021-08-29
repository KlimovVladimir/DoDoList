package com.example.dodolist.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dodolist.model.Note;
import com.example.dodolist.model.Schedule;
import com.example.dodolist.model.Subject;
import com.example.dodolist.model.Teacher;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();

}
