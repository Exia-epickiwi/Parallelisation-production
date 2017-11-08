package exia.ipc.exceptions;

import exia.ipc.entities.Machine;

public class MachineAllreadyUsedException extends Exception {
   private static final long serialVersionUID = 1L;

   public MachineAllreadyUsedException(Machine subject) {
      super("La machine " + subject.getName() + " est déjà en fonctionnement : le produit est perdu");
   }
}
