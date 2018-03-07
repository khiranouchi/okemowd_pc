package okemowd.view;

import javax.swing.JTabbedPane;

/**
 * The panel which has several tabs.
 * Each tab is the filter of musics
 * to narrow down the result of {@linkplain PaneList}. <br>
 * 
 * TODO what kind of tab there are with @link
 * 
 */
@SuppressWarnings("serial")
public class PaneFilter extends JTabbedPane{
  
  /**
   * Create this panel and add tabs.
   */
  PaneFilter(){
    FilterArtistfs fafs = new FilterArtistfs();
    
    
    addTab("Artist(fs)", fafs);
    addTab("Date", null);//kari
    addTab("Genre", null);//kari
    addTab("Tag", null);//kari
    
    
  }
  
  
  
}
