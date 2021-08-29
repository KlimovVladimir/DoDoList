package com.example.dodolist.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;


@Entity
public class Schedule implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int unicid;

    @ColumnInfo(name = "subject")
    public String subject;

    @ColumnInfo(name = "teacher")
    public String teacher;

    @ColumnInfo(name = "room")
    public String room;

    @ColumnInfo(name = "time")
    public long time;

    @ColumnInfo(name = "timeend")
    public long timeend;

    @ColumnInfo(name = "day")
    public int day;

    @ColumnInfo(name = "week")
    public int week;

    @ColumnInfo(name = "color")
    public int color;

    public Schedule() {

    }

    protected Schedule(Parcel in) {
        unicid = in.readInt();
        subject = in.readString();
        teacher = in.readString();
        room = in.readString();
        time = in.readLong();
        timeend = in.readLong();
        day = in.readInt();
        week = in.readInt();
        color = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(unicid);
        dest.writeString(subject);
        dest.writeString(teacher);
        dest.writeString(room);
        dest.writeLong(time);
        dest.writeLong(timeend);
        dest.writeInt(day);
        dest.writeInt(week);
        dest.writeInt(color);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return unicid == schedule.unicid &&
                time == schedule.time &&
                timeend == schedule.timeend &&
                Objects.equals(subject, schedule.subject) &&
                Objects.equals(teacher, schedule.teacher) &&
                Objects.equals(room, schedule.room) &&
                day == schedule.day &&
                week == schedule.week &&
                color == schedule.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unicid, subject, teacher, room, time, timeend, day, week, color);
    }

}