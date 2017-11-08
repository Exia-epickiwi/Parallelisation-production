package exia.ipc.entities;

import exia.ipc.exceptions.MachineAllreadyUsedException;
import exia.ipc.exceptions.OperationAllreadyDoneException;
import java.awt.Point;

public final class MachineZ extends Machine {
   private Product job;
   private MachineZ next;
   private int type;

   MachineZ(int id) {
      this(id, (MachineZ)null);
   }

   MachineZ(int id, MachineZ next) {
      super("Z" + id, new Point(405, 12 + id * 25 + (id > 2 ? 30 : 0)), new Point(368, 28 + id * 23 + (id > 2 ? 30 : 0)), new Point(415, 28 + id * 23 + (id > 2 ? 30 : 0)));
      this.job = null;
      this.type = id < 3 ? id : id - 3;
      this.next = next;
   }

   public void executeJob(Product p) throws MachineAllreadyUsedException, OperationAllreadyDoneException {
      if (this.job != null) {
         throw new MachineAllreadyUsedException(this);
      } else {
         this.job = p;
         this.notifyChange(1);

         try {
            if (this.type == 0) {
               Thread.sleep(300L);
            } else if (this.type == 1) {
               Thread.sleep(2100L);
            } else if (this.type == 2) {
               Thread.sleep(1500L);
            }
         } catch (InterruptedException var3) {
            return;
         }

         if (this.type == 0) {
            p.addOperation(4);
         } else if (this.type == 1) {
            p.addOperation(8);
         } else if (this.type == 2) {
            p.addOperation(16);
         } else {
            System.err.println("Erreur interne");
         }

         this.notifyChange(0);
         this.job = null;
      }
   }

   public boolean isMachineAvailable() {
      return this.job == null;
   }

   public boolean isChainAvailable() {
      boolean ready = this.job == null;
      if (this.next != null) {
         ready = ready && this.next.isMachineAvailable();
      }

      return ready;
   }

   public MachineZ getNextMachine() {
      return this.next;
   }
}
