package exia.ipc.fail;

import exia.ipc.entities.IStep3Strategy;
import exia.ipc.entities.MachineZ;
import exia.ipc.entities.Product;

public class WrongStep3 implements IStep3Strategy {
   public MachineZ chooseMachine(MachineZ target1, MachineZ target2) throws Exception {
      return target1;
   }

   public void onMachineRequest(Product product, MachineZ m1, MachineZ m2, MachineZ m3) throws Exception {
      m1.executeJob(product);
      m2.executeJob(product);
      m3.executeJob(product);
      product.makeFinished();
   }
}
