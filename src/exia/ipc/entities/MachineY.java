package exia.ipc.entities;

import exia.ipc.exceptions.MachineAllreadyUsedException;
import exia.ipc.exceptions.ProductLostException;
import java.awt.Point;

public final class MachineY extends Machine {
   MachineY() {
      super("Y", new Point(328, 72), new Point(260, 95), new Point(335, 95));
   }

   public void executeJob() throws MachineAllreadyUsedException {
      if (this.counter >= 2) {
         throw new MachineAllreadyUsedException(this);
      } else {
         this.incrementCounter();

         try {
            Thread.sleep(600L);
            final Product product = new Product(Product.Type.M4);
            product.addOperation(1);
            product.addOperation(2);
            final MachineZ machine = PrositIPC.Step3.chooseMachine((MachineZ)this.getRoute(0), (MachineZ)this.getRoute(1));
            if (machine == null) {
               throw new ProductLostException(product, "pas de machine Z choisie");
            }

            (new Thread(new Runnable() {
               public void run() {
                  PrositIPC.move(product, MachineY.this, machine);

                  try {
                     PrositIPC.Step3.onMachineRequest(product, machine, machine.getNextMachine(), machine.getNextMachine().getNextMachine());
                  } catch (Throwable var4) {
                     PrositIPC.handleError(var4);
                     return;
                  }

                  int var1 = 0;

                  while(!product.isFinished()) {
                     try {
                        Thread.sleep(500L);
                     } catch (InterruptedException var3) {
                        return;
                     }

                     if (var1++ >= 10) {
                        PrositIPC.handleError(new ProductLostException(product, "jamais terminé"));
                        return;
                     }
                  }

                  PrositIPC.move(product, machine, machine.getOutputNode());
                  machine.getOutputNode().incrementCounter();
                  PrositIPC.score();
               }
            }, "Y to Z")).start();
            this.decrementCounter();
         } catch (InterruptedException var3) {
            return;
         } catch (Exception var4) {
            this.decrementCounter();
            PrositIPC.handleError(var4);
         }

      }
   }
}
