package exia.ipc.exceptions;

import exia.ipc.entities.Product;

public class ProductLostException extends Exception {
   private static final long serialVersionUID = 1L;

   public ProductLostException(Product p, String msg, Throwable cause) {
      super("Un produit " + p.getType().toString() + " a été perdu : " + msg, cause);
   }

   public ProductLostException(Product p, String msg) {
      this(p, msg, (Throwable)null);
   }
}
