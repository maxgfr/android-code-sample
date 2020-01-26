package fr.isep.ii3510.assignment3.model;

import java.util.ArrayList;

public class Artist {

    private ArrayList<Album> albums;
    private String name;

    public Artist(String name) {
        this.name = name;
        albums = new ArrayList<>();
    }

    public void addAlbum (Album a) {
        albums.add(a);
    }

    public ArrayList<Album> getAlbums() {
        return this.albums;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        String res = "--------------------\n";
        res += "\t"+this.name+"\n";
        for(int i=0; i<albums.size(); i++) {
            res += "\t\t"+i+") "+albums.get(i).getName()+"\n";
            for(int j=0; j<albums.get(i).getSongs().size(); j++){
                res += "\t\t\t"+j+") "+albums.get(i).getSongs().get(j).getName()+"\n";
            }
            res +="\n";
        }
        res +="--------------------\n\n";
        return res;
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Artist)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Artist c = (Artist) o;

        return c.getName().equals(this.name);
    }
}
