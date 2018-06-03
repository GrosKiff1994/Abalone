import controller.GameController;
import controller.State;
import model.Board;
import model.Color;
import model.Game;
import model.Map;
import model.Player;
import view.Window;

public class Application {

  public static void main(String[] args) {

    Board board = new Board(Map.tabTest);
    Game game = new Game(board);
    GameController controller = new GameController(game);
    Window window = new Window(game);

    game.players.add(new Player("BLACK", Color.BLACK));
    game.players.add(new Player("WHITE", Color.WHITE));

    controller.window = window;
    controller.setState(State.NORMAL);
    controller.cleanMarbles(); // TODO: clean

    window.controller = controller;
    window.createPanel();
    window.panel.hideButtons();
    window.panel.updateClickables();
    window.setTitle("Abalone");
    window.setVisible(true);
    window.panel.drawBackground();

  }

}
