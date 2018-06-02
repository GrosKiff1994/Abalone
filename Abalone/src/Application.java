import controller.GameController;
import model.Board;
import model.Color;
import model.Game;
import model.Map;
import model.Player;
import view.Window;

public class Application {

  public static void main(String[] args) {

    Game game = new Game();
    GameController gameController = new GameController(game);
    Window window = new Window(game);

    window.setController(gameController);
    gameController.setWindow(window);

    Board board = new Board();
    board.load(Map.tabTest);
    game.setBoard(board);
    window.createPanel();
    gameController.cleanBalls(); // TODO: clean
    window.getPanel().hideButtons();
    window.getPanel().updateClickables();
    gameController.setState(controller.State.NORMAL);

    window.setTitle("Abalone");
    window.setVisible(true);
    window.getPanel().drawBackground();

    game.players.add(new Player("BLACK", Color.BLACK));
    game.players.add(new Player("WHITE", Color.WHITE));

  }
}
