
package org.example.model;

public class Audio {
    private String artistName;
    private String trackTitle;
    private String albumTitle;
    private int trackNumber;
    private int year;
    private int numReviews;
    private int numCopiesSold;

    // Constructor
    public Audio(){

    }

    public Audio(String artistName, String trackTitle, String albumTitle, int trackNumber, int year, int numReviews, int numCopiesSold) {
        this.artistName = artistName;
        this.trackTitle = trackTitle;
        this.albumTitle = albumTitle;
        this.trackNumber = trackNumber;
        this.year = year;
        this.numReviews = numReviews;
        this.numCopiesSold = numCopiesSold;
    }

    // Getter and setter methods
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

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(int numReviews) {
        this.numReviews = numReviews;
    }

    public int getNumCopiesSold() {
        return numCopiesSold;
    }

    public void setNumCopiesSold(int numCopiesSold) {
        this.numCopiesSold = numCopiesSold;
    }
}