package com.example.dodolist.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dodolist.model.Schedule;

import java.util.List;

@Dao
public interface ScheduleDao {
    @Query("SELECT * FROM Schedule")
    List<Schedule> getAll();

    @Query("SELECT * FROM Schedule")
    LiveData<List<Schedule>> getAllLiveData();

    @Query("SELECT * FROM Schedule WHERE (day  = :day AND week = :week)")
    LiveData<List<Schedule>> getLiveData(int day, int week);

    @Query("SELECT * FROM Schedule WHERE unicid IN (:userIds)")
    List<Schedule> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM Schedule WHERE unicid  = :unicid LIMIT 1")
    Schedule findById(int unicid);

    @Query("SELECT * FROM Schedule ORDER BY unicid DESC LIMIT 1")
    Schedule lastById();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Schedule schedule);

    @Update
    void update(Schedule schedule);

    @Delete
    void delete(Schedule schedule);
}
