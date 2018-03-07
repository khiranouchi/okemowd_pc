package okemowd.view;

import javax.swing.JList;

import okemowd.FilterModel;
import okemowd.controller.FilterMouseListener;

/**
 * This class is the list of one of the filter of musics.
 * The filter result may be shown in {@linkplain PaneList}. <br>
 * The mouse listener of this class is {@linkplain FilterMouseListener}. <br>
 * Extend this class and <<<
 * 
 * TODO
 * 
 */
@SuppressWarnings("serial")
public abstract class FilterAbstract extends JList<String>{
  
  ///<<<
  private FilterModel model;
  
  /**
   * TODO
   */
  public FilterAbstract(){
    
    addMouseListener(new FilterMouseListener());//<controler
    
    model = new FilterModel();//<<<<<< not here <<<<<<
    setModel(model.getModelArtistfs());//model
    
    
    
  }
  
  /**
   * (update the view. eg. this is called when the new music added by input)
   * TODO
   */
  public void updateList(){
    
    setModel(model.getModelArtistfs());//update
    
    // repaint ?
    
  }
  

}
