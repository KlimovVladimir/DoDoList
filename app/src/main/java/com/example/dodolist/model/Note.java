package com.example.dodolist.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;


@Entity
public class Note implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int unicid;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "subject")
    public String subject;

    @ColumnInfo(name = "time")
    public long time;

    @ColumnInfo(name = "date")
    public long date;

    @ColumnInfo(name = "spinnerSelection")
    public long dateNotif;

    @ColumnInfo(name = "done")
    public boolean done;

    @ColumnInfo(name = "color")
    public int color;

    public Note() {

    }

    protected Note(Parcel in) {
        unicid = in.readInt();
        text = in.readString();
        subject = in.readString();
        time = in.readLong();
        date = in.readLong();
        dateNotif = in.readLong();
        done = in.readByte() != 0;
        color = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(unicid);
        dest.writeString(text);
        dest.writeString(subject);
        dest.writeLong(time);
        dest.writeLong(date);
        dest.writeLong(dateNotif);
        dest.writeByte((byte) (done ? 1 : 0));
        dest.writeInt(color);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return unicid == note.unicid &&
                time == note.time &&
                date == note.date &&
                dateNotif == note.dateNotif &&
                done == note.done &&
                Objects.equals(text, note.text) &&
                Objects.equals(subject, note.subject) &&
                color == note.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unicid, text, subject, time, date, dateNotif, done, color);
    }

}
