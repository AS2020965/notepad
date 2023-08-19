package com.example.notepad;

import android.content.Context;
import android.icu.text.DateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyView>{

    Context context;
    RealmResults<Note> noteList;

    public MyAdapter(Context context, RealmResults<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }




    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyView(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {

        Note note = noteList.get(position);

        holder.titleOutput.setText(note.getTitle());
        holder.descriptionOutput.setText(note.getDescription());

        String formatedTime = DateFormat.getDateTimeInstance().format(note.createdTime);
        holder.timeOutput.setText(formatedTime);


        holder.itemView.setOnLongClickListener (new View.OnLongClickListener () {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu popupMenu = new PopupMenu (context,v);
                popupMenu.getMenu().add ("Delete");
                popupMenu.setOnMenuItemClickListener (new PopupMenu.OnMenuItemClickListener () {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals ("Delete")){

                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText (context,"Note Delete",Toast.LENGTH_SHORT).show ();

                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });



    }

    @Override
    public int getItemCount() {

        return noteList.size ();
    }

    public class MyView extends RecyclerView.ViewHolder{

       TextView titleOutput;
       TextView descriptionOutput;
       TextView timeOutput;

        public MyView(@NonNull View itemView) {
            super(itemView);

            titleOutput = itemView.findViewById(R.id.titleoutput);
            descriptionOutput = itemView.findViewById(R.id.descriptionoutput);
            timeOutput = itemView.findViewById(R.id.timeoutput);


        }
    }


}
