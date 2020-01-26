package fr.isep.ii3510.assignment3.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.isep.ii3510.assignment3.adapter.BandAdapter;
import fr.isep.ii3510.assignment3.R;
import fr.isep.ii3510.assignment3.ReadCSV;
import fr.isep.ii3510.assignment3.model.Artist;

import java.io.InputStream;
import java.util.ArrayList;

public class BandActivity extends AppCompatActivity {

    private ArrayList<Artist> artists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band);

        initData();
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.my_artist_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new BandAdapter(artists, this);
        recyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        ReadCSV reader = ReadCSV.getInstance();
        InputStream ins = getResources().openRawResource(
                getResources().getIdentifier("library",
                        "raw", getPackageName()));
        artists = reader.readFile(ins);
        for(int i=0; i<artists.size(); i++) {
            System.out.println(artists.get(i).toString());
        }
    }


}
