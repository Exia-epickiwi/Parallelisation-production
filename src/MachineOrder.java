import exia.ipc.entities.MachineZ;
import exia.ipc.entities.Product;
import exia.ipc.exceptions.MachineAllreadyUsedException;
import exia.ipc.exceptions.OperationAllreadyDoneException;

public class MachineOrder extends Thread{

    private MachineZ machine;
    private Product product;

    public MachineOrder(MachineZ machine, Product product) {
        this.machine = machine;
        this.product = product;
    }

    @Override
    public void run() {
        try {
            this.machine.executeJob(this.product);
        } catch (MachineAllreadyUsedException | OperationAllreadyDoneException e) {
            System.out.println(e.getMessage());
        }
    }

    public MachineZ getMachine() {
        return machine;
    }

    public void setMachine(MachineZ machine) {
        this.machine = machine;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
