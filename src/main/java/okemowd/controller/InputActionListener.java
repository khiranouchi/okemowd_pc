package okemowd.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;

import okemowd.view.PanelInput;

/**
 * TODO
 */
public class InputActionListener implements ActionListener{
  
  /**
   * View instance which have instance observed by this handler.
   */
  private PanelInput viewInstance;
  
  public InputActionListener(PanelInput viewInstance){
    this.viewInstance = viewInstance;
  }

  @Override
  public void actionPerformed(ActionEvent e){
    
    System.out.println("[InputActionListener/actionPerformed]start");
        
    if(((JComponent)e.getSource()).getName().equals("panelinput.btnadd")){
      System.out.println("btnadd");
      
      // tftitle.getText()
      // tfartist.getText()
      // for tfartistfs[i].getText()
      // for tfartistfsr[i].getText()
      // spdate.xxx()
      // cbgenre.xxx()
      // tftag.getText()
      
      
    }
    
    
    
    
    // TODO

    
    
    System.out.println("[InputActionListener/actionPerformed]end");
    
  }

}
