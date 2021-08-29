package com.example.dodolist.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

@Entity
public class Teacher implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int unicid;

    @ColumnInfo(name = "teacher")
    public String teacher;

    public Teacher() {

    }

    protected Teacher(Parcel in) {
        unicid = in.readInt();
        teacher = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(unicid);
        dest.writeString(teacher);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Teacher> CREATOR = new Creator<Teacher>() {
        @Override
        public Teacher createFromParcel(Parcel in) {
            return new Teacher(in);
        }

        @Override
        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher Teache = (Teacher) o;
        return unicid == Teache.unicid &&
                Objects.equals(teacher, Teache.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unicid, teacher);
    }

}