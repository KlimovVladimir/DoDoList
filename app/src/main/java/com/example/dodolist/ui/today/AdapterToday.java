package com.example.dodolist.ui.today;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.dodolist.App;
import com.example.dodolist.R;
import com.example.dodolist.model.Note;
import com.example.dodolist.ui.AddNote;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class AdapterToday extends RecyclerView.Adapter<AdapterToday.NoteViewHolder> {

    private SortedList<Note> sortedList;


    public AdapterToday() {
        sortedList = new SortedList<>(Note.class, new SortedList.Callback<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                if (!o2.done && o1.done) {
                    return 1;
                }
                if (o2.done && !o1.done) {
                    return -1;
                }
                return (int) (o1.date - o2.date);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position,count);
            }

            @Override
            public boolean areContentsTheSame(Note oldItem, Note newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Note item1, Note item2) {
                return item1.unicid == item2.unicid;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position,count);

            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position,count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //neponyapto
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list,parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public int getItemDone() {
        int count = 0;
        Note note;
        for (int i = 0; i < sortedList.size(); i++) {
            note = sortedList.get(i);
            if (note.done == true)
                count++;
        }
        return count;
    }

    public void setItems(List<Note> notes){
        sortedList.replaceAll(notes);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView noteText;
        TextView noteTimeText;
        TextView noteDateText;
        TextView noteSubjectText;
        CheckBox comleted;
        View delete, change, colorbar;
        Note note;

        boolean silentUpdate;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            noteText = itemView.findViewById(R.id.note_text_item);
            //noteTimeText = itemView.findViewById(R.id.note_text_time_item);
            noteDateText = itemView.findViewById(R.id.note_text_date_item);
            noteSubjectText = itemView.findViewById(R.id.note_text_subject_item);
            comleted = itemView.findViewById(R.id.note_comleted);
            delete = itemView.findViewById(R.id.note_delete);
            //change = itemView.findViewById(R.id.note_change);
            colorbar = itemView.findViewById(R.id.colorbar);

            //current = itemView.findViewById(R.id.current_date);

            /*change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddNote.start((Activity) itemView.getContext(), note);
                }
            });*/
            noteText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddNote.start((Activity) itemView.getContext(), note);
                }
            });
            noteDateText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddNote.start((Activity) itemView.getContext(), note);
                }
            });
            noteSubjectText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddNote.start((Activity) itemView.getContext(), note);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    App.getInstance().getNoteDao().delete(note);
                }
            });

            comleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean checked) {
                    if(!silentUpdate) {
                        note.done = checked;
                        /*if (checked)
                            TodayFragment.inc_progress();
                        else
                            TodayFragment.dec_progress();*/
                        App.getInstance().getNoteDao().update(note);
                    }
                    updateStrokeOut();
                }
            });
        }

        public void bind(Note note) {
            this.note = note;
            noteText.setText(note.text);
            noteSubjectText.setText(note.subject);
            colorbar.setBackgroundColor(note.color);

            long milliSeconds= note.time;
            String TimeText= getDate(milliSeconds,"HH:mm" );
           // noteTimeText.setText(TimeText);
            milliSeconds= note.date;
            TimeText= getDate(milliSeconds,"dd.MM.yyyy" );
            noteDateText.setText(TimeText);
            //noteTimeText.setText(Long.toString(milliSeconds));
            updateStrokeOut();

            silentUpdate = true;
            comleted.setChecked(note.done);
            silentUpdate = false;
        }
        public static String getDate(long milliSeconds, String dateFormat)
        {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            return formatter.format(calendar.getTime());
        }
        private void updateStrokeOut() {
            if (note.done) {
                noteText.setPaintFlags(noteText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else {
                noteText.setPaintFlags(noteText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}
