/*
 * Author: Harlan De Jong
 * Student Number: 3349828
 * Date: 22/05/20
 */
public class Album
{
    // instance variables 
    private String AlbumName;
    private String allSong, songName, genreSongs = "";
    private Song song1, song2, song3, song4;
    private int totalTime, SongSize, check = 0, genreCheck = 0, checker = 0, duration = 0, sum = 0, Chck=0, duplicateCheck=0;
    private final int MAX_TIME = 720; //12 minutes

    /**
     * Constructor for objects of class Album
     */
    public Album()
    {
        song1 = new Song();
        song2 = new Song();
        song3 = new Song();
        song4 = new Song();
        AlbumName = "";
        SongSize = 0;
    }

    public void createAlbum (String name)
    {
        setName(name);
    }

    public void setName (String inputName)
    {
        AlbumName = inputName;
    }

    public String getName()
    {
        return AlbumName;
    }

    public void addSong(String newsongName, String newartist, int newduration, String newgenre)
    {
        if (song1.getDuration() == 0)
            setSong(song1, newsongName, newartist, newduration, newgenre);
        else if (song2.getDuration() == 0)
            setSong(song2, newsongName, newartist, newduration, newgenre);
        else if (song3.getDuration() == 0)
            setSong(song3, newsongName, newartist, newduration, newgenre);
        else if (song4.getDuration() == 0)
            setSong(song4, newsongName, newartist, newduration, newgenre);
        else
            getSongError();
    }

    public void setSong(Song s, String songName, String newartist, int newduration, String newgenre)
    {
        s.setName(songName);
        s.setArtist(newartist);
        s.setDuration(newduration);
        s.setGenre(newgenre);
    }

    public String getSongError()
    {
        return "Maximum number of songs reached in album (4)";
    }
    
    public int duplicateSongCheck(int x, String sName, String aName, int sDuration, String sGenre)
    {
        duplicateCheck = 0;
        if(x == 1){
            if(song1.getName().equalsIgnoreCase(sName) && song1.getArtist().equalsIgnoreCase(aName) && song1.getDuration() == sDuration && song1.getGenre().equalsIgnoreCase(sGenre))
                duplicateCheck = 1;
        }
        else if(x == 2){
            if(song2.getName().equalsIgnoreCase(sName) && song2.getArtist().equalsIgnoreCase(aName) && song2.getDuration() == sDuration && song2.getGenre().equalsIgnoreCase(sGenre))
                duplicateCheck = 1;    
        }
        else if(x == 3){
            if(song3.getName().equalsIgnoreCase(sName) && song3.getArtist().equalsIgnoreCase(aName) && song3.getDuration() == sDuration && song3.getGenre().equalsIgnoreCase(sGenre))
                duplicateCheck = 1;
        }
        else if(x == 4){
            if(song4.getName().equalsIgnoreCase(sName) && song4.getArtist().equalsIgnoreCase(aName) && song4.getDuration() == sDuration && song4.getGenre().equalsIgnoreCase(sGenre))
                duplicateCheck = 1;
        }
        return duplicateCheck;
    }

    public String getSongAlbum(int songNum)
    {
        if (songNum == 1)
            allSong = song1.getAllSong();        
        else if (songNum == 2)
            allSong = song2.getAllSong(); 
        else if (songNum == 3)
            allSong = song3.getAllSong(); 
        else if (songNum == 4)
            allSong = song4.getAllSong(); 
        else
            getError();
        return allSong;
    }

    public String getError()
    {
        return "Invalid Input";
    }

    public int getDuration(int x)
    {
        if(x == 1)
            duration = song1.getDuration();
        else if(x == 2)
            duration = song2.getDuration();
        else if(x == 3)
            duration = song3.getDuration();
        else if(x == 4)
            duration = song4.getDuration();
        return duration;  
    }

    public int getDuration(Song s)
    {
        return s.getDuration();
    }

    public String getSongName(int songNum)
    {
        if (songNum == 1)
            songName = song1.getName();        
        else if (songNum == 2)
            songName = song2.getName(); 
        else if (songNum == 3)
            songName = song3.getName(); 
        else if (songNum == 4)
            songName = song4.getName(); 

        return songName;
    }

    public String getSongUnderDuration(int songTime){
        String songsUnder = "";
        if(song1.getDuration() <= songTime && song1.getDuration() > 0)
             songsUnder = songsUnder+returnUnderDuration(song1)+"\n";
        if(song2.getDuration() <= songTime && song2.getDuration() > 0)
            songsUnder = songsUnder+returnUnderDuration(song2)+"\n";
        if(song3.getDuration() <= songTime && song3.getDuration() > 0)
            songsUnder = songsUnder+returnUnderDuration(song3)+"\n";
        if(song4.getDuration() <= songTime && song4.getDuration() > 0)
            songsUnder = songsUnder+returnUnderDuration(song2)+"\n";
        return songsUnder;
    }

    public String returnUnderDuration(Song s)
    {
        return "•"+s.getName()+" ("+AlbumName+")";
    }

    public int getAllSongDuration(){
        sum = 0;
        sum = song1.getDuration()+song2.getDuration()+song3.getDuration()+song4.getDuration();
        totalTime = sum;
        sum = MAX_TIME-sum;
        return sum;
    }

    public String allGenreSongs(int genreNum){
        if(genreNum == 1){
            if(song1.getGenre().equalsIgnoreCase("rock") && song1.getDuration() > 0)
                genreSongs = genreSongs + returnGenreSongs(song1);
            if(song2.getGenre().equalsIgnoreCase("rock") && song2.getDuration() > 0)    
                genreSongs = genreSongs + returnGenreSongs(song2);
            if(song3.getGenre().equalsIgnoreCase("rock") && song3.getDuration() > 0)    
                genreSongs = genreSongs + returnGenreSongs(song3);
            if(song4.getGenre().equalsIgnoreCase("rock") && song4.getDuration() > 0)    
                genreSongs = genreSongs + returnGenreSongs(song4);
        }
        else if(genreNum == 2){
            if(song1.getGenre().equalsIgnoreCase("pop") && song1.getDuration() > 0)
                genreSongs = genreSongs + returnGenreSongs(song1);
            if(song2.getGenre().equalsIgnoreCase("pop") && song2.getDuration() > 0)    
                genreSongs = genreSongs + returnGenreSongs(song2);
            if(song3.getGenre().equalsIgnoreCase("pop") && song3.getDuration() > 0)    
                genreSongs = genreSongs + returnGenreSongs(song3);
            if(song4.getGenre().equalsIgnoreCase("pop") && song4.getDuration() > 0)    
                genreSongs = genreSongs + returnGenreSongs(song4);
        }
        else if(genreNum == 3){
            if(song1.getGenre().equalsIgnoreCase("hip-hop") && song1.getDuration() > 0)
                genreSongs = genreSongs + returnGenreSongs(song1);
            if(song2.getGenre().equalsIgnoreCase("hip-hop") && song2.getDuration() > 0)    
                genreSongs = genreSongs + returnGenreSongs(song2);
            if(song3.getGenre().equalsIgnoreCase("hip-hop") && song3.getDuration() > 0)    
                genreSongs = genreSongs + returnGenreSongs(song3);
            if(song4.getGenre().equalsIgnoreCase("hip-hop") && song4.getDuration() > 0)    
                genreSongs = genreSongs + returnGenreSongs(song4);
        }
        else if(genreNum == 4){
            if(song1.getGenre().equalsIgnoreCase("bossa nova") && song1.getDuration() > 0)
                genreSongs = genreSongs + returnGenreSongs(song1);
            if(song2.getGenre().equalsIgnoreCase("bossa nova") && song2.getDuration() > 0)    
                genreSongs = genreSongs + returnGenreSongs(song2);
            if(song3.getGenre().equalsIgnoreCase("bossa nova") && song3.getDuration() > 0)    
                genreSongs = genreSongs + returnGenreSongs(song3);
            if(song4.getGenre().equalsIgnoreCase("bossa nova") && song4.getDuration() > 0)    
                genreSongs = genreSongs + returnGenreSongs(song4);
        }
        return genreSongs;
    }

    public String returnGenreSongs(Song s)
    {
        return "•"+s.getName()+" ("+AlbumName+")\n";
    }

    public int deleteSong(String delSongName){
        Chck = 0;
        if(song1.getName().equalsIgnoreCase(delSongName) && song1.getDuration() > 0){
            setSong(song1, "", "", 0, "");
        }
        else if(song2.getName().equalsIgnoreCase(delSongName) && song2.getDuration() > 0){
            setSong(song2, "", "", 0, "");
        }
        else if(song3.getName().equalsIgnoreCase(delSongName) && song3.getDuration() > 0){
            setSong(song3, "", "", 0, "");
        }
        else if(song4.getName().equalsIgnoreCase(delSongName) && song4.getDuration() > 0){
            setSong(song3, "", "", 0, "");
        }
        else
            Chck = 1;
        return Chck;
    }
}
