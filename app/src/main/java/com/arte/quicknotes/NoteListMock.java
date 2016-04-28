package com.arte.quicknotes;

import com.arte.quicknotes.models.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arte on 27/04/2016.
 */
public class NoteListMock {

    private final static int INITIAL_NOTE_COUNT = 5;
    private final static String IPSUM = "Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator Lorem ipsum generator";
    private static List<Note> noteList;
    private static int nextId = 0;

    public static List<Note> getList() {
        if (noteList == null) {
            createList();
        }
        return noteList;
    }

    public static void addNote(Note note) {
        note.setId(nextId);
        noteList.add(note);
        nextId += 1;
    }

    private static void createList() {
        noteList = new ArrayList<>();
        for (int i=0; i < INITIAL_NOTE_COUNT; i++) {
            Note note = new Note();
            note.setTitle("Note #" + (i+1));
            note.setContent(IPSUM);
            addNote(note);
        }
    }

    public static void updateNote(Note updatedNote) {
        for (int i = 0; i < noteList.size(); i++) {
            Note note = noteList.get(i);
            if (note.getId() == updatedNote.getId()) {
                note.setTitle(updatedNote.getTitle());
                note.setContent(updatedNote.getContent());
                return;
            }
        }
    }

    public static void deleteNote(Note noteToDelete) {
        for (int i = 0; i < noteList.size(); i++) {
            Note note = noteList.get(i);
            if (note.getId() == noteToDelete.getId()) {
                noteList.remove(i);
                return;
            }
        }
    }
}
