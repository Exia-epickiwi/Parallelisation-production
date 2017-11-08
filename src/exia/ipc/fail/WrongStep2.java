package exia.ipc.fail;

import exia.ipc.entities.IStep2Strategy;
import exia.ipc.entities.MachineX;
import exia.ipc.entities.MachineY;

public class WrongStep2 implements IStep2Strategy {
   public void onMachineRequest(MachineX applicant, MachineY executor) throws Exception {
   }

   public void onMachineExecute(MachineX applicant, MachineY executor) throws Exception {
      executor.executeJob();
   }
}
