import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class testfile extends JPanel implements KeyListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int GRID_SIZE = 20;
    private static final int PACMAN_RADIUS = 10;

    private int pacmanX = 200;
    private int pacmanY = 300;
    private int pacmanDirection = 0; // 0 - right, 1 - down, 2 - left, 3 - up

    private boolean[][] walls = {
        {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, true, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, true, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, true, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, true, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, true, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, true, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, true, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, true, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, true, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, true, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, false,false, false, false, false, false, false, true},
        {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
    };

    public testfile() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        // Add key listener
        setFocusable(true);
        addKeyListener(this);

        // Set up the walls
        walls[0][0] = true;
        
        

        // Start the game loop
        Thread gameLoop = new Thread(() -> {
            while (true) {
                updateGame();
                repaint();

                try {
                    Thread.sleep(100); // Adjust the game speed by changing the sleep duration
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        gameLoop.start();
    }

    private void updateGame() {
        // Calculate the next position based on the current direction
        int nextX = pacmanX;
        int nextY = pacmanY;

        switch (pacmanDirection) {
            case 0:
                nextX += GRID_SIZE;
                break;
            case 1:
                nextY += GRID_SIZE;
                break;
            case 2:
                nextX -= GRID_SIZE;
                break;
            case 3:
                nextY -= GRID_SIZE;
                break;
        }

        // Check if the next position is within the boundaries
        if (nextX >= 0 && nextX < WIDTH && nextY >= 0 && nextY < HEIGHT) {
            // Check if the next position is a wall
            int gridX = nextX / GRID_SIZE;
            int gridY = nextY / GRID_SIZE;

            if (!walls[gridX][gridY]) {
                pacmanX = nextX;
                pacmanY = nextY;
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw Pacman
        g.setColor(Color.YELLOW);
        g.fillArc(pacmanX, pacmanY, PACMAN_RADIUS * 2, PACMAN_RADIUS * 2, getMouthAngle(), 360 - getMouthAngle() * 2);

        // Draw Maze (walls)
        g.setColor(Color.BLUE);
        for (int x = 0; x < walls.length; x++) {
            for (int y = 0; y < walls[0].length; y++) {
                if (walls[x][y]) {
                    g.fillRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE);
                }
            }
        }

        // Add other game elements (dots, ghosts, etc.) if desired
    }

    private int getMouthAngle() {
        // Calculate the mouth angle based on the current direction
        switch (pacmanDirection) {
            case 0:
                return 45;
            case 1:
                return 135;
            case 2:
                return 225;
            case 3:
                return 315;
            default:
                return 0;
        }
    }

    private void changeDirection(int newDirection) {
        pacmanDirection = newDirection;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                changeDirection(3);
                break;
            case KeyEvent.VK_DOWN:
                changeDirection(1);
                break;
            case KeyEvent.VK_LEFT:
                changeDirection(2);
                break;
            case KeyEvent.VK_RIGHT:
                changeDirection(0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Handle key released event if needed
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Handle key typed event if needed
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pacman Game");
        testfile game = new testfile();
        frame.getContentPane().add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
