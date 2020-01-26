package fr.isep.ii3510.assignment3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import fr.isep.ii3510.assignment3.R;
import fr.isep.ii3510.assignment3.model.Song;

public class SongAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<Song> mDataset;
    private Context mContext;


    public SongAdapter(ArrayList<Song> songs, Context context) {
        this.mDataset = songs;
        this.mContext = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_layout, parent, false);
        MyViewHolder SongViewHolder = new MyViewHolder(v);
        return SongViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.textView.setText(mDataset.get(position).getName());
        try {
            Glide.with(mContext)
                    .asBitmap()
                    .load("https://picsum.photos/200")
                    .into(holder.imageView);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
