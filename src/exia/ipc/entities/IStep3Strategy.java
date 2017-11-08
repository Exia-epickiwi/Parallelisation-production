package exia.ipc.entities;

public interface IStep3Strategy {
   MachineZ chooseMachine(MachineZ var1, MachineZ var2) throws Exception;

   void onMachineRequest(Product var1, MachineZ var2, MachineZ var3, MachineZ var4) throws Exception;
}
