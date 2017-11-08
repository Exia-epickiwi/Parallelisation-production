package exia.ipc.exceptions;

import exia.ipc.entities.Product;

public class AllreadyFinishedProductException extends Exception {
   private static final long serialVersionUID = 1L;

   public AllreadyFinishedProductException(Product product) {
   }
}
