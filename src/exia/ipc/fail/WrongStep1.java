package exia.ipc.fail;

import exia.ipc.entities.IStep1Strategy;
import exia.ipc.entities.InputDock;
import exia.ipc.entities.MachineX;
import exia.ipc.entities.Product;

public class WrongStep1 implements IStep1Strategy {
   public Product onMachineRequest(InputDock dock, MachineX machine) throws Exception {
      return dock.accept();
   }
}
