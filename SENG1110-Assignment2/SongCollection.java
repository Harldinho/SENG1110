/*
 * Author: Harlan De Jong
 * Student Number: 3349828
 * Date: 12/06/20
 */
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
public class SongCollection
{
    static Scanner console = new Scanner(System.in);
    private Album[] albums;
    private int action, albumTotal = 0;
    private final int MAX_ALBUMS = 5; //max is 4 but index starts at 1 during entire code, thus 5
    private void run(){ //runs all the functions privately
        console.useDelimiter("\n");
        albums = new Album[MAX_ALBUMS];
        showSelection(); //prints options
        showFileSelection(); //prints file option once
        showExitSelection(); //prints exit selection
        System.out.println("==================================================================");
        action = console.nextInt();
        while(action != 11)  {
            switch(action) {
                case 1:
                if(albumTotal == 4)
                    System.out.println("Album limit reached (4)");
                else
                    createAlbum(); //album creation function
                break;
                case 2: 
                addSong(); //function to add songs to album
                break;
                case 3:
                songsSpecificName(); //finds songs under a specified name
                break;
                case 4:
                albumDisplaySongs(); //displays all songs + contents from and album
                break;
                case 5:
                allAlbumDisplay(); //displays all albums and the song names of its contents
                break;
                case 6:
                songsUnderDuration(); //finds songs under a specified duration in minutes
                break;
                case 7:
                songsUnderGenre(); //finds songs under a specified genre
                break;
                case 8:
                deleteAlbum(); //deletes an album
                break;
                case 9:
                deleteSong(); //deletes a song from an album
                break;
                case 10:
                fileInput(); //allows the user to input a .txt file into the directory to be read for album/song creation (must be in the same directory as this .java file)
                break;
                default: System.out.println("Inputted number must be 1-11"); 
            }//end switch    
            showSelection(); //reprinting to user
            showExitSelection();
            action = console.nextInt();

        }
    }

    public static void main(String[] args) {
        SongCollection sg = new SongCollection();
        sg.run();
    }

    public static void showSelection(){
        System.out.println("==================================================================");
        System.out.println("Enter the corresponding number for what action you want to perform");
        System.out.println("==================================================================");
        System.out.println("(1) Create an album\n(2) Enter a song into an album\n(3) Request list of all songs with a specific name");
        System.out.println("(4) Request list of all songs from an album\n(5) Request list of all albums\n(6) Request list of all songs under a specified time");
        System.out.println("(7) Request a list of all songs within a genre\n(8) Delete an album");
        System.out.println("(9) Delete a song from an album");
    }

    public static void showFileSelection(){
        System.out.println("(10) Input albums and songs from an external file (Must be in the same directory)"); //This only gets displayed once
    }

    public static void showExitSelection(){
        System.out.println("(11) Exit");
    }

    public void createAlbum(){ //allows user to create albums
        String albumName;
        int check = 0;
        if(maxAlbum() == 0){ //checking to see if max album limit has been reached
            System.out.println("Input a name for the album: ");
            albumName = console.next();
            for(int i=1;i<=albumTotal;i++){ //iterating through all already made album names to see if any are the same as albumName
                if(albumName.equalsIgnoreCase(albums[i].getName()))
                    check = 1;
            }
            if(check == 1) //if albumName is the same as one already created and error message is sent
                System.out.println("Album name cannot be the same as one already created");
            else{
                albumTotal++; //incrementing total for global reference
                albums[albumTotal] = new Album(); //creating a new Album object
                albums[albumTotal].createAlbum(albumName); //creating album with given album name
            }
        }
    }

    public int maxAlbum(){ //checks to see if maximum amount of albums have been created already
        int check = 0;
        if(albumTotal == 4){
            System.out.println("Maximum number of albums created (4)");
            check = 1;
        }
        return check;

    }

    public void addSong(){ //allows user to add a song to an existing album
        String songName, artistName, albumSelect, songGenre;
        int albumIndex, songDuration;
        System.out.println("Which album do you want to add a song to?");
        albumSelect = console.next();
        if(checkAlbumExists() == 0){ //checks to see if any albums exist for a song to be added to
            if(pickAlbum(albumSelect) == 0){ //checks to see if the user submitted album name is legal
                System.out.println("No album exists with that name");
            }
            else{
                albumIndex = pickAlbum(albumSelect);
                songName = askSong(albumIndex); 
                if(songName.equals("null")){ //null is returned if the user doesnt want to continue adding a song with the same name as one already added
                    System.out.println("Action stopped");
                }
                else{
                    artistName = askArtist();
                    songDuration = askDuration();
                    if(sumDuration(albumIndex) < songDuration){ //checks that the duration is within; sum of all durations/720seconds
                        System.out.println("Duration of song must not exceed "+sumDuration(albumIndex)+" seconds");
                    }
                    else if(sumDuration(albumIndex) == 0){ //if all songs in album have reached a combined 720 seconds
                        System.out.println("Album song duration limit reached");
                    }
                    else if(songDuration == 0) //checks the user has entered a duration > 0
                        System.out.println("The duration must be greater than 0");
                    else{    
                        songGenre = askGenre();
                        if(songGenre.equals("Null")) //checks to see if the user has entered a legal genre
                            System.out.println("The song Genre must be either rock, pop, hip-hop or bossa nova");
                        else{    
                            albums[albumIndex].addSong(songName, artistName, songDuration, songGenre); //adds all aspects of song into album
                        }
                    }
                }
            }
        }

    }

    public int pickAlbum(String aSelect){//accepts an album name and returns the index with which it is in
        for(int i=1;i<=albumTotal;i++){
            if(aSelect.equalsIgnoreCase(albums[i].getName())) //as soon as it finds a matching name is will return the index
                return i;
        }
        return 0;
    }

    public String askSong(int albumIndex){ //asks the user what song they want to add
        String sName;
        System.out.println("Enter a song name:");
        sName = console.next();
        if(checkDupSong(sName, albumIndex) == 1){ //if user chooses that they dont want to add a song with the same name as one already
            return "null";
        }
        return sName;
    }

    public int checkDupSong(String sName, int albumIndex){ //checks to see if a song name is already used within the album
        int index=0, decision=0;
        if(!(albums[albumIndex].findDuplicate(sName).equals("null"))){ //if a duplicate song name is found within the album
            System.out.println(albums[albumIndex].findDuplicate(sName)); //asks user for confirmation to either continue or cancel adding duplicate song name
            decision = console.nextInt(); 
        }
        if(decision == 2){ //if user decides to cancel return 1
            return 1;
        }
        return 0;
    }

    public String askArtist(){ //asks user to input artist name, returns artistname
        String aName;
        System.out.println("Enter the artists name:");
        aName = console.next();
        return aName;
    }

    public int askDuration(){ //asks user to input duration, returns duration
        int sDur;
        System.out.println("Enter the duration of the song:");
        sDur = console.nextInt();
        if(sDur <= 0) //checks to see if duration is greater than 0
            return 0;
        return sDur;    
    }

    public int sumDuration(int index){ //returns sum of all songs duration in an album
        int durSum = 0;
        durSum = albums[index].getAllDuration();
        return durSum;
    }

    public String askGenre(){ //asks user for genre, returns genre
        String sGenre;
        System.out.println("Enter the genre of the song: (rock, pop, hip-hop or bossa nova)");
        sGenre = console.next();
        if(!(sGenre.equalsIgnoreCase("rock") || sGenre.equalsIgnoreCase("pop") || sGenre.equalsIgnoreCase("hip-hop") || sGenre.equalsIgnoreCase("bossa nova")))
            return "Null"; //checks to see if user entered legal genre type
        return sGenre;
    }

    public void songsSpecificName(){ //prints a list of all songs under a specific name
        String SpecName;
        if(checkAlbumExists() == 0){ //checking that an album exists first
            System.out.println("Enter the name of the song/s:");
            SpecName = console.next();
            for(int i=1;i<=albumTotal;i++){ //printing all songs under a specific name
                System.out.println(albums[i].getSpecificSong(SpecName));
            }
        }
    }

    public void albumDisplaySongs(){ //displays all songs alphabetically from a specific album
        String inputAlbum;
        int check = 0;
        if(checkAlbumExists() == 0){ //first checking if an album exists
            System.out.println("Enter the album name that you want to display songs from:");
            inputAlbum = console.next();
            if(checkAlbumName(inputAlbum) == 0){ //checking that given album name is legal, one that exists
                for(int i=1;i<=albumTotal;i++){ //iterating through all albums
                    if(albums[i].getName().equalsIgnoreCase(inputAlbum)) //if inputted album name is found to be one that exists, print all songs alphabetically
                        System.out.println(albums[i].getSongs());
                }
            }
        }
    }

    public void allAlbumDisplay(){ //displays all the albums and the song names of all songs within
        String[] albumList = new String[albumTotal]; //creating array to hold album names for alphabetising
        boolean unsorted = true; //condition is true for initial loop run
        String substitution; //used for temporary storage
        if(checkAlbumExists() == 0){ //checking that an album exists
            for(int i=0;i<=albumTotal-1;i++){ //storing all album names in an array
                albumList[i] = albums[i+1].getName();
            }
            while(unsorted){
                unsorted = false; //while loop will stop if loop is considered sorted
                for(int i=0;i<albumTotal-1;i++){ 
                    if(albumList[i].compareToIgnoreCase(albumList[i+1])>0){ //if the album name in the array has a higher ASCII numerical value than the one next to it
                        substitution = albumList[i]; //swapping the places of album name to achieve alphabetical order
                        albumList[i] = albumList[i+1];
                        albumList[i+1] = substitution;
                        unsorted = true; //thus unsorted still
                      
                    }
                }
            }
            for(int i=0;i<=albumTotal-1;i++){ //prints album names and songs in alphabetical order
                System.out.println("Album: "+albumList[i]+"\n"+albums[getAlbumIndex(albumList[i])].getSongName());
            }

        }
    }

    public int getAlbumIndex(String albName){ //returns index of an album given the name
        int returnIndex = 0;
        for(int i=1;i<=albumTotal;i++){
            if(albums[i].getName().equalsIgnoreCase(albName)){ //if the given name is equal to an already existing album return its index
                returnIndex = i;
            }
        }
        return returnIndex;
    }

    public void songsUnderDuration(){ //asks user for specified duration parameter, prints all songs that are under that duration time
        int selectedDuration;
        int check = 0;
        if(checkAlbumExists() == 0){ //first checking that an album exists
            do{
                System.out.println("Enter the duration (in minutes) that you want to see songs under:");
                selectedDuration = console.nextInt();
                selectedDuration *= 60; //multiplying by 60 for minutes, as per specification
                if(selectedDuration <= 0) //checks that the input is greater than 0
                    System.out.println("The duration must be greater than 0");
            }
            while(selectedDuration <= 0); //asks user for duration again if it is not greater than 0
            for(int i=1;i<=albumTotal;i++){
                if(!(albums[i].getUnderDuration(selectedDuration).equals("null"))){ //if a song has been found under parameters, it is printed
                    System.out.println(albums[i].getUnderDuration(selectedDuration));
                }
                else //if a song hasn't been found under parameters
                    check++;
            }
            if(check == albumTotal) //if no songs were found print error
                System.out.println("No songs were found under "+selectedDuration/60+" minute/s");
        }

    }

    public void songsUnderGenre(){ //asks user for specific genre, prints all songs that are under that genre 

        String selectedGenre;

        if(checkAlbumExists() == 0){ //first checking that an album exists
            do{
                
                System.out.println("Enter the genre you want to display all songs under:");
                selectedGenre = console.next();
                if(!(selectedGenre.equalsIgnoreCase("rock") || selectedGenre.equalsIgnoreCase("pop") || selectedGenre.equalsIgnoreCase("hip-hop") || selectedGenre.equalsIgnoreCase("bossa nova")))
                    System.out.println("Inputted genre must either be rock, pop, hip-hop or bossa nova"); //if the genre isnt legal print error
            }
            while(!(selectedGenre.equalsIgnoreCase("rock") || selectedGenre.equalsIgnoreCase("pop") || selectedGenre.equalsIgnoreCase("hip-hop") || selectedGenre.equalsIgnoreCase("bossa nova")));
            for(int i=1;i<=albumTotal;i++){
                System.out.print(albums[i].getUnderGenre(selectedGenre)); //printing all songs under the specific genre

            }
        }
    }

    public int checkAlbumExists(){ //checks if an album exists
        int check = 0;
        if(albumTotal == 0){
            System.out.println("No albums currently exist!");
            check = 1;
        }
        return check;
    }

    public int checkAlbumName(String albumN){ //checking if an album exists with a specific name
        int check = 0;
        for(int i=1;i<=albumTotal;i++){
            if(!(albums[i].getName().equalsIgnoreCase(albumN))) //if no matching name has been found...
                check++;
        }
        if(check == albumTotal){ //prints error if no matching names were found
            System.out.println("That album name doesn't exist!");
            check = 1;
            return check;
        }
        check = 0;
        return check;
    }

    public void deleteAlbum(){ //deletes an album from user input
        String AName;
        if(checkAlbumExists() == 0){ //checking that an album exists first
            System.out.println("Input the album name you want to delete:");
            AName = console.next();
            if(checkAlbumName(AName) == 0){ //checking if inputted name is a valid one
                confirmDeleteAlbum(AName); //deleting album
                System.out.println("Album '"+AName+"' successfully deleted");
            }
        }
    }

    public void confirmDeleteAlbum(String album){ //removes album from memory
        int index = 0;
        index = pickAlbum(album); //finds index of album
        albums[index].setName(""); //sets the name to nothing
        shuffle(index); //writes over the album with albums in the successing index
        albumTotal--; //reducing album total globally
    }

    public void shuffle(int indexNum){ //writes over an album to delete
        for(int i=indexNum;i<=albumTotal-1;i++){
            albums[i] = albums[i+1]; //writing over the album in the successing index
        }

    }

    public void deleteSong(){ //deletes a song from user input
        String AName, SName;
        int index = 0;
        if(checkAlbumExists() == 0){ //checking that an album exists first
            System.out.println("Input the album you want to delete a song from:");
            AName = console.next();
            if(checkAlbumName(AName) == 0){ //checking that the inputted album name is valid
                System.out.println("Input the song name you want to delete from album '"+AName+"':");
                SName = console.next();
                if(!(albums[pickAlbum(AName)].checkDuplicate(SName).equals("one") || albums[pickAlbum(AName)].checkDuplicate(SName).equals("null"))){
                    System.out.println(albums[pickAlbum(AName)].checkDuplicate(SName)); //if there is more than one song with the same name
                    index = console.nextInt();                                          //user will input the index of the song they want to delete
                    System.out.println(albums[pickAlbum(AName)].deleteSong(index));     //after seeing a detailed view of the songs
                }
                else if(albums[pickAlbum(AName)].checkDuplicate(SName).equals("one")){ //if there is only one song with that name
                    index = albums[pickAlbum(AName)].getSongIndex(SName);
                    System.out.println(albums[pickAlbum(AName)].deleteSong(index)); //delete the song at that index
                }
                else
                    System.out.println("No songs were found in album '"+AName+"' under the name '"+SName+"'");
            }
        }
    }

    public void fileInput(){

        String fName;
        int index=0;
        System.out.println("Enter the name of the file:");
        fName = console.next();
        try{
            String AlbumN, sName = "", aName = "", dur = "", sGenre = "";
            int Duration = 0;
            File myFile = new File(fName); //creating a new file object
            Scanner Reader = new Scanner(myFile); //creating a scanner object to read file
            while (Reader.hasNextLine()) { //while not at EOF

                String string = Reader.nextLine(); //storing a line read from file in string
                if(string.contains("Album ")){ //indicating that the following will be an album name
                    int length = string.length(); //finding length of string
                    index = string.indexOf(" ") + 1; //finds the index where a space was found and adds 1 to receive index start of album name
                    AlbumN = string.substring(index, length); //album name is equal to everything past 'Album '
                    albumTotal++; //global album total increments
                    albums[albumTotal] = new Album(); //creating a new album object
                    albums[albumTotal].createAlbum(AlbumN); //creating a new album with given name
                }
                if(string.contains("Name ")){ //indicating that the following will be a song name
                    int length = string.length(); 
                    index = string.indexOf(" ") + 1; //^ aforementioned
                    sName = string.substring(index, length); //^ aforementioned
                }
                if(string.contains("Artist ")){ //indicating that the following will be an artist name
                    int length = string.length();
                    index = string.indexOf(" ") + 1; //^ aforementioned
                    aName = string.substring(index, length); //^ aforementioned
                }
                if(string.contains("Duration ")){ //indicating that the following will be the duration
                    int length = string.length();
                    index = string.indexOf(" ") + 1; //^ aforementioned
                    dur = string.substring(index, length); //^ aforementioned
                    Duration = Integer.parseInt(dur); //converting the string of duration to appropriate integer type
                }
                if(string.contains("Genre ")){ //indicating that the following will be a genre
                    int length = string.length();
                    index = string.indexOf(" ") + 1; //^ aforementioned
                    sGenre = string.substring(index, length); //^ aforementioned
                }
                if(!(sGenre.equals(""))){ //if the genre has a value other than nothing, this is because genre is the last to be chosen
                    albums[albumTotal].addSong(sName, aName, Duration, sGenre); //adding all the songs to given album
                    sName = ""; //reinitialising everything to "" for next iteration
                    aName = "";
                    dur = "";
                    sGenre = "";
                }

            }
        }
        catch(FileNotFoundException e){ //shows the user an error message if the inputted file is not found
            System.out.println("An error occurred.");
            e.printStackTrace(); //printing where the error has been derived from, to console

        }
    }

}