/*
 * Author: Harlan De Jong
 * Student Number: 3349828
 * Date: 22/05/20
 */
public class Song
{
    private String name = "";
    private String artist = "";
    private int duration = 0;
    private String genre = "";
    private String allSong;
    
    public void setName(String inputName)
    {
        name = inputName;
    }

    public String getName()
    {
        return name;
    }

    public void setArtist(String inputArtist)
    {
        artist = inputArtist;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setDuration(int inputDuration)
    {
        duration = inputDuration;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setGenre(String inputGenre)
    {
        genre = inputGenre;
    }

    public String getGenre()
    {
        return genre;    
    }

    public String getAllSong()
    {
        if(duration>0)
            allSong = "=============================\nName: "+name+"\nArtist: "+artist+"\nDuration: "+duration+" seconds\nGenre: "+genre+"\n=============================";
        return allSong; 
    }

}