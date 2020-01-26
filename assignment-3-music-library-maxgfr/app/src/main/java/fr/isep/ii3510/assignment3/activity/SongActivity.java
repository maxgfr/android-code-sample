package fr.isep.ii3510.assignment3.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.isep.ii3510.assignment3.R;
import fr.isep.ii3510.assignment3.adapter.SongAdapter;
import fr.isep.ii3510.assignment3.model.Song;

import java.util.ArrayList;

public class SongActivity extends AppCompatActivity {

    private ArrayList<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        initData();
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.my_song_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new SongAdapter(songs, this);
        recyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        songs = (ArrayList<Song>) getIntent().getSerializableExtra("SONGS_SAVE");
        for (int i = 0; i<songs.size(); i++) {
            System.out.println(songs.get(i).getName());
        }
    }

}
