import exia.ipc.entities.IStep1Strategy;
import exia.ipc.entities.InputDock;
import exia.ipc.entities.MachineX;
import exia.ipc.entities.Product;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class Etape1 implements IStep1Strategy {

    HashMap<MachineX,Semaphore> machineLocks;

    public Etape1() {
        this.machineLocks = new HashMap<>();
    }

    @Override
    public synchronized Product onMachineRequest(InputDock inputDock, MachineX machineX) throws Exception {
        if(!this.machineLocks.containsKey(machineX)){
            this.machineLocks.put(machineX,new Semaphore(1));
        }
        while(!inputDock.isProductAvailable()){
            Thread.sleep(10);
        }
        return inputDock.accept();
    }

    @Override
    public void onMachineExecute(MachineX machine) throws Exception {
        Semaphore lock = this.machineLocks.get(machine);
        lock.acquire();
        machine.executeWork();
        lock.release();
        return;
    }
}
