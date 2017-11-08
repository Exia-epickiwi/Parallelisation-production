package exia.ipc.entities;

public interface IStep1Strategy {
   Product onMachineRequest(InputDock var1, MachineX var2) throws Exception;
   void onMachineExecute(MachineX machine) throws Exception;
}
