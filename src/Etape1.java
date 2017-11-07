import exia.ipc.entities.IStep1Strategy;
import exia.ipc.entities.InputDock;
import exia.ipc.entities.MachineX;
import exia.ipc.entities.Product;

public class Etape1 implements IStep1Strategy {
    @Override
    public synchronized Product onMachineRequest(InputDock inputDock, MachineX machineX) throws Exception {
        while(!inputDock.isProductAvailable()){
            Thread.sleep(10);
        }
        return inputDock.accept();
    }
}
