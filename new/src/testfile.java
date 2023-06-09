import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class TestFile extends JPanel implements KeyListener {
    private static final int WIDTH = 560;
    private static final int HEIGHT = 700;
    private static final int GRID_SIZE = 20;
    private static final int PACMAN_RADIUS = 10;

    private int pacmanX = 280;
    private int pacmanY = 480;
    private int pacmanDirection = 0; // 0 - right, 1 - down, 2 - left, 3 - up
    private int score = 0;

    private int[][] mapObjects = {
    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
    {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1,},
    {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
    {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
    {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
    {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1,},
    {1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
    {1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
    {1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1,},
    {1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1,},
    {0, 0, 0, 0, 0, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 0, 0, 0, 0, 0,},
    {0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0,},
    {0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 3, 3, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0,},
    {1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1,},
    {0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0,},
    {1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1,},
    {0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0,},
    {0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0,},
    {0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0,},
    {1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1,},
    {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1,},
    {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
    {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1,},
    {1, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 1,},
    {1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1,},
    {1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1,},
    {1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1,},
    {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1,},
    {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1,},
    {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1,},
    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,}
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
                    Thread.sleep(200); // Adjust the game speed by changing the sleep duration
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

        System.out.println(pacmanX/GRID_SIZE);
        System.out.println(pacmanY/GRID_SIZE);
        
        if ((pacmanY/GRID_SIZE) == 14 && (pacmanX/GRID_SIZE) == 26) {
            pacmanX = 1*GRID_SIZE;
        }
        if ((pacmanY/GRID_SIZE) == 14 && (pacmanX/GRID_SIZE) == 0) {
            pacmanX = 25*GRID_SIZE;
        }

        // Check if the next position is within the boundaries
        if (nextX >= 0 && nextX < WIDTH && nextY >= 0 && nextY < HEIGHT) {
            // Check if the next position is a wall
            int gridX = nextX / GRID_SIZE;
            int gridY = nextY / GRID_SIZE;

            if (mapObjects[gridY][gridX] == 0 || mapObjects[gridY][gridX] == 2) { // fixed the indexing here
                pacmanX = nextX;
                pacmanY = nextY;

                if(mapObjects[gridY][gridX] == 2) {
                    mapObjects[gridY][gridX] = 0;
                    score = score + 10;
                }
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

        // Draw Maze (mapObjects)
        g.setColor(Color.BLUE);
        for (int x = 0; x < mapObjects.length; x++) {
            for (int y = 0; y < mapObjects[x].length; y++) {
                if (mapObjects[x][y] == 1) { // fixed the indexing here
                    g.fillRect(y * GRID_SIZE, x * GRID_SIZE, GRID_SIZE, GRID_SIZE); // fixed the order of x and y
                }
            }
        }

        // Draw Objects (mapObjects)
        g.setColor(Color.YELLOW);
        for (int x = 0; x < mapObjects.length; x++) {
            for (int y = 0; y < mapObjects[x].length; y++) {
                if (mapObjects[x][y] == 2) { // fixed the indexing here
                    g.fillOval((y * GRID_SIZE)+8, (x * GRID_SIZE)+8, GRID_SIZE/4, GRID_SIZE/4); // fixed the order of x and y
                }
            }
        }

        g.setColor(Color.PINK);
        for (int x = 0; x < mapObjects.length; x++) {
            for (int y = 0; y < mapObjects[x].length; y++) {
                if (mapObjects[x][y] == 3) { // fixed the indexing here
                    g.fillRect((y * GRID_SIZE), x * GRID_SIZE, GRID_SIZE, GRID_SIZE/8); // fixed the order of x and y
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
