package com.example.secretnote;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class NoteEditorActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextNote;
    private DatabaseHelper db;
    private int noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextNote = findViewById(R.id.editTextNote);
        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null) {
            noteId = intent.getIntExtra("noteId", -1);
            if (noteId != -1) {
                Note note = db.getNoteById(noteId);
                if (note != null) {
                    editTextTitle.setText(note.getTitle());
                    editTextNote.setText(note.getContent());
                }
            }
        }

        FloatingActionButton fabSave = findViewById(R.id.fab_save);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString().trim();
                String content = editTextNote.getText().toString().trim();
                if (!title.isEmpty() && !content.isEmpty()) {
                    if (noteId == -1) {
                        Note note = new Note(title, content);
                        db.addNote(note);
                    } else {
                        Note note = new Note();
                        note.setId(noteId);
                        note.setTitle(title);
                        note.setContent(content);
                        db.updateNote(note);
                    }
                    finish();
                }
            }
        });
    }
}

