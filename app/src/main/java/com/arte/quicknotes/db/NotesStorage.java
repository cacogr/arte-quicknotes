package com.arte.quicknotes.db;

import com.arte.quicknotes.models.Note;

import java.util.List;

/**
 * Created by Carlos on 04/05/2016.
 */
public interface NotesStorage {

    void createNote(Note note);
    List<Note> getAllNotes();
    void updateNote(Note note);
    void deleteNote(Note note);
    void deleteAllNotes();

}
