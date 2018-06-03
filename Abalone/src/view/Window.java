package view;

import javax.swing.JFrame;
import controller.GameController;
import model.Game;

public class Window extends JFrame {

  private static final long serialVersionUID = 1L;
  public GamePanel panel;
  public Game game;
  public GameController controller;

  public Window(Game game) {
    this.game = game;
    setSize((int) (GamePanel.MARBLE_SIZE * 16.15),
        (int) (11 * (GamePanel.MARBLE_SIZE - GamePanel.MARBLE_SIZE / 16)));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);
  }

  public void createPanel() {
    panel = new GamePanel(this, controller);
    setContentPane(panel);
  }

}
