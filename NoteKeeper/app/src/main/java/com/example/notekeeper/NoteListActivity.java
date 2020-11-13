package com.example.notekeeper;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    private NoteRecyclerAdapter mNoteRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NoteListActivity.this, NoteActivity.class));
            }
        });

        initializeDisplayContent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Anytime our NoteListActivity is resumed, the data set is refreshed
        mNoteRecyclerAdapter.notifyDataSetChanged();
    }

    private void initializeDisplayContent() {
        //Get reference to recycler view that was loaded by our layout resource
        final RecyclerView recyclerNotes = findViewById(R.id.list_notes);
        //Instance of layout manager
        final LinearLayoutManager notesLayoutManager = new LinearLayoutManager(this);
        //Associated the layout manager to the recycler view
        recyclerNotes.setLayoutManager(notesLayoutManager);

        //Get notes to be displayed within the RecyclerView
        //Create instance of DataManager
        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        //Create instance of NoteRecyclerAdapter
        mNoteRecyclerAdapter = new NoteRecyclerAdapter(this, notes);
        //Associate adapter with the RecyclerView
        recyclerNotes.setAdapter(mNoteRecyclerAdapter);
    }
}