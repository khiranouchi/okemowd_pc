package okemowd.view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * Main frame of this project.
 * This frame contains some panels and panes. <br>
 * 
 * TODO how panel layouted with @link
 * 
 */
@SuppressWarnings("serial")
public class FrameMain extends JFrame{
  
  /*
   * Create main frame and add all panels and panes in it.
   */
  public FrameMain(){
    setTitle("OkeMowd Desktop Application");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1200, 720);//kari

    // panels and panes
    PaneFilter pfilter = new PaneFilter();
    PaneList plist = new PaneList();
    PanelInput pinput = new PanelInput();

    JSplitPane pall = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    JPanel pright = new JPanel();
    pright.setLayout(new BoxLayout(pright, BoxLayout.Y_AXIS));
    pright.add(plist);
    pright.add(pinput);

    pall.setLeftComponent(pfilter);
    pall.setRightComponent(pright);
    pall.setDividerSize(4);//<<<
    
    add(pall);
    
  }

  public static void main(String[] args){
    FrameMain frame = new FrameMain();
    frame.setVisible(true);
  }
}
