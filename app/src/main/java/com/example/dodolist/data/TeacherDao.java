package com.example.dodolist.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dodolist.model.Teacher;

import java.util.List;

@Dao
public interface TeacherDao {
    @Query("SELECT * FROM Teacher")
    List<Teacher> getAll();

    @Query("SELECT * FROM Teacher")
    LiveData<List<Teacher>> getAllLiveData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Teacher teacher);

    @Update
    void update(Teacher teacher);

    @Delete
    void delete(Teacher teacher);
}
