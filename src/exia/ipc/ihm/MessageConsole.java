package exia.ipc.ihm;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class MessageConsole {
   private JTextComponent textComponent;
   private Document document;
   private boolean isAppend;

   public MessageConsole(JTextComponent textComponent) {
      this(textComponent, true);
   }

   public MessageConsole(JTextComponent textComponent, boolean isAppend) {
      this.textComponent = textComponent;
      this.document = textComponent.getDocument();
      this.isAppend = isAppend;
      textComponent.setEditable(false);
   }

   public void redirectOut() {
      this.redirectOut((Color)null, (PrintStream)null);
   }

   public void redirectOut(Color textColor, PrintStream printStream) {
      MessageConsole.ConsoleOutputStream cos = new MessageConsole.ConsoleOutputStream(textColor, printStream);
      System.setOut(new PrintStream(cos, true));
   }

   public void redirectErr() {
      this.redirectErr((Color)null, (PrintStream)null);
   }

   public void redirectErr(Color textColor, PrintStream printStream) {
      MessageConsole.ConsoleOutputStream cos = new MessageConsole.ConsoleOutputStream(textColor, printStream);
      System.setErr(new PrintStream(cos, true));
   }

   class ConsoleOutputStream extends ByteArrayOutputStream {
      private final String EOL = System.getProperty("line.separator");
      private SimpleAttributeSet attributes;
      private PrintStream printStream;
      private StringBuffer buffer = new StringBuffer(80);
      private boolean isFirstLine;

      public ConsoleOutputStream(Color textColor, PrintStream printStream) {
         if (textColor != null) {
            this.attributes = new SimpleAttributeSet();
            StyleConstants.setForeground(this.attributes, textColor);
         }

         this.printStream = printStream;
         if (MessageConsole.this.isAppend) {
            this.isFirstLine = true;
         }

      }

      public void flush() {
         String message = this.toString();
         if (message.length() != 0) {
            if (MessageConsole.this.isAppend) {
               this.handleAppend(message);
            } else {
               this.handleInsert(message);
            }

            this.reset();
         }
      }

      private void handleAppend(String message) {
         if (MessageConsole.this.document.getLength() == 0) {
            this.buffer.setLength(0);
         }

         if (this.EOL.equals(message)) {
            this.buffer.append(message);
         } else {
            this.buffer.append(message);
            this.clearBuffer();
         }

      }

      private void handleInsert(String message) {
         this.buffer.append(message);
         if (this.EOL.equals(message)) {
            this.clearBuffer();
         }

      }

      private void clearBuffer() {
         if (this.isFirstLine && MessageConsole.this.document.getLength() != 0) {
            this.buffer.insert(0, "\n");
         }

         this.isFirstLine = false;
         String line = this.buffer.toString();

         try {
            if (MessageConsole.this.isAppend) {
               int offset = MessageConsole.this.document.getLength();
               MessageConsole.this.document.insertString(offset, line, this.attributes);
               MessageConsole.this.textComponent.setCaretPosition(MessageConsole.this.document.getLength());
            } else {
               MessageConsole.this.document.insertString(0, line, this.attributes);
               MessageConsole.this.textComponent.setCaretPosition(0);
            }
         } catch (BadLocationException var3) {
            ;
         }

         if (this.printStream != null) {
            this.printStream.print(line);
         }

         this.buffer.setLength(0);
      }
   }
}
