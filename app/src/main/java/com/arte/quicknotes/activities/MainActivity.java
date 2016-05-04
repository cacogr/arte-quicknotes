package com.arte.quicknotes.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.arte.quicknotes.R;
import com.arte.quicknotes.adapters.NotesAdapter;
import com.arte.quicknotes.db.NotesDataSource;
import com.arte.quicknotes.models.Note;

public class MainActivity extends AppCompatActivity implements NotesAdapter.Events {

    private NotesAdapter mAdapter;
    private NotesDataSource mNotesDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActivity();
    }

    private void setupActivity() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        final Context context = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, NoteActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.notes_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mNotesDataSource = NotesDataSource.getInstance(this);
        mAdapter = new NotesAdapter(mNotesDataSource.getAllNotes(), this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoteClicked(Note note) {
        Intent intent = new Intent(this, NoteActivity.class);
        Bundle arguments = new Bundle();
        arguments.putSerializable(NoteActivity.PARAM_NOTE, note);
        intent.putExtras(arguments);

        startActivityForResult(intent, 0);
    }
}
