package okemowd.view;

import java.util.Calendar;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import okemowd.controller.InputActionListener;
import okemowd.controller.InputFocusListener;
import okemowd.controller.Mp3dropImportHandler;

/**
 * TODO
 */
@SuppressWarnings("serial")
public class PanelInput extends JPanel{
  
  private JTextField tftitle;
  private JTextField tfartist;
  private JTextField tfartistfs;
  private JTextField tfartistfsr;
  private JSpinner spdate;
  private JComboBox<String> cbgenre;
  private JTextField tftag;
  
  /**
   * TODO
   */
  public PanelInput(){

    tftitle = new JTextField();
    tftitle.setColumns(10);
    tfartist = new JTextField();
    tfartist.setColumns(10);
    
    tfartistfs = new JTextField();
    tfartistfs.setColumns(5);
    tfartistfs.setName("panelinput.tfartistfs");
    tfartistfs.addFocusListener(new InputFocusListener());
    tfartistfsr = new JTextField();
    tfartistfsr.setColumns(5);

    SpinnerDateModel spdate_model = new SpinnerDateModel();
    spdate_model.setCalendarField(Calendar.YEAR);
    spdate = new JSpinner(spdate_model);
    JSpinner.DateEditor spdate_editor = new JSpinner.DateEditor(spdate, "yyyy");
    spdate.setEditor(spdate_editor);
    
    String cbgenre_data[] = {"g1", "g2", "g3"};//<<<TODO
    cbgenre = new JComboBox<>(cbgenre_data);//<<<(String<<<)
    
    tftag = new JTextField();
    tftag.setColumns(5);
    tftag.setName("panelinput.tftag");
    tftag.addFocusListener(new InputFocusListener());
        
    JButton btnadd = new JButton("Add");
    btnadd.setName("panelinput.btnadd");
    btnadd.addActionListener(new InputActionListener(this));
    //btnadd.addActionListener(new InputActionListener(btnadd));

    
    /* define layout ***********************************************/
    
    setLayout(new GridLayout(8, 1));
    
    JPanel ptitle = new JPanel();
    JPanel partist = new JPanel();
    JPanel partistfs = new JPanel();
    JPanel partistfsr = new JPanel();
    JPanel pdate = new JPanel();
    JPanel pgenre = new JPanel();
    JPanel ptag = new JPanel();
    JPanel pbtn = new JPanel();

    ptitle.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
    partist.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
    partistfs.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
    partistfsr.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
    pdate.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
    pgenre.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
    ptag.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
    pbtn.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));

    ptitle.add(new Label("Title"));
    ptitle.add(tftitle);
    partist.add(new Label("Artist"));
    partist.add(tfartist);
    partistfs.add(new Label("Artist(fs)"));
    partistfs.add(tfartistfs);
    partistfsr.add(new Label("Artist(fs)'s ruby"));
    partistfsr.add(tfartistfsr);
    pdate.add(new Label("Date"));
    pdate.add(spdate);
    pgenre.add(new Label("Genre"));
    pgenre.add(cbgenre);
    ptag.add(new Label("Tag"));
    ptag.add(tftag);
    pbtn.add(btnadd);

    add(ptitle);
    add(partist);
    add(partistfs);
    add(partistfsr);
    add(pdate);
    add(pgenre);
    add(ptag);
    add(pbtn);
    
    
    /* mp3-file drop ***********************************************/
    
    setTransferHandler(new Mp3dropImportHandler(this));
  
  }
  
  
  /**
   * (update inputed data. eg. when mp3file dropped)
   * 
   * TODO
   * 
   * @param title "Title" text
   * @param artist "Artist" and "Artist(fs) text
   * @param artistr ruby of "Artist"
   * @param year "Date" text
   * @param genre "Genre" combobox (become "Others" if not exist in genre list)
   */
  public void updateInputedData(String title, String artist, String artistr,
                                int year, String genre){
    tftitle.setText(title);
    tfartist.setText(artist);
    
    tfartistfs.setText(artist);
    // TODO create another textfield(fs)
    tfartistfsr.setText(artistr);
    // TODO create another textfield(fsr)
    
    Calendar cal = Calendar.getInstance();
    cal.set(year, 0, 1);
    spdate.setValue(cal.getTime());
    
    cbgenre.setSelectedIndex(1);//TODO<<<<<<
    
  }

  
}
