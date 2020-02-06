import javax.swing.*;
import java.awt.*;

public class Blocks extends JComponent {
   private Color color;
   private int x, y;
   public static final int SIZE = 30;

   public Blocks(Color color, int x, int y) {
      this.x = x;
      this.y = y;
      this.color = color;
      this.setBounds(this.x * 30 / 2, this.y * 30 / 2, 30 + x * 30, 30 + y * 30);
   }

   public void setPos(int x, int y) {
      this.x = x;
      this.y = y;
      this.setBounds(this.x * 30 / 2 - this.x, this.y * 30 / 2 - this.y, 30 + this.x * 30, 30 + this.y * 30);
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setColor(this.color);
      g.fillRect(this.x * 30 - this.x, this.y * 30 - this.y, 30, 30);
      g.setColor(Color.WHITE);
      g.drawRect(this.x * 30 - this.x, this.y * 30 - this.y, 30, 30);
   }

}