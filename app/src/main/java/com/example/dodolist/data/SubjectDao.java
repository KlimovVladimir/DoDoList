package com.example.dodolist.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dodolist.model.Subject;

import java.util.List;

@Dao
public interface SubjectDao {
    @Query("SELECT * FROM Subject")
    List<Subject> getAll();

    @Query("SELECT * FROM Subject")
    LiveData<List<Subject>> getAllLiveData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Subject subject);

    @Update
    void update(Subject subject);

    @Delete
    void delete(Subject subject);
}
