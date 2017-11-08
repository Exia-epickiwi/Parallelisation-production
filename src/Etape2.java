import exia.ipc.entities.IStep2Strategy;
import exia.ipc.entities.MachineX;
import exia.ipc.entities.MachineY;

import java.util.concurrent.Semaphore;

public class Etape2 implements IStep2Strategy {

    private Semaphore lock;

    public Etape2() {
        this.lock = new Semaphore(2);
    }

    @Override
    public void onMachineRequest(MachineX applicant, MachineY executor) throws Exception {}

    @Override
    public void onMachineExecute(MachineX applicant, MachineY executor) throws Exception {
        this.lock.acquire();
        executor.executeJob();
        this.lock.release();
    }
}
