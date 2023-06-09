import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class TestFile extends JPanel implements KeyListener {
    private static final int WIDTH = 560;
    private static final int HEIGHT = 700;
    private static final int GRID_SIZE = 20;
    private static final int PACMAN_RADIUS = 10;
    private int highScore = 0; // High score counter
    private boolean exitClicked = false;

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
        {1, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 1,},
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
        highScore = 0;
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

        // Add mouse listener
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                // Check if the mouse is within the bounds of the "Exit" text
                if (mouseX >= WIDTH - 80 && mouseX <= WIDTH - 20 &&
                        mouseY >= HEIGHT - 40 && mouseY <= HEIGHT - 20) {
                    exitClicked = true;
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
                case 0: // Right
                    nextX += GRID_SIZE;
                    break;
                case 1: // Down
                    nextY += GRID_SIZE;
                    break;
                case 2: // Left
                    nextX -= GRID_SIZE;
                    break;
                case 3: // Up
                    nextY -= GRID_SIZE;
                    break;
            }
        
            // Check if the next position is valid (not out of bounds and not a wall)
            if (nextX >= 0 && nextX < WIDTH && nextY >= 0 && nextY < HEIGHT &&
                    mapObjects[nextY / GRID_SIZE][nextX / GRID_SIZE] != 1) {
                pacmanX = nextX;
                pacmanY = nextY;
            }
        
            // Check if the current position contains a pellet (represented by value 2 in mapObjects)
            if (mapObjects[pacmanY / GRID_SIZE][pacmanX / GRID_SIZE] == 2) {
                score++; // Increase the score
                mapObjects[pacmanY / GRID_SIZE][pacmanX / GRID_SIZE] = 0; // Remove the pellet from the map
        
                // Update the high score if the current score is higher
                if (score > highScore) {
                    highScore = score;
                }
            }
        

        // Check if the "Exit" text was clicked
        if (exitClicked) {
            System.exit(0);
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
                if (mapObjects[x][y] == 1) {
                    g.fillRect(y * GRID_SIZE, x * GRID_SIZE, GRID_SIZE, GRID_SIZE);
                }
            }
        }

        // Draw Objects (mapObjects)
        g.setColor(Color.YELLOW);
        for (int x = 0; x < mapObjects.length; x++) {
            for (int y = 0; y < mapObjects[x].length; y++) {
                if (mapObjects[x][y] == 2) {
                    g.fillOval((y * GRID_SIZE) + 8, (x * GRID_SIZE) + 8, GRID_SIZE / 4, GRID_SIZE / 4);
                }
            }
        }

        // Draw the high score counter
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("High Score: " + highScore, 10, HEIGHT - 20);

        // Draw the "Exit" text at the bottom right
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Exit", WIDTH - 70, HEIGHT - 20);
        
    }

    private void changeDirection(int newDirection) {
        pacmanDirection = newDirection;
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                changeDirection(0);
                break;
            case KeyEvent.VK_DOWN:
                changeDirection(1);
                break;
            case KeyEvent.VK_LEFT:
                changeDirection(2);
                break;
            case KeyEvent.VK_UP:
                changeDirection(3);
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pacman Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new TestFile());
            frame.pack();
            frame.setVisible(true);
        });
    }
}
