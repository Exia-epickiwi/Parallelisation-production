package exia.ipc.exceptions;

import exia.ipc.entities.Product;

public class OperationAllreadyDoneException extends Exception {
   private static final long serialVersionUID = 1L;

   public OperationAllreadyDoneException(Product product, int value) {
      super("Le produit " + product.getType() + " est déjà passé sur la machine " + Product.machineName(value));
   }
}
