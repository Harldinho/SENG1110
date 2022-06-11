/*
 * Author: Harlan De Jong
 * Student Number: 3349828
 * Date: 12/06/20
 */
public class Song
{
    private String name = "";
    private String artist = "";
    private int duration = 0;
    private String genre = "";
    private String allSong;
    
    public void Song() //song object
    {
        name = "";
        artist = "";
        duration = 0;
        genre = "";
    }
    
    public void setName(String inputName) //sets name of song
    {
        name = inputName;
    }

    public String getName() //returns name of song
    {
        return name;
    }

    public void setArtist(String inputArtist) //sets the artists name
    {
        artist = inputArtist;
    }

    public String getArtist() //returns the artists name
    {
        return artist;
    }

    public void setDuration(int inputDuration) //sets the duration
    {
        duration = inputDuration;
    }

    public int getDuration() //returns the duration
    {
        return duration;
    }

    public void setGenre(String inputGenre) //set the genre
    {
        genre = inputGenre;
    }

    public String getGenre() //returns the genre
    {
        return genre;    
    }

    public String getAllSong() //returns all the contents of a song
    {
        if(duration>0) //for a song to be returned the duration must be greater than 0
            allSong = "=============================\nName: "+name+"\nArtist: "+artist+"\nDuration: "+duration+" seconds\nGenre: "+genre+"\n=============================";
        return allSong; 
    }

}
