/*
 * Author: Harlan De Jong
 * Student Number: 3349828
 * Date: 22/05/20
 */
import java.util.*;
public class SongCollection
{
    private Album album1, album2, album3;
    private int action, albumNumber = 0, check = 0, check1 = 0, check2 = 0, check3 = 0, songDuration, songNum1 = 1, songNum2 = 1, songNum3 = 1, genreCheck = 0, allDuration = 0, i = 1, noAlbumCheck=0, songTime=0, sum = 0, duplicateCheck=0;
    private Scanner console = new Scanner(System.in);
    private String albumName, albumSelection, songName, artistName, songGenre, genrePick, albumDel, albumDelSong, songDel;
    private void run(){
        console.useDelimiter("\n");
        album1 = new Album();
        album2 = new Album();
        album3 = new Album();
        
        showSelection();
        action = console.nextInt();
        while(action != 9)  {
            switch(action) {
                case 1:
                createAlbum();
                break;
                case 2: 
                addSong();
                break;
                case 3:
                albumDisplaySongs();
                break;
                case 4:
                albumList();
                break;
                case 5:
                songsUnderTime();
                break;
                case 6:
                songsUnderGenre();
                break;
                case 7:
                deleteAlbum();
                break;
                case 8:
                deleteSong();
                break;
                case 9: 
                break;
                default: System.out.println("Inputted number must be 1-9");
            }//end switch    
            showSelection(); 
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
        System.out.println("(1) Create an album\n(2) Enter a song into an album\n(3) Request list of all songs\n(4) Request list of all albums\n(5) Request list of all songs under a specified time\n(6) Request a list of all songs within a genre\n(7) Delete an album\n(8) Delete a song from an album\n(9) Exit");
        System.out.println("==================================================================");
    }

    public void createAlbum(){
        if(albumNumber >= 3)
            System.out.println("Maximum number of albums created (3)");
        else{
            System.out.println("Input a name for the album");
            albumName = console.next();
            if(albumName.equalsIgnoreCase(album1.getName()) || albumName.equalsIgnoreCase(album2.getName()) || albumName.equalsIgnoreCase(album3.getName()))
                System.out.println("Album name already exists");
            else{
                albumNumber++;
                if(albumNumber == 1 || album1.getName().equalsIgnoreCase("")){
                    album1.createAlbum(albumName);
                }
                else if (albumNumber == 2 || album2.getName().equalsIgnoreCase("")){
                    album2.createAlbum(albumName);
                }
                else if(albumNumber == 3 || album3.getName().equalsIgnoreCase("")){
                    album3.createAlbum(albumName);
                }
            }
        }
    }

    public void addSong(){
        check = 0;
        if(album1.getName().equalsIgnoreCase("") && album2.getName().equalsIgnoreCase("") && album3.getName().equalsIgnoreCase(""))
            System.out.println("No albums created yet!");
        else{
            System.out.println("Which album do you want to add a song to? ");
            albumSelection = console.next();
            if(albumSelection.equalsIgnoreCase(album1.getName())){
                check = 1;
            }
            else if(albumSelection.equalsIgnoreCase(album2.getName())){
                check = 2;
            }
            else if(albumSelection.equalsIgnoreCase(album3.getName())){
                check = 3;
            }
            if(check == 0)
                System.out.println("Album must be one that exists");
            else{
                if(songNum1 > 4 && check == 1){
                    System.out.println("Album song limit reached (4)");
                }
                else if(songNum2 > 4 && check == 2){
                    System.out.println("Album song limit reached (4)");    
                }
                else if(songNum3 > 4 && check == 3){
                    System.out.println("Album song limit reached (4)");   
                }
                else if(getDurationSum(1) <= 0 && check == 1)
                    System.out.println("Maximum song duration storage limit reached (720 seconds)");
                else if(getDurationSum(2) <= 0 && check == 2)
                    System.out.println("Maximum song duration storage limit reached (720 seconds)");
                else if(getDurationSum(3) <= 0 && check == 3)
                    System.out.println("Maximum song duration storage limit reached (720 seconds)");
                else{
                    System.out.println("Enter a song name: ");
                    songName = console.next();
                    System.out.println("Enter the artists name: ");
                    artistName = console.next();
                    System.out.println("Enter the duration of the song (seconds): ");
                    songDuration = console.nextInt();
                    if(songDuration > getDurationSum(check))
                        System.out.println("Duration of song must not exceed "+getDurationSum(check)+" seconds");
                    else{
                        System.out.println("Enter the genre of the song: ");
                        songGenre = console.next();
                        songGenre = songGenre.toLowerCase();
                        if(songGenre.equalsIgnoreCase("rock") || songGenre.equalsIgnoreCase("pop") || songGenre.equalsIgnoreCase("hip-hop") || songGenre.equalsIgnoreCase("bossa nova")){
                            if(checkDuplicateSong(songName, artistName, songDuration, songGenre) == 1)
                                System.out.println("This song already exists in an album");
                            else if(check == 1){
                                album1.addSong(songName, artistName, songDuration, songGenre);
                                songNum1++;
                            }
                            else if(check == 2){
                                album2.addSong(songName, artistName, songDuration, songGenre);
                                songNum2++;
                            }
                            else if(check == 3){
                                album3.addSong(songName, artistName, songDuration, songGenre);
                                songNum3++;
                            }
                        }
                        else
                            System.out.println("Genre must be either:\nrock, pop, hip-hop or bossa nova");
                    }
                }
            }
        }
    }
    
    public int checkDuplicateSong(String sName, String aName, int sDuration, String sGenre){
        duplicateCheck = 0;
        for(int x=1;x<=4;x++){
            if(album1.duplicateSongCheck(x, sName, aName, sDuration, sGenre) != 0)
                duplicateCheck = 1;
            if(album2.duplicateSongCheck(x, sName, aName, sDuration, sGenre) != 0)
                duplicateCheck = 1;
            if(album3.duplicateSongCheck(x, sName, aName, sDuration, sGenre) != 0)
                duplicateCheck = 1;
            if(duplicateCheck == 1)
                return duplicateCheck;
        }
        return duplicateCheck;
    }

    public int getDurationSum(int albumNUM){
        sum = 0;
        if(albumNUM == 1){
            sum = album1.getAllSongDuration();
        }
        else if (albumNUM == 2){
            sum = album2.getAllSongDuration();
        }
        else if (albumNUM == 3){
            sum = album3.getAllSongDuration();
        }

        return sum;
    }

    public void albumDisplaySongs(){
        check = 0;
        if(album1.getName().equalsIgnoreCase("") && album2.getName().equalsIgnoreCase("") && album3.getName().equalsIgnoreCase(""))
            System.out.println("No albums created yet!");
        else{    
            System.out.println("Which album do you want to display all songs? ");
            albumSelection = console.next();
            if(albumSelection.equalsIgnoreCase(album1.getName())){
                check = 1;
            }
            else if(albumSelection.equalsIgnoreCase(album2.getName())){
                check = 2;
            }
            else if(albumSelection.equalsIgnoreCase(album3.getName())){
                check = 3;
            }
            if(check == 0)
                System.out.println("Album must be one that exists");
            else{    
                if(check == 1)
                    if(songNum1-1 == 0)
                        System.out.println("This album does not have any songs yet");
                    else{
                        for(i=1;i<=4;i++){
                            if(album1.getDuration(i) != 0){
                                System.out.println(album1.getSongAlbum(i)+"\n");    
                            }
                        }
                    }
                else if(check == 2)
                    if(songNum2-1 == 0)
                        System.out.println("This album does not have any songs yet");
                    else
                        for(i=1;i<=4;i++){
                            if(album2.getDuration(i) != 0){
                                System.out.println(album2.getSongAlbum(i)+"\n");    
                            }
                        }
                else if(check == 3)
                    if(songNum3-1 == 0)
                        System.out.println("This album does not have any songs yet");
                    else
                        for(i=1;i<=4;i++){
                            if(album3.getDuration(i) != 0){
                                System.out.println(album3.getSongAlbum(i)+"\n");    
                            }
                        }
            }
        }
    }

    public void albumList(){
        noAlbumCheck = 0;
        if(!album1.getName().equalsIgnoreCase("")){
            System.out.println("=============================\nAlbum name: "+album1.getName());
            if(songNum1-1 == 0)
                System.out.println("\nNo songs yet!\n=============================");
            else{
                System.out.print("\nSong/s: ");
                for(i=1;i<=4;i++){
                    if(album1.getDuration(i) != 0)
                        System.out.print("•"+album1.getSongName(i)+"\n");
                }
            }
        }
        else
            noAlbumCheck++;
        if(!album2.getName().equalsIgnoreCase("")){
            System.out.println("=============================\nAlbum name: "+album2.getName());
            if(songNum2-1 == 0)
                System.out.println("\nNo songs yet!\n=============================");
            else{
                System.out.print("\nSong/s: ");
                for(i=1;i<=4;i++){
                    if(album2.getDuration(i) != 0)
                        System.out.print("•"+album2.getSongName(i)+"\n");
                }
            }
        }
        else
            noAlbumCheck++;
        if(!album3.getName().equalsIgnoreCase("")){
            System.out.println("=============================\nAlbum name: "+album3.getName());
            if(songNum3-1 == 0)
                System.out.println("\nNo songs yet!\n=============================");
            else{
                System.out.print("\nSong/s: ");
                for(i=1;i<=4;i++){
                    if(album3.getDuration(i) != 0)
                        System.out.print("•"+album3.getSongName(i)+"\n");
                }
            }
        }
        else
            noAlbumCheck++;
        if(noAlbumCheck == 3)
            System.out.println("No albums created yet");    
    }

    public void songsUnderTime(){
        if(album1.getName().equalsIgnoreCase("") && album2.getName().equalsIgnoreCase("") && album3.getName().equalsIgnoreCase(""))
            System.out.println("No albums created yet!");
        else if(songNum1 == 1 && songNum2 == 1 && songNum3 == 1)
            System.out.println("No songs yet!");
        else{    
            do{
                System.out.println("At what time do you want to display songs under this time (minutes): ");
                songTime = console.nextInt();
                songTime = 60*songTime;
                if(songTime <= 0)
                    System.out.println("Time must be greater than 0 minutes");
            }
            while(songTime <= 0);
            if(album1.getSongUnderDuration(songTime).equals("") && album2.getSongUnderDuration(songTime).equals("") && album3.getSongUnderDuration(songTime).equals(""))
                System.out.println("No songs under "+songTime/60+" minute/s");
            else{
                System.out.print(album1.getSongUnderDuration(songTime));
                System.out.print(album2.getSongUnderDuration(songTime));
                System.out.print(album3.getSongUnderDuration(songTime));
            }
        }    
    }

    public void songsUnderGenre(){
        if(album1.getName().equalsIgnoreCase("") && album2.getName().equalsIgnoreCase("") && album3.getName().equalsIgnoreCase(""))
            System.out.println("No albums created yet!");
        else if(songNum1 == 1 && songNum2 == 1 && songNum3 == 1)
            System.out.println("No songs yet!");
        else{
            System.out.println("Under what genre do you want to display songs: ");
            genrePick = console.next();
            if (genrePick.equalsIgnoreCase("rock")){
                System.out.print(album1.allGenreSongs(1));
                System.out.print(album2.allGenreSongs(1));
                System.out.print(album3.allGenreSongs(1));
            }   
            else if(genrePick.equalsIgnoreCase("pop")){
                System.out.print(album1.allGenreSongs(2));
                System.out.print(album2.allGenreSongs(2));
                System.out.print(album3.allGenreSongs(2));
            }
            else if(genrePick.equalsIgnoreCase("hip-hop")){
                System.out.print(album1.allGenreSongs(3));
                System.out.print(album2.allGenreSongs(3));
                System.out.print(album3.allGenreSongs(3));
            }
            else if(genrePick.equalsIgnoreCase("bossa nova")){
                System.out.print(album1.allGenreSongs(4));
                System.out.print(album2.allGenreSongs(4));
                System.out.print(album3.allGenreSongs(4));
            }
            else
                System.out.println("Genre must be either:\nrock, pop, hip-hop or bossa nova"); 
        }    
    }

    public void deleteAlbum(){
        if(album1.getName().equalsIgnoreCase("") && album2.getName().equalsIgnoreCase("") && album3.getName().equalsIgnoreCase(""))
            System.out.println("No albums created yet!");
        else{
            System.out.println("Which album do you want to delete: ");
            albumDel = console.next();
            if(album1.getName().equalsIgnoreCase(albumDel)){
                System.out.println("Album "+album1.getName()+" deleted.");
                album1.setName("");
                albumNumber--;
            }
            else if(album2.getName().equalsIgnoreCase(albumDel)){
                System.out.println("Album "+album2.getName()+" deleted.");
                album2.setName("");
                albumNumber--;
            }
            else if(album3.getName().equalsIgnoreCase(albumDel)){
                System.out.println("Album "+album3.getName()+" deleted.");
                album3.setName("");
                albumNumber--;
            }
            else{
                System.out.println("That album does not exist");
            }
        }
    }

    public void deleteSong(){
        if(album1.getName().equalsIgnoreCase("") && album2.getName().equalsIgnoreCase("") && album3.getName().equalsIgnoreCase(""))
            System.out.println("No albums created yet!");
        else if(songNum1 == 1 && songNum2 == 1 && songNum3 == 1)
            System.out.println("No songs yet!");
        else{
            System.out.println("Which album do you want a song to be deleted from:");
            albumDelSong = console.next();
            if(album1.getName().equalsIgnoreCase(albumDelSong)){
                System.out.println("Which song do you want to delete:");
                songDel = console.next();
                if(album1.deleteSong(songDel) == 0){
                    songNum1--;
                    System.out.println("Successfully deleted song: "+songDel);
                }
                else
                    System.out.println("Song not in album");
            }
            else if(album2.getName().equalsIgnoreCase(albumDelSong)){
                System.out.println("Which song do you want to delete:");
                songDel = console.next();
                if(album2.deleteSong(songDel) == 0){
                    songNum2--;
                    System.out.println("Successfully deleted song: "+songDel);
                }
                else
                    System.out.println("Song not in album");
            }
            else if(album3.getName().equalsIgnoreCase(albumDelSong)){
                System.out.println("Which song do you want to delete:");
                songDel = console.next();
                if(album3.deleteSong(songDel) == 0){
                    songNum3--;
                    System.out.println("Successfully deleted song: "+songDel);
                }
                else
                    System.out.println("Song not in album");
            }
            else{
                System.out.println("That album does not exist");
            }
        }
    }
}
