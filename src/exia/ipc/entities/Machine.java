package exia.ipc.entities;

import java.awt.Point;
import java.util.Map.Entry;

public abstract class Machine extends Node implements Runnable {
   Machine(String name, Point indicatorLocation, Point inputLocation, Point outputLocation) {
      super(name, indicatorLocation, inputLocation, outputLocation);
   }

   public void run() {
   }

   Node getOutputNode() {
      return (Node)((Entry)this.routes.entrySet().iterator().next()).getKey();
   }
}
