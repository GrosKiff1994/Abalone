package vue;

import javax.swing.JFrame;
import controller.GameController;
import modele.Game;

public class Window extends JFrame {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private PanneauJeu panelPrincipal;
  private Game modele;
  private GameController controller;

  public Window(Game modele) {
    this.modele = modele;
    setSize((int) (PanneauJeu.DIMBOULE * 16.15),
        (int) (11 * (PanneauJeu.DIMBOULE - PanneauJeu.DIMBOULE / 16)));

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);
  }

  public PanneauJeu getPanel() {
    return this.panelPrincipal;
  }

  public void createPanel() {
    panelPrincipal = new PanneauJeu(this, controller);
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
