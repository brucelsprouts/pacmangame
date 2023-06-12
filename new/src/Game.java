import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {
    private Board board;

    static int pacmanLives = 3;
    static int score = 0;

    public Game() {
        setTitle("Pacman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        board = new Board();
        add(board);
        pack();
        setVisible(true);
    }

    public void run() {
        while (true) {
            Board.updateGame();
            repaint();
            if (death()) {
                try {
                    Thread.sleep(3000);
                    Board.gList.clear();
                    board = new Board();
                    Pacman.pacmanX = 260;
                    Pacman.pacmanY = 460;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(175); // Adjust the game speed by changing the sleep duration
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean death() {
        for (int i = 0; i < Board.gList.size(); i++) {
            Ghost aGhost = Board.gList.get(i);
            if ((Pacman.pacmanX == aGhost.getGhostX() && Pacman.pacmanY == aGhost.getGhostY()) || 
                    (Pacman.prevX == aGhost.getGhostX() && Pacman.prevY == aGhost.getGhostY())) {
                Game.pacmanLives--;
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Game game = new Game();
        Thread thread = new Thread(game);
        thread.start();
    }
}