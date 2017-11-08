import exia.ipc.entities.IStep3Strategy;
import exia.ipc.entities.MachineZ;
import exia.ipc.entities.Product;

public class Etape3 implements IStep3Strategy {

    private MachineQueue queue;
    private boolean distributionSwitch = true;

    public Etape3() {
        this.queue = new MachineQueue();
        this.queue.start();
    }

    @Override
    public MachineZ chooseMachine(MachineZ target1, MachineZ target2) throws Exception {
        if(this.distributionSwitch){
            this.distributionSwitch = false;
            return target1;
        } else {
            this.distributionSwitch = true;
            return target2;
        }
    }

    @Override
    public void onMachineRequest(Product product, MachineZ m1, MachineZ m2, MachineZ m3) throws Exception {
        MachineOrderCollection orders = this.queue.addProductChain(product,m1,m2,m3);
        orders.waitFor();
        product.makeFinished();
    }

}
