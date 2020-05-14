import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * This module is on swing and recursion. A JFrame is created to allow on click events to
 * recursively draw H Trees. UI Functionalities like color changing, tree depth, delay, and clear
 * are implemented for users to play around with while creating H Trees
 * 
 * @author Justin
 *
 */
@SuppressWarnings("serial")
public class Default extends JFrame {

  private static ImageIcon icon = new ImageIcon("res\\windowIcon.png");

  // Setup Window Resolution Based on Client's Desktop Resolution
  private static GraphicsDevice gd =
      GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
  private static final int MONITOR_WIDTH = gd.getDisplayMode().getWidth();
  private static final int MONITOR_HEIGHT = gd.getDisplayMode().getHeight();
  private static final Dimension DEFAULT_WINDOW_SIZE =
      new Dimension(MONITOR_WIDTH / 2, MONITOR_HEIGHT / 2);
  private static final Dimension MIN_WINDOW_SIZE = new Dimension(400, 400);
  // End Window Resolution Setup

  private static DrawPanel paintPanel = new DrawPanel(); // The Panel used to Draw
  private static JComboBox<String> comboColor = null; // Combobox to change colors

  public Default() {

    Container frame = this.getContentPane();

    // JMenu - Create Widgets
    JMenuBar menuBar = new JMenuBar();
    JMenu menuFile = new JMenu("File");
    JMenu menuColor = new JMenu("Color");
    JMenuItem miExit = new JMenuItem("Exit");
    JMenuItem miClear = new JMenuItem("Clear");
    JMenuItem miRed = new JMenuItem("Red");
    JMenuItem miGreen = new JMenuItem("Green");
    JMenuItem miBlue = new JMenuItem("Blue");
    JMenuItem miBlack = new JMenuItem("Black");
    JMenuItem miPink = new JMenuItem("Pink");

    // JMenu - Settings
    miExit.addActionListener((e) -> exitProgram());
    miClear.addActionListener((e) -> clearDraw());
    miBlack.addActionListener(new ColorChange());
    miRed.addActionListener(new ColorChange());
    miGreen.addActionListener(new ColorChange());
    miBlue.addActionListener(new ColorChange());
    miPink.addActionListener(new ColorChange());

    // JMenu - Build
    menuBar.add(menuFile);
    menuBar.add(menuColor);
    menuFile.add(miClear);
    menuFile.add(miExit);
    menuColor.add(miBlack);
    menuColor.add(miRed);
    menuColor.add(miGreen);
    menuColor.add(miBlue);
    menuColor.add(miPink);

    // JPanels
    JPanel drawPanel = paintPanel.getPanel();
    JPanel panMenu = new JPanel();
    panMenu.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

    // Right Menu GridBag Settings
    GridBagLayout gbLayout = new GridBagLayout();
    gbLayout.columnWidths = new int[] {15, 89, 15, 0};
    gbLayout.rowHeights = new int[] {0, 0, 0, 0, 0, 23, 0};
    gbLayout.columnWeights = new double[] {0.0, 1.0, 0.0, Double.MIN_VALUE};
    gbLayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
    panMenu.setLayout(gbLayout);

    GridBagConstraints gbcTreeSize = new GridBagConstraints(); // Tree Size Label
    gbcTreeSize.insets = new Insets(25, 0, 5, 5);
    gbcTreeSize.gridx = 1;
    gbcTreeSize.gridy = 0;

    GridBagConstraints gbcTreeSizeSlider = new GridBagConstraints(); // Tree Size Slider
    gbcTreeSizeSlider.insets = new Insets(0, 0, 5, 5);
    gbcTreeSizeSlider.gridx = 1;
    gbcTreeSizeSlider.gridy = 1;

    GridBagConstraints gbcDelay = new GridBagConstraints(); // Delay Label
    gbcDelay.insets = new Insets(0, 0, 5, 5);
    gbcDelay.gridx = 1;
    gbcDelay.gridy = 2;

    GridBagConstraints gbcDelaySlider = new GridBagConstraints(); // Delay Slider
    gbcDelaySlider.insets = new Insets(0, 0, 5, 5);
    gbcDelaySlider.gridx = 1;
    gbcDelaySlider.gridy = 3;

    GridBagConstraints gbcColor = new GridBagConstraints(); // Color Label
    gbcColor.insets = new Insets(15, 0, 5, 5);
    gbcColor.gridx = 1;
    gbcColor.gridy = 4;

    GridBagConstraints gbcComboColor = new GridBagConstraints(); // Combo Box
    gbcComboColor.insets = new Insets(0, 0, 5, 5);
    gbcComboColor.fill = GridBagConstraints.HORIZONTAL;
    gbcComboColor.gridx = 1;
    gbcComboColor.gridy = 5;

    // Side Menu Widgets
    JSlider slideSize = new JSlider(2, 6, 3); // Tree size Slider
    slideSize.setPaintTicks(true);
    slideSize.setMajorTickSpacing(2);
    slideSize.setMinorTickSpacing(1);
    slideSize.setPaintLabels(true);
    slideSize.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        paintPanel.setTreeDepth(slideSize.getValue());
      }
    });

    JSlider slideDelay = new JSlider(0, 4, 0); // Tree Draw Delay
    slideDelay.setPaintTicks(true);
    slideDelay.setMajorTickSpacing(1);
    slideDelay.setPaintLabels(true);
    slideDelay.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        paintPanel.setDelay(slideDelay.getValue());
      }
    });

    comboColor = new JComboBox<String>(new String[] {"Black", "Red", "Green", "Blue", "Pink"});
    comboColor.addActionListener(new ColorChange());

    // Window Properties
    this.setSize(DEFAULT_WINDOW_SIZE);
    this.setLocationRelativeTo(null); // Centers Window on Default Monitor
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setIconImage(icon.getImage()); // H-Tree Icon for window and task
    this.setTitle("Recursive H Tree");
    this.setMinimumSize(MIN_WINDOW_SIZE);
    this.setLayout(new BorderLayout(0, 0));

    // Build Window
    this.setJMenuBar(menuBar);
    frame.setLayout(new BorderLayout());
    frame.add(drawPanel, BorderLayout.CENTER);
    frame.add(panMenu, BorderLayout.EAST);
    panMenu.add(new JLabel("Tree Depth"), gbcTreeSize);
    panMenu.add(slideSize, gbcTreeSizeSlider);
    panMenu.add(new JLabel("Draw Delay"), gbcDelay);
    panMenu.add(slideDelay, gbcDelaySlider);
    panMenu.add(new JLabel("Change Color"), gbcColor);
    panMenu.add(comboColor, gbcComboColor);
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {

      @Override
      public void run() {
        Default window = new Default(); // Builds the window
        window.setVisible(true); // Once it's completed make it visible
      }
    });
  }

  private void exitProgram() {
    System.exit(0);
  }

  // Clear drawing panel
  private void clearDraw() {
    paintPanel.repaint();
  }

  // Handles Color Changes for the MenuItems and ComboBox
  private class ColorChange implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      String color = "";
      if (e.getSource() instanceof JComboBox) { // Did the ComboBox Call This?
        @SuppressWarnings("unchecked")
        JComboBox<String> combo = (JComboBox<String>) e.getSource();
        color = combo.getSelectedItem().toString(); // Get Color Text

      } else if (e.getSource() instanceof JMenuItem) { // Did the MenuItem Call This?
        JMenuItem menuItem = (JMenuItem) e.getSource();
        color = menuItem.getText(); // Get Color Text
      } else {
        return;
      }

      switch (color) { // Uses the color text from the sender to set the color
        case "Black":
          paintPanel.setColor(Color.BLACK);
          break;
        case "Red":
          paintPanel.setColor(Color.RED);
          break;
        case "Green":
          paintPanel.setColor(Color.GREEN);
          break;
        case "Blue":
          paintPanel.setColor(Color.BLUE);
          break;
        case "Pink":
          paintPanel.setColor(Color.PINK);
          break;
      }
      if (!color.isEmpty()) {
        comboColor.setSelectedItem(color); // Sync the Combo box with MenuItem
      }
    }
  }
}
