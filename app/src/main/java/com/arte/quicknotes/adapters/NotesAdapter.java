package com.arte.quicknotes.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arte.quicknotes.R;
import com.arte.quicknotes.models.Note;

import java.util.List;

/**
 * Created by arte on 27/04/2016.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    public interface Events {
        void onNoteClicked(Note note);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView noteTitle;
        public TextView noteExcerpt;

        public ViewHolder(View itemView) {
            super(itemView);

            noteTitle = (TextView) itemView.findViewById(R.id.note_title);
            noteExcerpt = (TextView) itemView.findViewById(R.id.note_excerpt);
        }
    }

    private List<Note> mNoteList;
    private Events mEvents;

    public NotesAdapter(List<Note> notes, Events events) {
        mNoteList = notes;
        mEvents = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Note note = mNoteList.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.noteExcerpt.setText(note.getExcerpt());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEvents.onNoteClicked(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }
}
