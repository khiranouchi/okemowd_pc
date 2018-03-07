package okemowd.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JList;

import okemowd.view.FilterArtistfs;
import okemowd.view.PaneList;

/**
 * This class is mouse listener of the filter list {@linkplain FilterAbstract}.
 * When you double-click an element of the list,
 * the filter result is shown in {@linkplain PaneList}. <<<
 * 
 * TODO
 * 
 */
public class FilterMouseListener implements MouseListener{

  @Override
  public void mouseClicked(MouseEvent e){
    if (e.getClickCount() == 2) {
      Object src = e.getSource();
      int index = ((JList<String>)e.getSource()).locationToIndex(e.getPoint());
      System.out.println("Double clicked on Item " + index);

      if(src instanceof FilterArtistfs){
        System.out.println("FilterArtistfs");
        
      }else{
        System.out.println("Others");
        
      }

      // TODO
      // get music id or something like that, from "index"
      
      // TODO
      // update the "model" of "PanelList's table"
      
    }
  }

  @Override
  public void mouseEntered(MouseEvent e){
  }

  @Override
  public void mouseExited(MouseEvent e){
  }

  @Override
  public void mousePressed(MouseEvent e){
  }

  @Override
  public void mouseReleased(MouseEvent e){
  }
}
