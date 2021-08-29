package com.example.dodolist.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dodolist.model.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note")
    List<Note> getAll();

    @Query("SELECT * FROM Note")
    LiveData<List<Note>> getAllLiveData();

    @Query("SELECT * FROM Note WHERE date  = :date")
    LiveData<List<Note>> getLiveData(long date);

    @Query("SELECT * FROM Note WHERE done  = :done")
    LiveData<List<Note>> getNotDoneLiveData(boolean done);

    @Query("SELECT * FROM Note WHERE unicid IN (:userIds)")
    List<Note> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM Note WHERE unicid  = :unicid LIMIT 1")
    Note findById(int unicid);

    @Query("SELECT * FROM Note ORDER BY unicid DESC LIMIT 1")
    Note lastById();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
