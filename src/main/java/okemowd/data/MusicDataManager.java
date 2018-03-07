package okemowd.data;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is to manage music database.
 * You can store musics' data and get info of them. <br>
 * You can use this class several times and there is initialization only first time.
 * 
 * TODO
 * <<< stmt is all same in program
 * 
 */
public class MusicDataManager{
  
  static private boolean isAlreadySetup = false; 
  static private Connection conn;
  static private Statement stmt;
  static private final String dbname = "musicdata.db";
  
  /**
   * Initialize database and create user's personal genre list (only first time).
   * (Connect to SQLite database and create necessary tables if not exist)
   */
  public MusicDataManager(){
    // only first time
    if(!isAlreadySetup){
      try{
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbname);
        stmt = conn.createStatement();
        createTableIfNotExist();
        createGenresIfNotExist();//<<<<<<not here(because not every time this need)TODO<<<
        isAlreadySetup = true;
      }catch(ClassNotFoundException | SQLException e){
        e.printStackTrace();
      }
    }
  }
  
  
  /**
   * Add new music data.
   * @param title
   * @param artist null is acceptable
   * @param year null(0) is acceptable
   * @param genre_id genre_id predetermined in the database
   * @param artistfs
   * @param artistfsr this size must be equal to artistfs 
   * @param tag
   */
  public void addMusic(String title, String artist, int year, int genre_id,
                       List<String> artistfs, List<String> artistfsr, List<String> tag){
    try{
      // enafsr )
      for(int i = 0 ; i < artistfs.size() ; i++){
        // insert new artistfs and artistfsr IF NOT ALREADY EXIST
        stmt.executeUpdate("INSERT OR IGNORE INTO enafsr VALUES("
                         + "'" + artistfs.get(i) + "', "
                         + "'" + artistfsr.get(i) + "')");
      }
      
      // dmain
      stmt.executeUpdate("INSERT INTO dmain(title,artist,year,genre_id) VALUES(" //abbreviate music_id
                       + "'" + title + "', "
                       + "'" + artist + "', "
                       + year + ", "
                       + genre_id + ")");
      ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid() AS last_music_id");
      int music_id = rs.getInt("last_music_id");
      rs.close();
      
      // dafs
      for(String afs : artistfs){
        stmt.executeUpdate("INSERT INTO dafs(music_id,artistfs) VALUES(" //abbreviate id
                         + music_id + ", "
                         + "'" + afs + "')");
      }
      
      // dtag
      for(String t : tag){
        stmt.executeUpdate("INSERT INTO dtag(music_id,tag) VALUES(" //abbreviate id
                         + music_id + ", "
                         + "'" + t + "')");
      }
      
    }catch(SQLException e){
      e.printStackTrace();
    }
  }
  
  
  /**
   * Get all artistfs in the database.
   * @deprecated
   * @return artistfs
   */
  public List<String> getListArtistfs(){
    List<String> artistfs = new ArrayList<>();
    try{
      ResultSet rs = stmt.executeQuery("SELECT artistfs FROM enafsr");
      while(rs.next()) artistfs.add(rs.getString("artistfs"));
    }catch(SQLException e){
      e.printStackTrace();
    }
    return artistfs;
  }
  
  /**
   * Get all tags in the database.
   * @return tags
   */
  public List<String> getListTag(){
    List<String> tag = new ArrayList<>();
    try{
      ResultSet rs = stmt.executeQuery("SELECT DISTINCT tag FROM dtag");
      while(rs.next()) tag.add(rs.getString("tag"));
    }catch(SQLException e){
      e.printStackTrace();
    }
    return tag;
  }

  /**
   * Get all years in the database.
   * @return years
   */
  public List<Integer> getListYear(){
    List<Integer> year = new ArrayList<>();
    try{
      ResultSet rs = stmt.executeQuery("SELECT DISTINCT year FROM dmain");
      while(rs.next()) year.add(rs.getInt("year"));
    }catch(SQLException e){
      e.printStackTrace();
    }
    return year;
  }  

  /**
   * Get all genre_id and genre in the database.
   * @param buf_genre_id genre_ids are returned here
   * @param buf_genre genres are returned here
   */
  public void getListGenre(List<Integer> buf_genre_id, List<String> buf_genre){
    try{
      ResultSet rs = stmt.executeQuery("SELECT * FROM engenre");
      while(rs.next()){
        buf_genre_id.add(rs.getInt("genre_id"));
        buf_genre.add(rs.getString("genre"));
      }
    }catch(SQLException e){
      e.printStackTrace();
    }
  }
  
  
  /**
   * Get genre_id from the potential genre string roughly.
   * The genre string can be a little different from actual one
   * (eg. "J-POP" can be "jpop" or "JPop").
   * @param genre potential genre string
   * @return genre_id (0 if not match any genre)
   */
  public int getGenreIdRoughly(String genre){
    return getGenreIdRoughlyPv(genre);
  }
  
  /**
   * Get artistfsr of artistfs.
   * Return null if the artistfs is not exist in the database. 
   * @param artistfs
   * @return artistfsr (null if not exist in the database)
   */
  public String getRubyOfArtistfs(String artistfs){
    try{
      ResultSet rs = stmt.executeQuery(
          "SELECT artistfsr FROM enafsr WHERE artistfs = '" + artistfs + "'");
      if(rs.next())//if exist
        return rs.getString("artistfsr");
    }catch(SQLException e){
      e.printStackTrace();
    }
    return null;
  }
  
  /**
   * Get if the artistfs exists or not in the database.
   * @param artistfs
   * @return if exist in the database
   */
  public boolean isExistArtistfs(String artistfs){
    try{
      return stmt.executeQuery(
             "SELECT artistfs FROM enafsr WHERE artistfs = '" + artistfs + "'").next();
    }catch(SQLException e){
      e.printStackTrace();
      return false;
    }
  }
  
  
  /***************************************************************************/
  
  
  Connection getConn(){
    return conn;
  }
  
  
  /***************************************************************************/
  
  
  /**
   * Create the tables needed if they are not exist. <br>
   * 
   * 
   * TODO tables
   * 
   * 
   */
  private void createTableIfNotExist() throws SQLException {
    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS dmain ("
        + "music_id INTEGER NOT NULL PRIMARY KEY, "//AUTO
        + "title    TEXT    NOT NULL, "
        + "artist   TEXT, "
        + "year     INTEGER, "
        + "genre_id INTEGER REFERENCES engenre(genre_id));"
        + "CREATE INDEX IF NOT EXISTS id_dmain ON dmain (year, genre_id)");
    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS dafs ("
        + "id       INTEGER NOT NULL PRIMARY KEY, "//AUTO
        + "music_id INTEGER NOT NULL REFERENCES dmain(music_id), "
        + "artistfs TEXT    NOT NULL REFERENCES enafsr(artistfs));"
        + "CREATE INDEX IF NOT EXISTS id_dafs ON dafs (music_id, artistfs)");
    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS dtag ("
        + "id       INTEGER NOT NULL PRIMARY KEY, "//AUTO
        + "music_id INTEGER NOT NULL REFERENCES dmain(music_id), "
        + "tag      TEXT    NOT NULL);"
        + "CREATE INDEX IF NOT EXISTS id_dtag ON dtag (music_id, tag)");
    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS enafsr ("
        + "artistfs   TEXT  NOT NULL PRIMARY KEY, "
        + "artistfsr  TEXT  NOT NULL)");
    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS engenre ("
        + "genre_id INTEGER NOT NULL PRIMARY KEY, "
        + "genre    TEXT    NOT NULL);"
        + "CREATE INDEX IF NOT EXISTS id_engenre ON engenre (genre)");
  }
  
  /**
   * Create user's personal genres in the genre table.
   * 
   * TODO
   * by user determined file<<<
   * 
   */
  private void createGenresIfNotExist() throws SQLException {
    // 0 is "Others"(null) <<<
    stmt.executeUpdate("INSERT OR IGNORE INTO engenre VALUES(1, 'Anime')");
    stmt.executeUpdate("INSERt OR IGNORE INTO engenre VALUES(2, 'Game')");
    stmt.executeUpdate("INSERT OR IGNORE INTO engenre VALUES(3, 'Doujin')");
    stmt.executeUpdate("INSERT OR IGNORE INTO engenre VALUES(4, 'J-POP')");
    stmt.executeUpdate("INSERT OR IGNORE INTO engenre VALUES(5, 'Vocaloid')");
    
    // this should be got from user's personal data file TODO<<<
    
  }
  
  /**
   * Get genre_id from the potential genre string roughly.
   * 
   * TODO
   * by user determined file<<<
   * 
   * @param genre potential genre string
   * @return genre_id (0 if not match any genre)
   */
  private int getGenreIdRoughlyPv(String genre){
    
    switch(genre){
    case "Anime": case "anime": case "アニメ":
      return 1;
    case "Game": case "game": case "ゲーム": case "ゲームミュージック":
      return 2;
    case "Doujin": case "doujin": case "同人音楽": case "東方アレンジ":
      return 3;
    case "J-POP": case "JPOP": case "JPop": case "Jポップ":
      return 4;
    case "Vocaloid": case "vocaloid": case "ボーカロイド":
      return 5;
    default:
      return 0;
    }
    
    // this also should be got from user's personal data file TODO<<<
    
  }
  
  
  
  
  
  
  //
  // fot test
  //
  public static void main(String[] args){
    MusicDataManager mdm = new MusicDataManager();
    
    /*List<String> afs1 = new ArrayList<>();
    afs1.add("afs11"); afs1.add("afs12"); afs1.add("afs13");
    List<String> afsr1 = new ArrayList<>();
    afsr1.add("afsr11"); afsr1.add("afsr12"); afsr1.add("afsr13");
    List<String> tag1 = new ArrayList<>();
    tag1.add("tag11"); tag1.add("tag12");
    mdm.addMusic("title1", "artist1", 2001, 3, afs1, afsr1, tag1);
    
    List<String> afs2 = new ArrayList<>();
    afs2.add("afs21"); afs2.add("afs13");
    List<String> afsr2 = new ArrayList<>();
    afsr2.add("afsr21"); afsr2.add("afsr13");
    List<String> tag2 = new ArrayList<>();
    mdm.addMusic("aiueo2", null, 1112, 2, afs2, afsr2, tag2);
    
    List<String> afs3 = new ArrayList<>();
    List<String> afsr3 = new ArrayList<>();
    List<String> tag3 = new ArrayList<>();
    tag3.add("tag3"); tag3.add("tag12"); tag3.add("tag3k"); tag3.add("tag11");
    mdm.addMusic("kd3", "artist3", 1993, 1, afs3, afsr3, tag3);*/
    
    /*List<String> list_artistfs = mdm.getListArtistfs();
    List<String> list_tag = mdm.getListTag();
    List<Integer> list_year = mdm.getListYear();
    List<Integer> list_genre_id = new ArrayList<>();
    List<String> list_genre = new ArrayList<>();
    mdm.getListGenre(list_genre_id, list_genre);
    System.out.println(list_artistfs);
    System.out.println(list_tag);
    System.out.println(list_year);
    System.out.println(list_genre_id);
    System.out.println(list_genre);
    
    for(String artistfs : list_artistfs){
      System.out.println("[ " + artistfs);
      System.out.println(mdm.isExistArtistfs(artistfs));
      System.out.println(mdm.getRubyOfArtistfs(artistfs));
    }
    System.out.println("[ xxxnai");
    System.out.println(mdm.isExistArtistfs("xxxnai"));
    System.out.println(mdm.getRubyOfArtistfs("xxxnai"));*/
    
    ExtractorMusic em = new ExtractorMusic(mdm);
    em.startByArtistfs("afs13");
    while(true){
      int music_id = em.next();
      if(music_id == 0) break;
      System.out.println("[" + em.getTitle() + "] " + em.getArtist()
                       + " / " + em.getYear() + " / " + em.getGenre());
      for(String artistfs : em.getArtistfs()) System.out.print(artistfs + " ");
      System.out.println();
      for(String tag : em.getTag()) System.out.print(tag + " ");
      System.out.println();
    }
    
    
    
  }
  
  
}
