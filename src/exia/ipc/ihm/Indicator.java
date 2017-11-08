package exia.ipc.ihm;

import exia.ipc.entities.IndicatorListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Indicator extends JLabel implements IndicatorListener {
   private static final long serialVersionUID = 1L;
   private int value = 0;
   private Dimension dimMax = new Dimension(20, 35);
   private Dimension dimMin = new Dimension(25, 20);
   private Point location;

   public Indicator(Point location) {
      this.location = location;
   }

   public Point getDefaultLocation() {
      return this.location;
   }

   public void paint(Graphics g) {
      g.setColor(Color.RED);
      g.fillRect(0, 0, this.getWidth(), this.getHeight());
      g.setColor(Color.WHITE);
      g.drawString("" + this.value, this.value > 9 ? 1 : 5, 11);
      g.setColor(Color.BLACK);
      g.drawLine(0, 0, this.getWidth(), 0);
      g.drawLine(0, 0, 0, this.getHeight());
      g.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this.getHeight());
      g.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, this.getHeight() - 1);
   }

   public void reset() {
      this.value = 0;
      this.invalidate();
   }

   public void setValue(int value) {
      this.value = value;
      this.invalidate();
   }

   public Dimension getPreferredSize() {
      return this.value > 9 ? this.dimMax : this.dimMin;
   }

   public Dimension getMinimumSize() {
      return this.dimMin;
   }

   public Dimension getMaximumSize() {
      return this.dimMax;
   }

   public void notifyChange(int value) {
      this.setValue(value);
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            Indicator.this.repaint();
         }
      });
   }
}
