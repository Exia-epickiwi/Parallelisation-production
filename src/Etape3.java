import exia.ipc.entities.IStep3Strategy;
import exia.ipc.entities.MachineZ;
import exia.ipc.entities.Product;

import java.util.concurrent.Semaphore;

public class Etape3 implements IStep3Strategy {

    private Semaphore lockM1;
    private Semaphore lockM2;

    public Etape3() {
        this.lockM1 = new Semaphore(1);
        this.lockM2 = new Semaphore(1);
    }

    private MachineZ getChainAvailable(MachineZ m1, MachineZ m2) throws InterruptedException {
        if(this.lockM1.tryAcquire())
            return m1;
        else if(this.lockM2.tryAcquire())
            return m2;
        else
            return null;
    }

    @Override
    public MachineZ chooseMachine(MachineZ target1, MachineZ target2) throws Exception {
        MachineZ machineAvailable = getChainAvailable(target1,target2);
        while (machineAvailable == null){
            Thread.sleep(10);
            machineAvailable = getChainAvailable(target1,target2);
        }
        return machineAvailable;
    }

    @Override
    public void onMachineRequest(Product product, MachineZ m1, MachineZ m2, MachineZ m3) throws Exception {
        MachineZParallelizer parallelizer = new MachineZParallelizer(product,m1,m2,m3);
        parallelizer.executeJob();
        product.makeFinished();
        switch(m1.getName()){
            case "Z0":
                this.lockM2.release();
                break;
            case "Z3":
                this.lockM1.release();
                break;
        }
    }

}
