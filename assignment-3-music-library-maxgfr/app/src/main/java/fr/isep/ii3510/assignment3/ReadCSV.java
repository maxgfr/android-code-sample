package fr.isep.ii3510.assignment3;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import fr.isep.ii3510.assignment3.model.Album;
import fr.isep.ii3510.assignment3.model.Artist;
import fr.isep.ii3510.assignment3.model.Song;

public class ReadCSV {

    private static ReadCSV INSTANCE = null;

    private ReadCSV() {

    }

    public static ReadCSV getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReadCSV();
        }
        return INSTANCE;
    }

    public ArrayList<Artist> readFile(InputStream input) {
        ArrayList<Artist> artists = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(input));
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                //System.out.println(nextLine[0] + nextLine[1] + nextLine[2]);
                Artist artist = new Artist(nextLine[0]);
                Album album = new Album(nextLine[1]);
                Song song = new Song(nextLine[2]);
                if(artists.contains(artist)) {
                    int indexOf = artists.indexOf(artist);
                    ArrayList<Album> albums = artists.get(indexOf).getAlbums();
                    if(!albums.contains(album)) {
                        albums.add(album);
                    }
                    int indexOf2 = albums.indexOf(album);
                    ArrayList<Song> songs = albums.get(indexOf2).getSongs();
                    if(!songs.contains(song)) {
                        songs.add(song);
                    }
                } else {
                    artists.add(artist);
                    ArrayList<Album> albums =  artist.getAlbums();
                    albums.add(album);
                    ArrayList<Song> songs = album.getSongs();
                    songs.add(song);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return artists;
    }

}
