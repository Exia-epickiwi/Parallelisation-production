import exia.ipc.entities.MachineZ;
import exia.ipc.entities.Product;
import exia.ipc.exceptions.MachineAllreadyUsedException;
import exia.ipc.exceptions.OperationAllreadyDoneException;

public class MachineZParallelizer {

    private MachineJob[] machineJobThreads;

    public MachineZParallelizer(Product product,MachineZ... machines) {
        this.machineJobThreads = new MachineJob[machines.length];
        for(int i = 0; i<machines.length; i++){
            this.machineJobThreads[i] = new MachineJob(product,machines[i]);
        }
    }

    public void executeJob() throws InterruptedException {
        for (MachineJob machineJobThread : this.machineJobThreads) {
            machineJobThread.start();
        }
        boolean allClear = true;
        do {
            for (MachineJob machineJobThread : this.machineJobThreads) {
                allClear = machineJobThread.getState() == Thread.State.TERMINATED;
                if (!allClear)
                    break;
            }
            Thread.sleep(10);
        }while (!allClear);
    }

    private class MachineJob extends Thread{

        private Product product;
        private MachineZ machine;

        public MachineJob(Product product, MachineZ machine) {
            this.product = product;
            this.machine = machine;
        }

        @Override
        public void run() {
            try {
                this.machine.executeJob(this.product);
            } catch (MachineAllreadyUsedException|OperationAllreadyDoneException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
