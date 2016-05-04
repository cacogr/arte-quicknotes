package com.arte.quicknotes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.arte.quicknotes.models.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 04/05/2016.
 */
public class NotesDataSource implements NotesStorage {

    private NotesDbHelper dbHelper;
    private static NotesDataSource mInstance;
    private static List<Note> mNoteList = new ArrayList<>();

    public NotesDataSource(Context context) {
        dbHelper = new NotesDbHelper(context);
    }

    public static NotesDataSource getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NotesDataSource(context);
        }
        return mInstance;
    }

    public void createNote(Note note) {
        ContentValues values = getContentValues(note.getTitle(), note.getContent());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(NotesDbHelper.NoteEntry.TABLE_NAME, null, values);

        getAllNotes();
    }

    public void updateNote(Note note) {
        ContentValues values = getContentValues(note.getTitle(), note.getContent());
        String whereClause = NotesDbHelper.NoteEntry._ID + " = ?";
        String[] args = {String.valueOf(note.getId())};

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update(NotesDbHelper.NoteEntry.TABLE_NAME, values, whereClause, args);

        getAllNotes();
    }

    public void deleteNote(Note note) {
        long deleteId = note.getId();
        String[] args = {String.valueOf(deleteId)};

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(NotesDbHelper.NoteEntry.TABLE_NAME,
                NotesDbHelper.NoteEntry._ID + " = ?",
                args);

        getAllNotes();
    }

    public void deleteAllNotes() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(NotesDbHelper.NoteEntry.TABLE_NAME, null, null);
        mNoteList.clear();
    }

    public List<Note> getAllNotes() {
        Cursor cursor = getAll();
        mNoteList.clear();
        while (cursor.moveToNext()) {
            Note note = toNote(cursor);
            mNoteList.add(note);
        }
        return mNoteList;
    }

    private Cursor getAll() {
        String[] projection = {
                NotesDbHelper.NoteEntry._ID,
                NotesDbHelper.NoteEntry.COLUMN_NAME_TITLE,
                NotesDbHelper.NoteEntry.COLUMN_NAME_CONTENT
        };
        String sortOrder =
                NotesDbHelper.NoteEntry._ID + " DESC";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(
                NotesDbHelper.NoteEntry.TABLE_NAME,     // The table to query
                projection,                             // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                sortOrder                               // The sort order
        );
    }

    private Note toNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(NotesDbHelper.NoteEntry._ID)));
        note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(NotesDbHelper.NoteEntry.COLUMN_NAME_TITLE)));
        note.setContent(cursor.getString(cursor.getColumnIndexOrThrow(NotesDbHelper.NoteEntry.COLUMN_NAME_CONTENT)));
        return note;
    }

    private ContentValues getContentValues(String title, String content) {
        ContentValues values = new ContentValues();
        values.put(NotesDbHelper.NoteEntry.COLUMN_NAME_TITLE, title);
        values.put(NotesDbHelper.NoteEntry.COLUMN_NAME_CONTENT, content);
        return values;
    }

}
