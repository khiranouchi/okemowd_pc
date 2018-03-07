package okemowd.controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JComponent;

/**
 * TODO
 */
public class InputFocusListener implements FocusListener{

  @Override
  public void focusGained(FocusEvent e){
  }

  @Override
  public void focusLost(FocusEvent e){
    
    System.out.println("[InputFocusListener/focusLost]start");
    
    String compname = ((JComponent)e.getSource()).getName();
    if(compname.equals("panelinput.tfartistfs")){
      System.out.println("tfartistfs");
      
      
//    }else if(compname.equals("panelinput.tfartistfsr")){
//      System.out.println("tfartistfsr");
      
      
    }else if(compname.equals("panelinput.tftag")){
      System.out.println("tftag");
      
      
    }
    
    
    
    // TODO

    
    System.out.println("[InputFocusListener/focusLost]end");
  }

}
