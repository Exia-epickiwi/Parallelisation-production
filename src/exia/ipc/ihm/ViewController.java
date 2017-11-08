package exia.ipc.ihm;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ViewController {
   public View view;
   private List moves;

   public ViewController(View v) {
      this.view = v;
      this.moves = new ArrayList();
   }

   public void run() {
      MessageConsole mc = new MessageConsole(this.view.textArea);
      mc.redirectOut();
      mc.redirectErr(Color.RED, (PrintStream)null);
      this.view.addComponentListener(new ComponentAdapter() {
         public void componentResized(ComponentEvent e) {
            Component[] var5;
            int var4 = (var5 = ViewController.this.view.gamePanel.getComponents()).length;

            for(int var3 = 0; var3 < var4; ++var3) {
               Component c = var5[var3];
               if (c instanceof Indicator) {
                  c.setLocation(ViewController.this.view.gamePanel.applyRatio(((Indicator)c).getDefaultLocation()));
               }
            }

         }
      });
      this.view.setVisible(true);
   }

   public List getMoves() {
      return this.moves;
   }
}
