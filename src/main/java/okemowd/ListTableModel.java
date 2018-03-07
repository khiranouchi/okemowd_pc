package okemowd;

import javax.swing.table.DefaultTableModel;

public class ListTableModel{
  
  
  //kari
  public ListTableModel(){
    String columns[] = {"id", "Title", "Artist", "Artist(fs)", "Date", "Genre", "Tag"};
    String default_tabledata[][] = {
        {"1001", "title1", "artist1", "afs11, afs12, afs13", "2017/1/4", "Anime", ""},
        {"1009", "title2", "", "afs2", "2018", "Others", "tag21, tag22, tag23"},
        {"1003", "title3", "artist3", "afs3", "", "Game", "tag3"}
    };    
    model = new DefaultTableModel(default_tabledata, columns);
    
    
  }

  /**
   * TODO
   */
  private DefaultTableModel model = new DefaultTableModel();
  
  /*
   * TODO
   */
  public DefaultTableModel getModel(){
    return model;
  }
  
  
  
  
}
