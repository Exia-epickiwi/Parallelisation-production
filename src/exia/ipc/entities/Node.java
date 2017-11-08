package exia.ipc.entities;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class Node {
   private List listeners = new ArrayList();
   protected Map routes = new HashMap();
   private String name;
   private Point indicatorLocation;
   private Point inputLocation;
   private Point outputLocation;
   int counter = 0;

   Node(String name, Point indicatorLocation, Point inputLocation, Point outputLocation) {
      this.name = name;
      this.indicatorLocation = indicatorLocation;
      this.inputLocation = inputLocation;
      this.outputLocation = outputLocation;
      PrositIPC.register(this);
   }

   void addIndicatorListener(IndicatorListener l) {
      this.listeners.add(l);
   }

   void notifyChange(int value) {
      Iterator var3 = this.listeners.iterator();

      while(var3.hasNext()) {
         IndicatorListener i = (IndicatorListener)var3.next();
         i.notifyChange(value);
      }

   }

   public String getName() {
      return this.name;
   }

   public Point getIndicatorLocation() {
      return this.indicatorLocation;
   }

   public Point getInputLocation() {
      return this.inputLocation;
   }

   public Point getOutputLocation() {
      return this.outputLocation;
   }

   public String toString() {
      return this.getClass().getSimpleName() + "[" + this.name + "]";
   }

   void addRoute(Node target, Point... points) {
      this.routes.put(target, points);
   }

   public Point[] getRoute(Node target) {
      return (Point[])this.routes.get(target);
   }

   Node getRoute(int index) {
      return (Node)(new ArrayList(this.routes.keySet())).get(index);
   }

   void incrementCounter() {
      ++this.counter;
      this.notifyChange(this.counter);
   }

   void decrementCounter() {
      --this.counter;
      this.notifyChange(this.counter);
   }

   void resetCounter() {
      this.counter = 0;
      this.notifyChange(0);
   }
}
