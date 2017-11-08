package exia.ipc.ihm;

import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class View extends JFrame {
   private static final long serialVersionUID = 5113884946042341761L;
   public JPanel contentPane;
   public GamePanel gamePanel;
   public JLabel labelCoins;
   public JTextArea textArea;

   public View() {
      this.setTitle("Exia - Prosit IPC");
      this.setDefaultCloseOperation(3);
      this.setBounds(100, 100, 615, 483);
      this.contentPane = new JPanel();
      this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      this.setContentPane(this.contentPane);
      this.gamePanel = new GamePanel();
      this.gamePanel.setOpaque(false);
      this.gamePanel.setLayout((LayoutManager)null);
      JScrollPane scrollPane = new JScrollPane();
      GroupLayout gl_contentPane = new GroupLayout(this.contentPane);
      gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(this.gamePanel, Alignment.LEADING, -1, 589, 32767).addComponent(scrollPane, -1, 577, 32767)).addGap(0)));
      gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addComponent(this.gamePanel, -1, -1, 32767).addPreferredGap(ComponentPlacement.RELATED).addComponent(scrollPane, -2, 105, -2)));
      this.labelCoins = new JLabel("0");
      this.labelCoins.setIcon(new ImageIcon(View.class.getResource("/exia/ipc/ihm/res/coins.png")));
      this.labelCoins.setBounds(10, 11, 90, 14);
      this.gamePanel.add(this.labelCoins);
      this.textArea = new JTextArea();
      this.textArea.setForeground(Color.RED);
      scrollPane.setViewportView(this.textArea);
      this.contentPane.setLayout(gl_contentPane);
   }
}
