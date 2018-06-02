package view;

import javax.swing.JFrame;
import controller.GameController;
import model.Game;

public class Window extends JFrame {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private GamePanel panelPrincipal;
  private Game modele;
  private GameController controller;

  public Window(Game modele) {
    this.modele = modele;
    setSize((int) (GamePanel.DIMBOULE * 16.15),
        (int) (11 * (GamePanel.DIMBOULE - GamePanel.DIMBOULE / 16)));

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);
  }

  public GamePanel getPanel() {
    return this.panelPrincipal;
  }

  public void createPanel() {
    panelPrincipal = new GamePanel(this, controller);
    setContentPane(panelPrincipal);
  }

  public void setController(GameController controller) {
    this.controller = controller;
  }

  public GameController getController() {
    return this.controller;
  }

  public Game getModele() {
    return modele;
  }
}
