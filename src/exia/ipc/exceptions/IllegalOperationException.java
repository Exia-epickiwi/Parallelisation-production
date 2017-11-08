package exia.ipc.exceptions;

import exia.ipc.entities.Node;

public class IllegalOperationException extends Exception {
   private static final long serialVersionUID = 1L;

   public IllegalOperationException(Node m, String msg) {
      super("Operation invalide sur " + m + " : " + msg);
   }
}
