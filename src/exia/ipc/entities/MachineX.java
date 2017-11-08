package exia.ipc.entities;

import exia.ipc.exceptions.*;

import java.awt.Point;

public final class MachineX extends Machine {
   private int tempsTraitement = 4;
   InputDock q1;
   InputDock q2;
   boolean working = false;

   public MachineX(int id, InputDock q1, InputDock q2) {
      super("X" + id, new Point(215, 30 + id * 31), new Point(160, 35 + id * 31), new Point(222, 37 + id * 31));
      this.q1 = q1;
      this.q2 = q2;
   }

   public final void executeWork() throws Exception {
      if (working){
         throw new MachineAllreadyUsedException(this);
      }
      working = true;
      try {
         Thread.sleep((long)(this.tempsTraitement * 500 + (int)(Math.random() * 800.0D)));
      } catch (InterruptedException var2) {
         return;
      }

      Product p3 = new Product(Product.Type.M3);

      if (p3 == null)
         throw new ProductLostException(p3, "La fonction onMachineExceute() n'as pa renvoyé de produit");

      (new Thread(new Runnable() {
         public void run() {
            try {
               p3.addOperation(1);
               final MachineY next = (MachineY) getOutputNode();
               PrositIPC.Step2.onMachineRequest(MachineX.this, next);
               MachineX.this.notifyChange(0);
               PrositIPC.move(p3, MachineX.this, next);
               PrositIPC.Step2.onMachineExecute(MachineX.this, next);
            } catch (Throwable var2) {
               PrositIPC.handleError(var2);
            }

         }
      }, "X to Y")).start();

      working = false;
   }

   public void run() {
      while(!Thread.interrupted()) {
         try {
            Product p1 = PrositIPC.Step1.onMachineRequest(this.q1, this);
            Product p2 = PrositIPC.Step1.onMachineRequest(this.q2, this);
            Thread t1 = PrositIPC.moveAsynch(p1, this.q1, this);
            PrositIPC.move(p2, this.q2, this);
            t1.join();
            if (p1 == null) {
               throw new IllegalOperationException(this.q1, "la méthode onMachineRequest() a renvoyé un NULL");
            }

            if (p2 == null) {
               throw new IllegalOperationException(this.q2, "la méthode onMachineRequest() a renvoyé un NULL");
            }


            (new Thread(new Runnable() {
               @Override
               public void run() {
                  try{
                     notifyChange(1);
                     PrositIPC.Step1.onMachineExecute(MachineX.this);
                  } catch (Throwable var2) {
                     PrositIPC.handleError(var2);
                  }
               }
            })).start();

         } catch (CurrentAccessException | NoMoreProductsException var7) {
            NoMoreProductsException e = (NoMoreProductsException) var7;

            try {
               PrositIPC.handleError(e);
               Thread.sleep(5000L);
            } catch (InterruptedException var6) {
               return;
            }
         } catch (Throwable var8) {
            PrositIPC.handleError(var8);
         }
      }

   }
}
