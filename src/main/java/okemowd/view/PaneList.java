package okemowd.view;

import javax.swing.JScrollPane;

/**
 * The panel which has music list table {@linkplain ListTable}.
 */
@SuppressWarnings("serial")
public class PaneList extends JScrollPane{
  
  /**
   * Create this panel and add music list table {@linkplain ListTable}.
   */
  public PaneList(){
    setViewportView(new ListTable());
  }
}
