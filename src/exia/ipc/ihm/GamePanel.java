package exia.ipc.ihm;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
   private static final long serialVersionUID = 1L;
   private Dimension dim = new Dimension(587, 324);
   private Image imgA = (new ImageIcon(GamePanel.class.getResource("/exia/ipc/ihm/res/BackgroundA.png"))).getImage();

   public void paint(Graphics g) {
      g.drawImage(this.imgA, 0, 0, this.getWidth(), this.getHeight(), (ImageObserver)null);
      super.paint(g);
   }

   public double getRatioX() {
      return (double)this.getWidth() / this.dim.getWidth();
   }

   public double getRatioY() {
      return (double)this.getHeight() / this.dim.getHeight();
   }

   public Dimension getMinimumSize() {
      return this.dim;
   }

   public Dimension getPreferredSize() {
      return this.dim;
   }

   public Point applyRatio(Point location) {
      return new Point((int)((double)location.x * this.getRatioX()), (int)((double)location.y * this.getRatioY()));
   }

   public List applyRatio(List route) {
      List out = new ArrayList();
      Iterator var4 = route.iterator();

      while(var4.hasNext()) {
         Point p = (Point)var4.next();
         out.add(this.applyRatio(p));
      }

      return out;
   }
}
