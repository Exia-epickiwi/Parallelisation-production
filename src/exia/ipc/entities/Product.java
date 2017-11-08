package exia.ipc.entities;

import exia.ipc.exceptions.AllreadyFinishedProductException;
import exia.ipc.exceptions.NotFinishedProductException;
import exia.ipc.exceptions.OperationAllreadyDoneException;

public final class Product {
   private int step = 0;
   private Product.Type type;
   private boolean finished;
   public static final int X = 1;
   public static final int Y = 2;
   public static final int Z1 = 4;
   public static final int Z2 = 8;
   public static final int Z3 = 16;

   public Product(Product.Type type) {
      this.type = type;
   }

   void addOperation(int value) throws OperationAllreadyDoneException {
      if ((this.step & value) == value) {
         throw new OperationAllreadyDoneException(this, value);
      } else {
         this.step |= value;
      }
   }

   public Product.Type getType() {
      return this.type;
   }

   public boolean isFinished() {
      return this.finished && this.step >= 31;
   }

   public void makeFinished() throws AllreadyFinishedProductException, NotFinishedProductException {
      if (this.finished) {
         throw new AllreadyFinishedProductException(this);
      } else if (this.step < 31) {
         throw new NotFinishedProductException(this);
      } else {
         this.finished = true;
      }
   }

   public static String machineName(int value) {
      if (value == 1) {
         return "X";
      } else if (value == 2) {
         return "Y";
      } else if (value == 4) {
         return "Za";
      } else if (value == 8) {
         return "Zb";
      } else {
         return value == 16 ? "Zc" : "?";
      }
   }

   public static enum Type {
      M1,
      M2,
      M3,
      M4;
   }
}
