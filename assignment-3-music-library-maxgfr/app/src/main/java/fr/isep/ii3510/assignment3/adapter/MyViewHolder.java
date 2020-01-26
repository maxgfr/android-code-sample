package fr.isep.ii3510.assignment3.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import fr.isep.ii3510.assignment3.R;

public class MyViewHolder extends RecyclerView.ViewHolder{

    public ImageView imageView;
    public TextView textView;
    public RelativeLayout relativeLayout;

    public MyViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_art);
        textView = itemView.findViewById(R.id.image_name);
        relativeLayout = itemView.findViewById(R.id.parent_layout);
    }
}
