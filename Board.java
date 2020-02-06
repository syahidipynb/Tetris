
/**
 * Board
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.lang.model.util.ElementScanner6;
import javax.swing.*;

public class Board extends JPanel {
   private int height;
   private int width;
   private int x;
   private int y;
   private int xCount;
   private int yCount;
   public boolean blocked[][];
   public Blocks blocks[][];
   private Image bg;


   public Board(int x, int y, int width, int height, int xCount, int yCount, String url) {
      super();
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.xCount = xCount;
      this.yCount = yCount;
      this.blocks = new Blocks[xCount][yCount];
      this.blocked = new boolean[xCount][yCount];
      this.setLayout(null);
      this.setPreferredSize(new Dimension(width + 1, height + 1));
      this.bg = new ImageIcon(getClass().getResource(url)).getImage();
   }

   public int getBoardHeight() {
      return this.height;
   }

   public int getBoardWidth() {
      return this.width;
   }

   @Override
   public void paintComponent(Graphics g) {
      
      super.paintComponent(g);
      g.setColor(Color.BLACK);
      g.drawImage(this.bg, 0, 0, null);
      for (int i = this.x; i < this.width; i += this.width / this.xCount) {
         for (int j = this.y; j < this.height; j += this.height / this.yCount) {
            g.drawRect(i, j, Blocks.SIZE, Blocks.SIZE);
         }
      }

   }

   public int clear() {
      int clearedRow = 0;
      int multiplier = 1;
      for (int i = 0; i < 20; i++) {
         for (int j = 0; j < 10; j++) {
            if (!this.blocked[j][i]) {
               break;
            }
            if (j == 9) {
               this.clear(i);
               clearedRow++;
            }
         }
      }
      for (int i = 0; i < clearedRow; i++) {
         multiplier *= (i + 1);
      }
      if (clearedRow > 0) {
         return multiplier * 100;
      } else
         return 0;
   }

   public void clear(int line) {
      for (int i = 0; i < 10; i++) {
         this.remove(blocks[i][line]);
         blocked[i][line] = false;
         blocks[i][line] = null;
      }
      for (int i = line - 1; i >= 0; i--) {
         for (int j = 0; j < 10; j++) {
            if (blocks[j][i] != null) {
               blocks[j][i].setPos(blocks[j][i].getX(), blocks[j][i].getY() + 1);
               blocks[j][i + 1] = blocks[j][i];
               blocked[j][i + 1] = true;
               blocked[j][i] = false;
               blocks[j][i] = null;
            }
         }
      }
      this.revalidate();
      this.repaint();
   }
}