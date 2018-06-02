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

    Game game = new Game();
    GameController controller = new GameController(game);
    Window window = new Window(game);

    controller.setWindow(window);
    window.controller = controller;

    Board board = new Board();
    board.load(Map.tabTest);
    game.board = board;
    window.createPanel();
    controller.cleanBalls(); // TODO: clean
    window.panel.hideButtons();
    window.panel.updateClickables();
    controller.state = State.NORMAL;

    window.setTitle("Abalone");
    window.setVisible(true);
    window.panel.drawBackground();

    game.players.add(new Player("BLACK", Color.BLACK));
    game.players.add(new Player("WHITE", Color.WHITE));

  }

}
