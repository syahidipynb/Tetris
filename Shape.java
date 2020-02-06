import javax.swing.*;
import java.awt.*;

public class Shape extends JPanel {
      private Blocks[] blocks;
      private char form;
      private int x;
      public int y;
      public int xPadding;
      public int yPadding;
      public int size;

      public Shape(Blocks[] blocks, char form) {
            super(null);
            this.size = 150;
            this.form = form;
            this.blocks = new Blocks[4];

            this.xPadding = 2;
            this.yPadding = 2;
            this.setOpaque(false);
            this.x = 0;
            this.y = 0;
            for (int i = 0; i < 4; i++) {
                  this.blocks[i] = blocks[i];
                  this.blocks[i].setPos(this.blocks[i].getX() + this.xPadding, this.blocks[i].getY() + this.yPadding);
                  this.add(this.blocks[i]);
            }
            this.setBounds(this.x, this.y, this.size, this.size);
      }

      public void moveLeft(boolean game[][]) {
            boolean render = true;
            for (Blocks block : this.blocks) {
                  if (block.getX() < 1
                              || game[block.getX() - 1][this.y + this.yPadding * 30 > 0 ? block.getY() + this.y / 30
                                          : 0]) {
                        render = false;
                  } else {

                  }
            }
            if (render) {
                  for (Blocks block : this.blocks) {
                        block.setPos(block.getX() - 1, block.getY());
                  }
                  this.xPadding--;
            }
      }

      public void moveRight(boolean game[][]) {
            boolean render = true;
            for (Blocks block : this.blocks) {
                  if (block.getX() > 8
                              || game[block.getX() + 1][this.y + this.yPadding * 30 > 0 ? block.getY() + this.y / 30
                                          : 0]) {
                        render = false;
                  } else {

                  }
            }
            if (render) {
                  for (Blocks block : this.blocks) {
                        block.setPos(block.getX() + 1, block.getY());
                  }
                  this.xPadding++;
            }
      }

      public boolean moveDown(boolean game[][]) {
            if (!this.isHit(game)) {
                  this.y += 30;
                  this.setBounds(0, this.y, this.size, this.size);
                  return true;
            } else
                  return false;
            // else{
            // // for (int i = 0; i < 20; i++) {
            // // this.clear()
            // // }
            // }
      }

      public void rotate(boolean game[][]) {
            int newX[] = new int[4];
            int newY[] = new int[4];
            boolean render = true;
            if (this.form == 'O') {
                  return;
            }
            for (int i = 0; i < 4; i++) {
                  newX[i] = (this.blocks[i].getY() - this.yPadding) * -1 + this.xPadding;
                  newY[i] = this.blocks[i].getX() - this.xPadding + this.yPadding;
                  if (newX[i] < 0 || newY[i] < 0 || newX[i] >= 9 || game[newX[i]][newY[i]])
                        render = false;
            }
            if (render) {
                  for (int i = 0; i < 4; i++) {
                        this.blocks[i].setPos(newX[i], newY[i]);
                  }
            }
      }

      public boolean isHit(boolean game[][]) {
            int mostTop = 0;
            for (Blocks block : this.blocks) {
                  int y = block.getY() + this.y / 30;
                  int x = block.getX();
                  if (y < mostTop) {
                        mostTop = y;
                  }
                  if (y >= 19 || game[x][y >= 0 ? y + 1 : 0]) {
                        return true;
                  }
            }
            return false;
      }

      public Blocks[] getBlocks() {
            return this.blocks;
      }

      public void setContainerSize(int size) {
            this.size = size;
      }

      public void toActiveShape() {
            this.xPadding = 4;
            this.yPadding = 4;
            this.size = 300;
            int yMax = 0;
            for (int i = 0; i < this.blocks.length; i++) {
                  this.blocks[i].setPos(this.blocks[i].getX() + this.xPadding - 2,
                              this.blocks[i].getY() + this.yPadding - 2);
                  if (this.blocks[i].getY() > yMax)
                        yMax = this.blocks[i].getY();
            }
            this.x = 0;
            this.y = -1 * (this.size + 30 - yMax * 30);
            this.setBounds(this.x, this.y, this.size, this.size);
      }

}