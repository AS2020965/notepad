package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class AddNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        EditText titleInput = findViewById(R.id.titleinput);
        EditText descriptionInput = findViewById(R.id.description);
        MaterialButton saveBtn = findViewById(R.id.savebtn);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                long createTime = System.currentTimeMillis();

                realm.beginTransaction();
                Note note = realm.createObject(Note.class);
                note.setTitle(title);
                note.setDescription(description);
                note.setCreatedTime(createTime);
                realm.commitTransaction();
                Toast.makeText(getApplicationContext(),"Note Saved",Toast.LENGTH_SHORT).show();
                finish();


            }
        });

    }
}