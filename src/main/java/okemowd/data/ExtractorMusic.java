package okemowd.data;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * 
 * TODO
 * 
 * another statment is created in this class, so don't be afraid
 *
 *
 */
public class ExtractorMusic{
  
  private Connection conn;//MusicDataManager's
  private Statement stmt;//different one from MusicDataManager's
  private ResultSet rs_dmain;
  
  private String title;
  private String artist;
  private int year;
  private String genre;
  private List<String> artistfs;
  private List<String> tag;
  
  
  /**
   * TODO
   * @param mdm
   */
  public ExtractorMusic(MusicDataManager mdm){//<<
    conn = mdm.getConn();//<<
    try{
      stmt = conn.createStatement();//create new statement
    }catch(SQLException e){
      e.printStackTrace();
    }
  }
  
  
  /**
   * Start extraction by artistfs. Call method getNext() next.
   * @param artistfs
   */
  public void startByArtistfs(String artistfs){
    try{
      rs_dmain = stmt.executeQuery("SELECT * FROM dmain WHERE music_id IN ("
          + "SELECT music_id FROM dafs WHERE artistfs = '" + artistfs + "'" //(DISTINCT)
          + ")");
    }catch(SQLException e){
      e.printStackTrace();
    }
  }
  
  /**
   * Start extraction by tag. Call method getNext() next.
   * @param tag
   */
  public void startByTag(String tag){
    try{
      rs_dmain = stmt.executeQuery("SELECT * FROM dmain WHERE music_id IN ("
          + "SELECT music_id FROM dtag WHERE tag = '" + tag + "'" //(DISTINCT)
          + ")");
    }catch(SQLException e){
      e.printStackTrace();
    }
  }
  
  /**
   * Start extraction by year. Call method getNext() next.
   * @param year
   */
  public void startByYear(int year){
    try{
      rs_dmain = stmt.executeQuery("SELECT * FROM dmain WHERE year = " + year);
    }catch(SQLException e){
      e.printStackTrace();
    }
  }
  
  /**
   * Start extraction by genre_id. Call method getNext() next.
   * @param genre_id
   */
  public void startByGenreId(int genre_id){
    try{
      rs_dmain = stmt.executeQuery("SELECT * FROM dmain WHERE genre_id = " + genre_id);
    }catch(SQLException e){
      e.printStackTrace();
    }
  }
  
  
  /**
   * 
   * TODO
   * 
   * @return music_id (0 if the next does not exist)
   */
  public int next(){
    try{
      // if the next does not exist
      if(!rs_dmain.next()){
        rs_dmain.close();
        return 0;
      }
      
      // get one row in dmain
      int music_id = rs_dmain.getInt("music_id");
      title = rs_dmain.getString("title");
      artist = rs_dmain.getString("artist");
      year = rs_dmain.getInt("year");//<<<
      int genre_id = rs_dmain.getInt("genre_id");
      
      Statement stmtlc = conn.createStatement();
      ResultSet rs;
      
      // from engenre by genre_id
      rs = stmtlc.executeQuery(
          "SELECT genre FROM engenre WHERE genre_id = " + genre_id);
      rs.next();
      genre = rs.getString("genre");
      rs.close();
      
      // from dafs by music_id
      rs = stmtlc.executeQuery(
          "SELECT artistfs FROM dafs WHERE music_id = " + music_id);
      artistfs = new ArrayList<>();
      while(rs.next())
        artistfs.add(rs.getString("artistfs"));
      rs.close();
      
      // from dtag by music_id
      rs = stmtlc.executeQuery(
          "SELECT tag FROM dtag WHERE music_id = " + music_id);
      tag = new ArrayList<>();
      while(rs.next())
        tag.add(rs.getString("tag"));
      rs.close();
      
      return music_id;
    
    }catch(SQLException e){
      e.printStackTrace();
      return 0;
    }
  }

  /**
   * Get title. Call this after next().
   * @return title
   */
  public String getTitle(){
    return title;
  }

  /**
   * Get artist. Call this after next().
   * @return artist
   */
  public String getArtist(){
    return artist;
  }

  /**
   * Get year. Call this after next().
   * @return year
   */
  public int getYear(){
    return year;
  }

  /**
   * Get genre. Call this after next().
   * @return genre
   */
  public String getGenre(){
    return genre;
  }
  
  /**
   * Get list of artistfs. Call this after next().
   * @return list of artistfs
   */
  public List<String> getArtistfs(){
    return artistfs;
  }

  /**
   * Get list of tag. Call this after next().
   * @return list of tag
   */
  public List<String> getTag(){
    return tag;
  }  
}
