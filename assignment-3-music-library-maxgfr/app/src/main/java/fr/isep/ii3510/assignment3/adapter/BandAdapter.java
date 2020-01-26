package fr.isep.ii3510.assignment3.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import fr.isep.ii3510.assignment3.R;
import fr.isep.ii3510.assignment3.activity.AlbumActivity;
import fr.isep.ii3510.assignment3.model.Artist;

public class BandAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<Artist> mDataset;
    private Context mContext;


    public BandAdapter(ArrayList<Artist> artists, Context context) {
        this.mDataset = artists;
        this.mContext = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_layout, parent, false);
        MyViewHolder artistViewHolder = new MyViewHolder(v);
        return artistViewHolder;
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

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AlbumActivity.class);
                intent.putParcelableArrayListExtra("ALBUMS_SAVE", mDataset.get(position).getAlbums());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
