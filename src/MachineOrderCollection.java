public class MachineOrderCollection {

    private MachineOrder[] orders;

    public MachineOrderCollection(MachineOrder... orders) {
        this.orders = orders;
    }

    public void waitFor() throws InterruptedException {
        boolean allClear = true;
        do{
            for(MachineOrder order:this.orders){
                allClear = order.getState() == Thread.State.TERMINATED;
                if(!allClear)
                    break;
            }

            Thread.sleep(100);
        }while(!allClear);
    }

    public MachineOrder[] getOrders() {
        return orders;
    }
}
