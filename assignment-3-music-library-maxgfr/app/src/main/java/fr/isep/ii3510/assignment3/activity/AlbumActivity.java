package fr.isep.ii3510.assignment3.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.isep.ii3510.assignment3.R;
import fr.isep.ii3510.assignment3.adapter.AlbumAdapter;
import fr.isep.ii3510.assignment3.model.Album;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    private ArrayList<Album> albums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        initData();
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.my_album_recycler_view);
        System.out.println(recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new AlbumAdapter(albums, this);
        recyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        albums = getIntent().getParcelableArrayListExtra("ALBUMS_SAVE");
        for (int i = 0; i<albums.size(); i++) {
            System.out.println(albums.get(i).getName());
            for (int j = 0; j<albums.get(i).getSongs().size(); j++) {
                System.out.println(albums.get(i).getSongs().get(j).getName());
            }
        }
    }

}
