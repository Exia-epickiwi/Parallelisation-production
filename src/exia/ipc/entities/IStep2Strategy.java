package exia.ipc.entities;

public interface IStep2Strategy {
   void onMachineRequest(MachineX var1, MachineY var2) throws Exception;

   void onMachineExecute(MachineX var1, MachineY var2) throws Exception;
}
