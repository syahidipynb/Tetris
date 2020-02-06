import java.awt.*;

import javax.swing.*;

public class Page extends JLayeredPane {
   protected JPanel component;
   protected JPanel modal;

   public Page() {
      super();
   }

   public void setComponent(JPanel c) {
      this.component = c;
      this.component.setBounds(0, 0, Game.SCREEN_HEIGHT, Game.SCREEN_WIDTH);
      this.component.setPreferredSize(new Dimension(Game.SCREEN_HEIGHT, Game.SCREEN_WIDTH));
      this.component.setLayout(new BoxLayout(this.component, BoxLayout.X_AXIS));

   }

   public void setModal(JPanel c) {
      this.modal = c;
      this.modal.setOpaque(false);
      this.modal.setBounds(1, 1, Game.SCREEN_HEIGHT - 1, Game.SCREEN_WIDTH - 1);
      this.modal.setLayout(new BoxLayout(this.modal, BoxLayout.Y_AXIS));

   }

   public void showModal() {
      this.setPanelEnabled(this.component, false);
      this.add(this.modal, JLayeredPane.MODAL_LAYER);
   }

   public void hideModal() {
      this.remove(this.modal);
      this.setPanelEnabled(this.component, true);
      this.revalidate();
      this.repaint();
   }

   public void render() {
      this.add(this.component, JLayeredPane.DEFAULT_LAYER);
   }

   public JPanel getComponent() {
      return this.component;
   }
   
   private void setPanelEnabled(JPanel panel, Boolean isEnabled) {
    panel.setEnabled(isEnabled);

    Component[] components = panel.getComponents();

    for (Component component : components) {
        if (component instanceof JPanel) {
            setPanelEnabled((JPanel) component, isEnabled);
        }
        component.setEnabled(isEnabled);
    }
}
}