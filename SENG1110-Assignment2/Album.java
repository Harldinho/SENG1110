/*
 * Author: Harlan De Jong
 * Student Number: 3349828
 * Date: 12/06/20
 */
public class Album
{
    // instance variables 
    private String AlbumName;
    private String allSong, songName, genreSongs = "";
    private Song[] songs;
    private int totalTime, SongSize, duration = 0, total, songTotal=0;
    private final int MAX_TIME = 720, MAX_SONGS = 6; //max songs is 5 but index starts at 1 during entire code, thus 6


    public Album() //Album object
    {
        songs = new Song[MAX_SONGS]; //creating an array of songs holding 5 total
        AlbumName = "";
        SongSize = 0;
        total = 0;
    }

    public void createAlbum (String name) //creates album by setting name to something other than ""
    {
        setName(name);
    }

    public void setName (String inputName) //sets new name of album
    {
        AlbumName = inputName;
    }

    public String getName() //returns name of album
    {
        return AlbumName;
    }

    public void addSong(String newsongName, String newartist, int newduration, String newgenre) //adds a song into the song object array accepts all song parameters
    {
        songTotal++; //incrementing global song total
        songs[songTotal] = new Song(); //creating new song object
        songs[songTotal].setName(newsongName); //setting parameters to their respective positions within song
        songs[songTotal].setArtist(newartist);
        songs[songTotal].setDuration(newduration);
        songs[songTotal].setGenre(newgenre);
    }

    public String getSpecificSong(String specName){ //accepts a specific song name and returns a list of all songs under that song name
        String specReturn = "";
        for(int i=1;i<=songTotal;i++){ //iterating through all stored songs
            if(songs[i].getName().equalsIgnoreCase(specName)){ //if a song is equal to the specified song it is added to the list
                specReturn = specReturn + songs[i].getAllSong()+"\n";
            }
        }
        if(specReturn.equals("")) //if there were no songs found print error
            specReturn = "No songs were found under the name "+specName;
        return specReturn;
    }

    public String getSongs(){ //returns all songs from an album with details
        String allSongs = "", substitution;
        String[] nameArray = new String[5]; //creates an array to store song names for alphabetisation
        boolean unsorted = true; //allow loop to run initially
        for(int i=0;i<=songTotal-1;i++){ //assigning all song names to an array for aphabetic comparison
            nameArray[i] = songs[i+1].getName();
        }
        while(unsorted){
            unsorted = false; //while loop will stop if loop is considered sorted
            for(int i=0;i<songTotal-1;i++){ 
                if(nameArray[i].compareToIgnoreCase(nameArray[i+1])>0){ //if the song name in the array has a higher ASCII numerical value than the one next to it
                    substitution = nameArray[i]; //swapping the places of song name to achieve alphabetical order
                    nameArray[i] = nameArray[i+1];
                    nameArray[i+1] = substitution;
                    unsorted = true; //thus unsorted still
                }
            }
        }
        for(int i=0;i<=songTotal-1;i++){ //returns song names in alphabetical order
            allSongs = allSongs + songs[getSongIndex(nameArray[i])].getAllSong()+"\n";
        }

        if(allSongs.equals("")) //if no songs were found in the album return error
            allSongs = "No songs within album: "+AlbumName;
        return allSongs;
    }

    public String getSongName(){ //returns the name of all songs within an album
        String sName = "", substitution;
        String[] nameArray = new String[5]; //creates an array to store song names for alphabetisation
        boolean unsorted = true; //allow loop to run initially
        for(int i=0;i<=songTotal-1;i++){ //assigning all song names to an array for aphabetic comparison
            nameArray[i] = songs[i+1].getName();
        }
        while(unsorted){
            unsorted = false; //while loop will stop if loop is considered sorted
            for(int i=0;i<songTotal-1;i++){
                if(nameArray[i].compareToIgnoreCase(nameArray[i+1])>0){ //if the song name in the array has a higher ASCII numerical value than the one next to it
                    substitution = nameArray[i]; //swapping the places of song name to achieve alphabetical order
                    nameArray[i] = nameArray[i+1];
                    nameArray[i+1] = substitution;
                    unsorted = true; //thus unsorted still
                }
            }
        }
        for(int i=0;i<=songTotal-1;i++){ //returns song names in alphabetical order
            sName = sName+"•"+nameArray[i]+"\n";
        }
        if(sName.equals("")){ //if no songs were found in the album return error
            sName = "No songs yet!\n";
            return sName;
        }
        sName = "Songs: "+sName;
        return sName;
    }

    public int getAllDuration(){ //returns the available duration for songs to be added into album
        int dSum = 0;
        for(int i=1;i<=songTotal;i++){
            dSum = dSum+songs[i].getDuration();
        }
        dSum = MAX_TIME - dSum; //this is the max amount of time the next song added can have
        return dSum;
    }

    public String getUnderDuration(int Dur){ //returns a list of songs that are under a specific duration
        String sList = "";
        for(int i=1;i<=songTotal;i++){ 
            if(songs[i].getDuration() <= Dur){ //if the songs are under the duration add them to the list
                sList = sList + songs[i].getName()+" ("+AlbumName+")\n";
            }
        }
        if(sList.equals("")) //if no songs were found under a certain time return error
            sList = "null";
        return sList;
    }

    public String getUnderGenre(String Genre){ //returns a list of songs under a specific genre
        String gList = "";
        for(int i=1;i<=songTotal;i++){
            if(songs[i].getGenre().equalsIgnoreCase(Genre)) //if the songs are under the genre add them to the list 
                gList = gList + songs[i].getName()+" ("+AlbumName+")\n";
        }
        return gList;
    }

    public String getSongError() //returns error message
    {
        return "Maximum number of songs reached in album (5)";
    }

    public String checkDuplicate(String songName){ //checks to see if a song has the same name one inputted
        int check = 0;
        int delIndex = 0;
        int aIndex[] = new int[5]; //creating an array to hold indexes of same songs
        String upperText, returnString = "";
        for(int i=1;i<=songTotal;i++){
            if(songs[i].getName().equalsIgnoreCase(songName)){ //stores index of song with same name
                aIndex[i-1] = i;
                check++;
            }
        }
        if(check > 1){ //if there is more than one song with the same name
            upperText = "There is more than one song with the name '"+songName+"', enter the corresponding number to delete\n";
            for(int i=0;i<=4;i++){
                if(aIndex[i] > 0){ //returning entire contents of all songs with same name for user to see
                    returnString = returnString + "===================\nEnter ("+aIndex[i]+") to delete this song\n"+getSongsAtIndex(aIndex[i])+"\n";
                }
            }
            return upperText + returnString;
            
        }
        else if(check == 1){ //if there is no duplicates
            return "one";
        }
        return "null";
    }

    public String getSongsAtIndex(int index){ //returns all song details at an index
        return "\nSong: "+songs[index].getName()+"\nArtist: "+songs[index].getArtist()+"\nDuration: "+songs[index].getDuration()+"\nGenre: "+songs[index].getGenre()+"\n===================";
    }

    public String deleteSong(int index){ //deletes a song from a given index
        for(int i=index;i<=songTotal-1;i++){ //writing over deleted song with its index successor
            songs[i]=songs[i+1];
        }
        songTotal--; //globally decrementing song total
        return "Successfully deleted song";
    }
    
    public int getSongIndex(String SongName){ //returns the song index from given song name
        int returnIndex = 0;
        for(int i=1;i<=songTotal;i++){
            if(songs[i].getName().equalsIgnoreCase(SongName)){ //when the input matches up with a song name it returns the index
                returnIndex = i;
            }
        }
        return returnIndex;
    }
    
    public String findDuplicate(String sName){ //finds the duplicate song and returns a list of same song details
        int sIndex[] = new int[5]; //creating an array to hold indexes of same songs
        int check = 0;
        String returnFirst, returnList = "", returnLast = "\nEnter (1) to continue adding this song\nEnter (2) to cancel";
        for(int i=1;i<=songTotal;i++){
            if(songs[i].getName().equalsIgnoreCase(sName)){ //if inputted name equals a song name store its index
                sIndex[i-1] = i;
                check++;
            }
        }
        if(check >= 1){
            returnFirst = "There is already a song or more with the same name '"+sName+"', here is the song/s\n";
            for(int i=0;i<=4;i++){
                if(sIndex[i]>0){ //add a song to return list
                    returnList = returnList + "==================="+getSongsAtIndex(sIndex[i]);
                }
            }
            return returnFirst + returnList + returnLast;
        }
        return "null";
    }
}
