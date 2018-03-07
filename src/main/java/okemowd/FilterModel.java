package okemowd;

import javax.swing.DefaultListModel;

public class FilterModel{
  
  //kari
  public FilterModel(){
    modelArtistfs.addElement("artistfs1");
    modelArtistfs.addElement("artistfs2");
    modelArtistfs.addElement("artistfs3");
  }
  
  
  

  /*
   * The model which represents the elements(Artist(fs)) in this list.
   * When elements are updated, this is updated and set to the list.
   * TODO
   */
  private DefaultListModel<String> modelArtistfs = new DefaultListModel<String>();

  /*
   * TODO
   */
  public DefaultListModel<String> getModelArtistfs(){
    return modelArtistfs;
  }
  

}
