package okemowd.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

import okemowd.tag.Mp3tagReader;
import okemowd.view.PanelInput;

/**
 * TODO
 */
@SuppressWarnings("serial")
public class Mp3dropImportHandler extends TransferHandler{

  /**
   * Instance which register this handler.
   */
  private JComponent viewInstance;
  
  /**
   * Create handler. <br>
   * 
   * TODO
   * viewinstance must have
   * updateInputedData(String title, String artist, int year, String genre)
   * 
   * @param viewInstance instance which register this handler
   */
  public Mp3dropImportHandler(JComponent viewInstance){
    this.viewInstance = viewInstance;
  }  
  
  @Override
  public boolean canImport(TransferSupport support){
    // if dropped data is file list
    if(support.isDrop()
    && support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
      System.out.println("[Mp3dropImportHandler/canImport] return true");//log<<<
      return true;
    }
    System.out.println("[Mp3dropImportHandler/canImport] return false");//log<<<
    return false;
  }
  
  @Override
  public boolean importData(TransferSupport support){
    if(!canImport(support))
      return false;
    
    try{
      // get File
      Transferable t = support.getTransferable();
      List<File> files = (List<File>)t.getTransferData(DataFlavor.javaFileListFlavor);
      File mp3file = files.get(0);

      System.out.println("[Mp3dropImportHandler/importData] file: " + mp3file.toString());//log<<<
      
      // get mp3file info from tag
      Mp3tagReader mp3tagreader = new Mp3tagReader(mp3file);
      String title = mp3tagreader.getTitle();
      String artist = mp3tagreader.getArtist();
      String artistr = mp3tagreader.getArtistRuby();
      int year = mp3tagreader.getYear();
      String genre = mp3tagreader.getGenre();
      
      // update inputed data <<<(PanelInput<<<)
      ((PanelInput)viewInstance).updateInputedData(title, artist, artistr, year, genre);
    
    }catch(UnsupportedFlavorException | IOException e){
      e.printStackTrace();
    }

    return true;
  }
}
