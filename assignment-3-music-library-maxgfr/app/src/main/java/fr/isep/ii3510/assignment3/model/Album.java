package fr.isep.ii3510.assignment3.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Album implements Parcelable {

    private ArrayList<Song> songs;
    private String name;

    public Album(String name) {
        this.name = name;
        songs = new ArrayList<>();
    }

    public void addSong (Song a) {
        songs.add(a);
    }

    public ArrayList<Song> getSongs () {
        return this.songs;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Album)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Album c = (Album) o;

        return c.getName().equals(this.name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(songs);
        dest.writeString(name);

    }

    protected Album(Parcel in) {
        songs = in.createTypedArrayList(Song.CREATOR);
        name = in.readString();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

}
