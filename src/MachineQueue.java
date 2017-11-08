import exia.ipc.entities.MachineZ;
import exia.ipc.entities.Product;

import java.util.ArrayList;

public class MachineQueue extends Thread {

    private ArrayList<MachineOrder> waitingOrders;
    private ArrayList<MachineOrder> runningOrders;
    private boolean working;

    public MachineQueue() {
        this.waitingOrders = new ArrayList<>();
        this.runningOrders = new ArrayList<>();
        this.working = true;
    }

    @Override
    public void run() {
        while (working){

            MachineOrder[] runningOrdersArray = new MachineOrder[this.runningOrders.size()];
            runningOrdersArray = this.runningOrders.toArray(runningOrdersArray);
            for(MachineOrder order:runningOrdersArray){
                if(order.getState() == State.TERMINATED){
                    this.runningOrders.remove(order);
                }
            }

            MachineOrder[] waitingOrdersArray = new MachineOrder[this.waitingOrders.size()];
            waitingOrdersArray = this.waitingOrders.toArray(waitingOrdersArray);
            for (int i = waitingOrdersArray.length-1; i >= 0; i--) {
                MachineOrder order = waitingOrdersArray[i];
                if(!isMachineWorking(order.getMachine())){
                    this.waitingOrders.remove(order);
                    this.runningOrders.add(order);
                    order.start();
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    //---------------------
    //    INTERNAL CALLS
    //---------------------

    private boolean isMachineWorking(MachineZ machine) {
        for (MachineOrder order : this.runningOrders) {
            if(order.getMachine() == machine){
                return true;
            }
        }
        return false;
    }

    //---------------------
    //       PUBLIC
    //---------------------

    public MachineOrderCollection addProductChain(Product product,MachineZ... machines){
        ArrayList<MachineOrder> orders = new ArrayList<>();
        for (MachineZ machine: machines) {
            MachineOrder order = new MachineOrder(machine,product);
            this.waitingOrders.add(order);
            orders.add(order);
        }
        MachineOrder[] ordersArray = new MachineOrder[orders.size()];
        ordersArray = orders.toArray(ordersArray);
        return new MachineOrderCollection(ordersArray);
    }

    public int countWaitingOrders(MachineZ... machines){
        int count = 0;
        for (MachineOrder order: this.waitingOrders){
            for (MachineZ machine : machines) {
                if(order.getMachine() == machine){
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    //---------------------
    //  GETTERS & SETTERS
    //---------------------

    public boolean isWorking() {
        return working;
    }

    public void stopWorking() {
        this.working = false;
    }
}
