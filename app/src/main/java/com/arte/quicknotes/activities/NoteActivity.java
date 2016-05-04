package com.arte.quicknotes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.arte.quicknotes.R;
import com.arte.quicknotes.db.NotesDataSource;
import com.arte.quicknotes.models.Note;

public class NoteActivity extends AppCompatActivity {

    public static final String PARAM_NOTE = "param_note";

    private EditText mTitle;
    private EditText mContent;
    private Note mNote;

    private NotesDataSource mNotesDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        setupActivity();
        loadNote();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);

        if (mNote == null) {
            MenuItem delete = menu.findItem(R.id.action_delete);
            delete.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveNote();
                return true;
            case R.id.action_delete:
                deleteNote();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadNote() {
        Note note = (Note) getIntent().getSerializableExtra(PARAM_NOTE);
        if (note == null) {
            return;
        }

        mNote = note;
        mTitle.setText(note.getTitle());
        mContent.setText(note.getContent());
    }

    private void saveNote() {
        String title = mTitle.getText().toString();
        String content = mContent.getText().toString();

        if (mNote == null) {
            Note note = new Note();
            note.setTitle(title);
            note.setContent(content);
            mNotesDataSource.createNote(note);
        } else {
            mNote.setTitle(title);
            mNote.setContent(content);
            mNotesDataSource.updateNote(mNote);
        }
        finish();
    }

    private void deleteNote() {
        if (mNote != null) {
            mNotesDataSource.deleteNote(mNote);
        }
        finish();
    }

    private void setupActivity() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNotesDataSource = NotesDataSource.getInstance(this);

        mTitle = (EditText) findViewById(R.id.et_title);
        mContent = (EditText) findViewById(R.id.et_content);
    }
}
