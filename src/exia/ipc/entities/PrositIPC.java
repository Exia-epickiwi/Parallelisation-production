package exia.ipc.entities;

import exia.ipc.fail.WrongStep1;
import exia.ipc.fail.WrongStep2;
import exia.ipc.fail.WrongStep3;
import exia.ipc.ihm.Indicator;
import exia.ipc.ihm.Mobile;
import exia.ipc.ihm.PlaceHolder;
import exia.ipc.ihm.View;
import exia.ipc.ihm.ViewController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineEvent.Type;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.pushingpixels.trident.TimelineScenario;
import org.pushingpixels.trident.TimelineScenario.TimelineScenarioState;
import org.pushingpixels.trident.callback.TimelineScenarioCallback;

public final class PrositIPC {
   private static ArrayList jobs;
   public static IStep1Strategy Step1 = new WrongStep1();
   public static IStep2Strategy Step2 = new WrongStep2();
   public static IStep3Strategy Step3 = new WrongStep3();
   private static ViewController ctrl;
   private static OutputDock outputDock;
   private static AudioInputStream audioStream;
   private static AudioFormat audioFormat;
   private static int audioSize;
   private static byte[] audioData;
   private static Info audioInfo;
   private static int score = 0;

   static {
      try {
         URL yourFile = PrositIPC.class.getResource("/exia/ipc/ihm/res/smw_coin.wav");
         audioStream = AudioSystem.getAudioInputStream(yourFile);
         audioFormat = audioStream.getFormat();
         audioSize = (int)((long)audioFormat.getFrameSize() * audioStream.getFrameLength());
         audioData = new byte[audioSize];
         audioInfo = new Info(Clip.class, audioFormat, audioSize);
         audioStream.read(audioData, 0, audioSize);
      } catch (Exception var12) {
         var12.printStackTrace();
      }

      jobs = new ArrayList();
      InputDock q1 = new InputDock(Product.Type.M1, new Point(50, 38), new Point(78, 63));
      InputDock q2 = new InputDock(Product.Type.M2, new Point(50, 160), new Point(78, 130));
      q1.addProducts(3);
      q2.addProducts(2);
      MachineX m1 = new MachineX(1, q1, q2);
      MachineX m2 = new MachineX(2, q1, q2);
      MachineX m3 = new MachineX(3, q1, q2);
      q1.addRoute(m1, new Point[]{new Point(78, 95), new Point(143, 95), new Point(143, 65)});
      q2.addRoute(m1, new Point[]{new Point(78, 95), new Point(143, 95), new Point(143, 65)});
      q1.addRoute(m2, new Point[]{new Point(78, 95), new Point(143, 95)});
      q2.addRoute(m2, new Point[]{new Point(78, 95), new Point(143, 95)});
      q1.addRoute(m3, new Point[]{new Point(78, 95), new Point(143, 95), new Point(143, 125)});
      q2.addRoute(m3, new Point[]{new Point(78, 95), new Point(143, 95), new Point(143, 125)});
      MachineY m4 = new MachineY();
      m1.addRoute(m4, new Point[]{new Point(238, 67), new Point(238, 95)});
      m2.addRoute(m4, new Point[]{new Point(230, 95), new Point(238, 95)});
      m3.addRoute(m4, new Point[]{new Point(238, 125), new Point(238, 95)});
      MachineZ m7 = new MachineZ(2);
      MachineZ m6 = new MachineZ(1, m7);
      MachineZ m5 = new MachineZ(0, m6);
      MachineZ m10 = new MachineZ(5);
      MachineZ m9 = new MachineZ(4, m10);
      MachineZ m8 = new MachineZ(3, m9);
      m4.addRoute(m5, new Point[]{new Point(363, 95)});
      m4.addRoute(m8, new Point[]{new Point(363, 95)});
      outputDock = new OutputDock();
      m5.addRoute(outputDock, new Point[]{new Point(420, 100)});
      m8.addRoute(outputDock, new Point[]{new Point(420, 100)});
   }

   public static void start() {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            View v = new View();
            PrositIPC.ctrl = new ViewController(v);
            PrositIPC.ctrl.run();
            PrositIPC.beginStart();
         }
      });
   }

   private static void beginStart() {
      System.out.println("Go !");
      Iterator var1 = jobs.iterator();

      while(var1.hasNext()) {
         Node r = (Node)var1.next();
         if (r instanceof Runnable) {
            (new Thread((Runnable)r, "Thread " + r.getClass().getSimpleName())).start();
         }

         Indicator indicator = new Indicator(r.getIndicatorLocation());
         r.addIndicatorListener(indicator);
         indicator.setSize(new Dimension(16, 14));
         indicator.setLocation(r.getIndicatorLocation());
         ctrl.view.gamePanel.add(indicator);
      }

      (new Timer()).scheduleAtFixedRate(new TimerTask() {
         public void run() {
            SwingUtilities.invokeLater(new Runnable() {
               public void run() {
                  PrositIPC.ctrl.view.labelCoins.setText("" + PrositIPC.score);
                  System.out.println("Vous avez gagné " + PrositIPC.score + " pièces d'or en 30 secondes");
                  PrositIPC.score = 0;
                  PrositIPC.outputDock.resetCounter();
               }
            });
            PrositIPC.truck();
         }
      }, 30000L, 30000L);
   }

   static void debugInOut(Node r) {
      JPanel p;
      if (r.getInputLocation() != null) {
         p = new JPanel();
         p.setSize(3, 3);
         p.setBackground(Color.GREEN);
         p.setLocation(r.getInputLocation());
         ctrl.view.gamePanel.add(p);
      }

      if (r.getOutputLocation() != null) {
         p = new JPanel();
         p.setSize(3, 3);
         p.setBackground(Color.BLUE);
         p.setLocation(r.getOutputLocation());
         ctrl.view.gamePanel.add(p);
      }

   }

   static void playCoinSound() {
      try {
         final Clip clip = (Clip)AudioSystem.getLine(audioInfo);
         clip.open(audioFormat, audioData, 0, audioSize);
         clip.addLineListener(new LineListener() {
            public void update(LineEvent event) {
               if (event.getType() == Type.STOP) {
                  clip.close();
               }

            }
         });
         clip.start();
      } catch (Exception var1) {
         var1.printStackTrace();
      }

   }

   static void register(Node job) {
      jobs.add(job);
   }

   static void truck() {
      move(new Mobile(ctrl.view.gamePanel));
   }

   static void move(Product p, Node from, Node to) {
      move(new Mobile(ctrl.view.gamePanel, p, from, to));
   }

   private static void move(final Mobile mobile) {
      final TimelineScenario scenario = mobile.getMoveScenario();
      final PlaceHolder done = new PlaceHolder(false);
      scenario.addCallback(new TimelineScenarioCallback() {
         public void onTimelineScenarioDone() {
            done.setValue(true);
         }
      });
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            PrositIPC.ctrl.view.gamePanel.add(mobile);
            scenario.play();
         }
      });

      while(!(Boolean)done.getValue() && scenario.getState() != TimelineScenarioState.DONE) {
         ;
      }

      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            PrositIPC.ctrl.view.gamePanel.remove(mobile);
            PrositIPC.ctrl.view.gamePanel.repaint();
         }
      });
   }

   static Thread moveAsynch(final Product p, final Node from, final Node to) {
      Thread t = new Thread(new Runnable() {
         public void run() {
            PrositIPC.move(p, from, to);
         }
      }, "Move asynch");
      t.start();
      return t;
   }

   public static View getView() {
      return ctrl.view;
   }

   public static void handleError(Throwable e) {
      if ("exia.ipc.exceptions".equals(e.getClass().getPackage().getName())) {
         System.err.println("Alerte : " + e.getMessage());
         score -= 5;
      } else {
         e.printStackTrace();
      }

   }

   static void score() {
      score += 10;
      playCoinSound();
   }

   public static int getScore() {
      return score;
   }
}
