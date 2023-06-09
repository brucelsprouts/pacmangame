import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class TestFile extends JPanel implements KeyListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int GRID_SIZE = 20;
    private static final int PACMAN_RADIUS = 10;

    private int pacmanX = 200;
    private int pacmanY = 300;
    private int pacmanDirection = 0; // 0 - right, 1 - down, 2 - left, 3 - up

    private boolean[][] walls = { 
        {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
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
        {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true}
    };

    private Image pacmanImage;
    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;

    public TestFile() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        // Add key listener
        setFocusable(true);
        addKeyListener(this);

        // Load the Pacman images
        try {
            pacmanImage = ImageIO.read(getClass().getResource("img/pacman.jpg"));
            pacmanUpImage = ImageIO.read(getClass().getResource("img/pacmanup.jpg"));
            pacmanDownImage = ImageIO.read(getClass().getResource("img/pacmandown.jpg"));
            pacmanLeftImage = ImageIO.read(getClass().getResource("img/pacmanleft.jpg"));
            pacmanRightImage = ImageIO.read(getClass().getResource("img/pacmanright.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public void updateGame() {
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

            if (!walls[gridY][gridX]) { // fixed the indexing here
                pacmanX = nextX;
                pacmanY = nextY;
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw Pacman based on the current direction
        Image currentImage = pacmanImage;
        switch (pacmanDirection) {
            case 0:
                currentImage = pacmanRightImage;
                break;
            case 1:
                currentImage = pacmanDownImage;
                break;
            case 2:
                currentImage = pacmanLeftImage;
                break;
            case 3:
                currentImage = pacmanUpImage;
                break;
        }

        g.drawImage(currentImage, pacmanX, pacmanY, PACMAN_RADIUS * 2, PACMAN_RADIUS * 2, this);

        // Draw Maze (walls)
        g.setColor(Color.BLUE);
        for (int x = 0; x < walls.length; x++) {
            for (int y = 0; y < walls[x].length; y++) {
                if (walls[x][y]) { // fixed the indexing here
                    g.fillRect(y * GRID_SIZE, x * GRID_SIZE, GRID_SIZE, GRID_SIZE); // fixed the order of x and y
                }
            }
        }

        // Add other game elements (dots, ghosts, etc.) if desired
    }

    private void changeDirection(int newDirection) {
        pacmanDirection = newDirection;
    }

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

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pacman Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(new TestFile());
        frame.pack();
        frame.setVisible(true);
    }
}
