package org.example.model;

import java.util.UUID;

public class Audio {
    private String id;
    private String artistName;
    private String trackTitle;
    private String albumTitle;
    private String trackNumber;
    private String year;
    private String numReviews;
    private String numCopiesSold;

    // Constructor
    public Audio(){

    }

    public Audio(String artistName, String trackTitle, String albumTitle, String trackNumber, String year, String numReviews, String numCopiesSold) {
        this.id = UUID.randomUUID().toString();
        this.artistName = artistName;
        this.trackTitle = trackTitle;
        this.albumTitle = albumTitle;
        this.trackNumber = trackNumber;
        this.year = year;
        this.numReviews = numReviews;
        this.numCopiesSold = numCopiesSold;
    }

    public Audio(String id, String artistName, String trackTitle, String albumTitle, String trackNumber, String year, String numReviews, String numCopiesSold) {
        this.id = id;
        this.artistName = artistName;
        this.trackTitle = trackTitle;
        this.albumTitle = albumTitle;
        this.trackNumber = trackNumber;
        this.year = year;
        this.numReviews = numReviews;
        this.numCopiesSold = numCopiesSold;
    }

    // Getter and setter methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTrackTitle() {
        return trackTitle;
    }

    public void setTrackTitle(String trackTitle) {
        this.trackTitle = trackTitle;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(String numReviews) {
        this.numReviews = numReviews;
    }

    public String getNumCopiesSold() {
        return numCopiesSold;
    }

    public void setNumCopiesSold(String numCopiesSold) {
        this.numCopiesSold = numCopiesSold;
    }

    @Override
    public String toString() {
        return "Audio [id=" + id + ", artistName=" + artistName + ", trackTitle=" + trackTitle + ", albumTitle="
                + albumTitle + ", trackNumber=" + trackNumber + ", year=" + year + ", numReviews=" + numReviews
                + ", numCopiesSold=" + numCopiesSold + "]";
    }
}
