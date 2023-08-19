package com.example.notepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import org.bson.json.JsonWriter;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

//import io.realm.RealmObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        MaterialButton addNoteBtn = findViewById(R.id.addnewnotebtn);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,AddNote.class));






            }


        });

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Note> noteList = realm.where(Note.class).findAll();


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager (new LinearLayoutManager (this));
        MyAdapter myAdapter = new MyAdapter(getApplicationContext(),noteList);
        recyclerView.setAdapter(myAdapter);

           noteList.addChangeListener ( new RealmChangeListener<RealmResults<Note>> () {

            @Override
            public void onChange(RealmResults<Note> notes) {

                myAdapter.notifyDataSetChanged ();
            }
        } );

    }


}