package okemowd.view;

import javax.swing.JTable;

import okemowd.ListTableModel;

/**
 * This class is the table of music list.
 * Music list may be the result of filtering by the filter {@linkplain FilterAbstract}. <br>
 * 
 * TODO
 */
@SuppressWarnings("serial")
public class ListTable extends JTable{
  
  ///<<<
  private ListTableModel model;
  
  /**
   * TODO
   */
  public ListTable(){
    
    model = new ListTableModel();//<<<
    setModel(model.getModel());//<<<
    
  }
  
  /**
   * (update the view. eg. this is called when filtered by filter)
   * TODO
   */
  public void updateTable(){
    
    // TODO
    
  }
  

}
