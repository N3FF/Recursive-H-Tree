import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * This is the panel that the HTrees are drawn on and contains the recursive method for the HTrees
 * 
 * @author Justin
 *
 */
@SuppressWarnings("serial")
public class DrawPanel extends JPanel {

  private int treeDepth = 3; // Default Tree Depth
  private int delay = 0; // Used to set thread sleep time
  private Color color = Color.BLACK; // Default HTree Color

  /**
   * Creates a Double Buffered JPanel with a mouse listener
   */
  public DrawPanel() {
    this.setDoubleBuffered(true);
    this.setBackground(Color.WHITE);
    this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    this.addMouseListener(new MouseClick()); // Used to Get Cursor Location
  }

  private void createTree(Point p) {
    int offset = (int) ((this.getHeight() * .2) / 2);
    drawTree(p.x, p.y, offset); // Draws Main H
    _createTree(p.x, p.y, treeDepth - 1, offset);
  }

  private void _createTree(int x, int y, int treeDepth, int offset) {
    if (treeDepth > 0 && offset > 1) {
      drawTree(x - offset, y - offset, offset / 2); // TopLeft H
      drawTree(x - offset, y + offset, offset / 2); // BottomLeft H
      drawTree(x + offset, y + offset, offset / 2); // BottomRight H
      drawTree(x + offset, y - offset, offset / 2); // TopRight H

      try {
        Thread.sleep(this.delay);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      _createTree(x + offset, y + offset, treeDepth - 1, offset / 2); // BottomRight H's
      _createTree(x + offset, y - offset, treeDepth - 1, offset / 2); // TopRight H's
      _createTree(x - offset, y + offset, treeDepth - 1, offset / 2); // BottomLeft H's
      _createTree(x - offset, y - offset, treeDepth - 1, offset / 2); // TopLeft H's
    }
  }

  private void drawTree(int x, int y, int offset) {
    Graphics g = this.getGraphics();
    g.setColor(color); // Set Line Color

    g.drawLine(x - offset, y, x + offset, y); // Draw Center Line
    g.drawLine(x - offset, y + offset, x - offset, y - offset); // Draw Left Line
    g.drawLine(x + offset, y + offset, x + offset, y - offset); // Draw Right Line
  }

  /**
   * Set how deep the tree will recurse.
   * 
   * @param treeDepth The number of recursive calls
   */
  public void setTreeDepth(int treeDepth) {
    this.treeDepth = treeDepth; // Sets Depth From Slider
  }

  /**
   * Change the delay of the Thread Sleep
   * 
   * @param delay The duration of the sleep.
   */
  public void setDelay(int delay) {
    this.delay = delay * 25; // Sets Thread Sleep Duration
  }

  /**
   * Returns the JPanel for Drawing
   * 
   * @return This JPanel
   */
  public JPanel getPanel() {
    return this; // Returns This Panel to Main
  }

  /**
   * Changes the color of the HTree
   * 
   * @param color Sets the Color
   */
  public void setColor(Color color) {
    this.color = color; // Sets Color From ComboBox and MenuItem
  }

  private class MouseClick extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
      super.mouseClicked(e);
      createTree(e.getPoint()); // Gets Mouse Click Point and Starts Recursive Call
    }
  }
}

