package exia.ipc.ihm;

public class PlaceHolder {
   private Object val;

   public PlaceHolder(Object val) {
      this.setValue(val);
   }

   public synchronized void setValue(Object val) {
      this.val = val;
   }

   public synchronized Object getValue() {
      return this.val;
   }
}
