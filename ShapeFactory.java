import java.util.Random;
import java.awt.*;

/**
 * ShapeFactory
 */
public class ShapeFactory {
      private final static char SHAPES[] = { 'I', 'J', 'L', 'O', 'S', 'T', 'Z' };

      public static Shape getRandomShape() {
            Random rand = new Random();
            Blocks blocks[] = new Blocks[4];
            int r = 0, g = 0, b = 0;
            int coords[][][] = { { { 0, 1 }, { 0, 0 }, { 0, -1 }, { 0, -2 } },
                        { { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } }, { { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } },
                        { { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } }, { { -1, 1 }, { 0, 1 }, { 0, 0 }, { 1, 0 } },
                        { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } }, { { -1, 0 }, { 0, 0 }, { 0, 1 }, { 1, 1 } } };
            int randomNumber = rand.nextInt(7);
            char randomShape = ShapeFactory.SHAPES[randomNumber];
            switch (randomShape) {
            case 'I':
                  r = 170;
                  g = 0;
                  b = 0;
                  break;
            case 'J':
                  r = 0;
                  g = 170;
                  b = 0;
                  break;
            case 'L':
                  r = 0;
                  g = 0;
                  b = 170;
                  break;
            case 'O':
                  r = 200;
                  g = 100;
                  b = 0;
                  break;
            case 'S':
                  r = 170;
                  g = 0;
                  b = 170;
                  break;
            case 'T':
                  r = 0;
                  g = 100;
                  b = 100;
                  break;
            case 'Z':
                  r = 101;
                  g = 23;
                  b = 0;
                  break;
            default:
                  break;
            }
            for (int i = 0; i < 4; i++) {
                  blocks[i] = new Blocks(new Color(r, g, b), coords[randomNumber][i][0], coords[randomNumber][i][1]);
            }
            Shape x = new Shape(blocks, randomShape);
            return x;
      }
}