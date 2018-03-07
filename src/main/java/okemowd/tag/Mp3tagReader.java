package okemowd.tag;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.ID3v24Frames;
import org.jaudiotagger.tag.id3.ID3v24Tag;

/**
 * This class is to get info of music from mp3 file by reading the tag.
 * If mp3 file does not have ID3v2 tag, all info will be empty. 
 */
public class Mp3tagReader{
  
  private String title;
  private String artist;
  private String artistr;
  private int year;
  private String genre;
  
  /**
   * Parse the file.
   * @param mp3file mp3 file whose tag is read
   */
  public Mp3tagReader(File file){
  //public Mp3tagReader(File mp3file){
    
    System.out.println("[Mp3tagReader/Mp3tagReader] start");//<<<log
    
    try{
      MP3File mp3file = (MP3File)AudioFileIO.read(file);
      
      if(mp3file.hasID3v2Tag()){
        System.out.println("hasID3v2Tag");//<<<log
        
        ID3v24Tag tag = mp3file.getID3v2TagAsv24();
        
        title = tag.getFirst(ID3v24Frames.FRAME_ID_TITLE);
        genre = tag.getFirst(ID3v24Frames.FRAME_ID_GENRE);

        artist = tag.getFirst(ID3v24Frames.FRAME_ID_ARTIST);
        if(artist.isEmpty())
          artist = tag.getFirst(ID3v24Frames.FRAME_ID_ACCOMPANIMENT);//"TPE2"
        artistr = Phonetic.getKana(artist);
        
        String syear = tag.getFirst(ID3v24Frames.FRAME_ID_YEAR);
        year = 1;
        try{
          if(syear.length() >= 4)
            year = Integer.parseInt(syear.substring(0, 4));//get first 4 digit
        }catch(NumberFormatException e){}
        
      }else if(mp3file.hasID3v1Tag()){
        System.out.println("hasID3v1Tag");//<<<log
                
        /*
         * not support ID3v1 now
         * because jaudiotagger cannot get UTF-16 format correctly when ID3v1.
         */
        //ID3v1Tag tag = mp3file.getID3v1Tag();
        //tag.getFirstTitle();
        //tag.getFirstArtist();
        //tag.getFirstYear();
        //tag.getFirstGenre();
        title = "";
        artist = "";
        year = 1;
        genre = "";
        
      }else{
        System.out.println("NoTag?");//<<<log
        
        title = "";
        artist = "";
        year = 1;
        genre = "";
      }
    
    }catch(CannotReadException | IOException | TagException
    | ReadOnlyFileException | InvalidAudioFrameException e){
      System.out.println("NoTag?2");//<<<log
      title = "";
      artist = "";
      year = 1;
      genre = "";
    }
  }
  
  
  /**
   * Get song title.
   */
  public String getTitle(){
    return title;
  }
  
  /**
   * Get artist.
   * If not exist, get album-artist.
   */
  public String getArtist(){
    return artist;
  }
  
  /**
   * Get ruby of artist. 
   */
  public String getArtistRuby(){
    return artistr;
  }
  
  /**
   * Get released year with int.
   */
  public int getYear(){
    return year;
  }
  
  /**
   * Get genre with string.
   */
  public String getGenre(){
    return genre;
  }
}
