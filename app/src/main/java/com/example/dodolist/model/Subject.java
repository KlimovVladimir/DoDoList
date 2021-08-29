package com.example.dodolist.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

@Entity
public class Subject implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int unicid;

    @ColumnInfo(name = "subject")
    public String subject;

    public Subject() {

    }

    protected Subject(Parcel in) {
        unicid = in.readInt();
        subject = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(unicid);
        dest.writeString(subject);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject Subjec = (Subject) o;
        return unicid == Subjec.unicid &&
                Objects.equals(subject, Subjec.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unicid, subject);
    }

}