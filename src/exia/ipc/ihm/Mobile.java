package exia.ipc.ihm;

import exia.ipc.entities.Node;
import exia.ipc.entities.Product;
import exia.ipc.entities.PrositIPC;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.TimelineScenario;
import org.pushingpixels.trident.TimelineScenario.Sequence;

public class Mobile extends JLabel {
   private static final long serialVersionUID = 1L;
   /** @deprecated */
   private static final int STEP = 14;
   /** @deprecated */
   private boolean reached = false;
   /** @deprecated */
   private List route;
   /** @deprecated */
   private Point next;
   private Sequence scenario;

   public Mobile(GamePanel panel) {
      this.setSize(45, 34);
      this.setLocation(panel.applyRatio(new Point(472, 185)));
      this.setIcon(new ImageIcon(GamePanel.class.getResource("/exia/ipc/ihm/res/Truck.png")));
      this.route = new ArrayList();
      this.route.add(new Point(new Point(472, 185)));
      this.route.add(new Point(new Point(472, 230)));
      this.route.add(new Point(new Point(-80, 230)));
      this.route = panel.applyRatio(this.route);
      this.next = (Point)this.route.remove(0);
      this.scenario = new Sequence();
      int i = 0;
      Point last = this.next;
      Iterator var5 = this.route.iterator();

      while(var5.hasNext()) {
         Point pt = (Point)var5.next();
         Timeline timeline = new Timeline(this);
         timeline.addPropertyToInterpolate("location", last.getLocation(), pt);
         timeline.setDuration((long)(i++ > 0 ? 4000 : 800));
         last = pt;
         this.scenario.addScenarioActor(timeline);
      }

   }

   public Mobile(GamePanel panel, Product p, Node from, Node to) {
      this.setSize(20, 20);
      this.setLocation(panel.applyRatio(from.getOutputLocation()));
      this.setIcon(new ImageIcon(GamePanel.class.getResource("/exia/ipc/ihm/res/Package.png")));
      this.route = new ArrayList(Arrays.asList(from.getRoute(to)));
      this.route.add(to.getInputLocation());
      this.route = panel.applyRatio(this.route);
      this.scenario = new Sequence();
      Point last = panel.applyRatio(from.getOutputLocation());
      Iterator var7 = this.route.iterator();

      while(var7.hasNext()) {
         Point pt = (Point)var7.next();
         Timeline timeline = new Timeline(this);
         timeline.addPropertyToInterpolate("location", last.getLocation(), pt);
         last = pt;
         this.scenario.addScenarioActor(timeline);
      }

      if (this.route.size() < 1) {
         throw new NullPointerException("Invalid route");
      } else {
         this.next = (Point)this.route.remove(0);
      }
   }

   /** @deprecated */
   @Deprecated
   public void waitForDestinationReached() {
      while(!this.reached) {
         ;
      }

   }

   /** @deprecated */
   @Deprecated
   public void updatePosition() {
      boolean reached = true;
      Point loc = this.getLocation();
      if (Math.abs(loc.x - this.next.x) < 14) {
         loc.x = this.next.x;
      } else {
         reached = false;
         if (loc.x > this.next.x) {
            loc.x -= 14;
         } else if (loc.x < this.next.x) {
            loc.x += 14;
         }
      }

      if (Math.abs(loc.y - this.next.y) < 14) {
         loc.y = this.next.y;
      } else {
         reached = false;
         if (loc.y > this.next.y) {
            loc.y -= 14;
         } else if (loc.y < this.next.y) {
            loc.y += 14;
         }
      }

      this.setLocation(loc);
      if (reached) {
         if (!this.route.isEmpty()) {
            this.next = (Point)this.route.remove(0);
         } else {
            PrositIPC.getView().gamePanel.remove(this);
            this.reached = true;
         }
      }

   }

   public TimelineScenario getMoveScenario() {
      return this.scenario;
   }
}
