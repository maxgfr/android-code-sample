package fr.isep.ii3510.assignment3.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {

    private String name;

    public Song(String name) {
        this.name = name;
    }

    protected Song(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

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
        if (!(o instanceof Song)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Song c = (Song) o;

        return c.getName().equals(this.name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }
}
