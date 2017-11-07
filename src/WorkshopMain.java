import exia.ipc.entities.PrositIPC;

public class WorkshopMain {

    public static void main(String[] args){
        PrositIPC.Step1 = new Etape1();
        PrositIPC.Step2 = new Etape2();
        PrositIPC.Step3 = new Etape3();
        PrositIPC.start();
    }

}
