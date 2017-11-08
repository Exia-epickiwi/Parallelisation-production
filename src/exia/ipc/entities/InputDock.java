package exia.ipc.entities;

import exia.ipc.exceptions.CurrentAccessException;
import exia.ipc.exceptions.NoMoreProductsException;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public final class InputDock extends Node implements Runnable {
   private List stock = new ArrayList();
   private boolean working = false;
   private Product.Type product;

   public InputDock(Product.Type type, Point indicatorLocation, Point outputLocation) {
      super("Q" + type, indicatorLocation, (Point)null, outputLocation);
      this.product = type;
   }

   public int getAvailableProductsCount() {
      return this.stock.size();
   }

   public Product accept() throws NoMoreProductsException, CurrentAccessException {
      if (this.working) {
         throw new CurrentAccessException(this);
      } else if (this.stock.size() < 1) {
         throw new NoMoreProductsException(this);
      } else {
         this.working = true;

         try {
            Thread.sleep(200L);
         } catch (InterruptedException var2) {
            return null;
         }

         Product p = (Product)this.stock.remove(0);
         this.working = false;
         this.notifyChange(this.stock.size());
         return p;
      }
   }

   boolean isCurrentlyPickingUp() {
      return this.working;
   }

   public void run() {
      while(!Thread.interrupted()) {
         int var1 = 1 + (int)(Math.random() * 3.0D);

         while(var1-- > 0) {
            this.stock.add(new Product(this.product));
         }

         this.notifyChange(this.stock.size());

         try {
            Thread.sleep((long)((2 + (int)(Math.random() * 4.0D)) * 900));
         } catch (InterruptedException var3) {
            return;
         }
      }

   }

   public boolean isProductAvailable() {
      return !this.stock.isEmpty();
   }

   void addProduct(Product p) {
      this.stock.add(p);
   }

   void addIndicatorListener(IndicatorListener l) {
      super.addIndicatorListener(l);
      l.notifyChange(this.stock.size());
   }

   void addProducts(int i) {
      while(i-- > 0) {
         this.addProduct(new Product(this.product));
      }

   }
}
