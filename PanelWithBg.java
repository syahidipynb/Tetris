import java.awt.*;
import javax.swing.*;

/**
 * PanelWithBg
 */
public class PanelWithBg extends JPanel {
   private Image bg;

   public PanelWithBg(String url) {
      this.bg = new ImageIcon(getClass().getResource(url)).getImage();
   }

   public void paintComponent(Graphics g) {
      g.drawImage(this.bg, 0, 0, null);
   }
}