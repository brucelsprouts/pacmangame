import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class TestFile extends JPanel implements KeyListener, MouseListener {
    private static final int WIDTH = 560;
    private static final int HEIGHT = 700;
    private static final int GRID_SIZE = 20;
    private static final int PACMAN_RADIUS = 10;
    private int highScore = 0; // High score counter
    private boolean exitClicked = false;

    private int pacmanX = 260;
    private int pacmanY = 460;
    private int pacmanDirection = 4; // 0 - right, 1 - down, 2 - left, 3 - up, 4 = start
    private int score = 0;

    private int GhostX = 280;
    private int GhostY = 300;

    //0 = empty space, 1 = wall, 2 = pellet, 3 = ghost gate
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

    //pacman images
    private Image pacmanImage;
    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;

    //ghost images
    private Image redLeft;
    private Image redRight;
    private Image blueLeft;
    private Image blueRight;
    private Image orangeLeft;
    private Image orangeRight;
    private Image pinkLeft;
    private Image pinkRight;

    public TestFile() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        highScore = 0;
        // Add key listener
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        // Load the Pacman and Ghost images
        try {
            pacmanImage = ImageIO.read(getClass().getResource("img/pacman.jpg"));
            pacmanUpImage = ImageIO.read(getClass().getResource("img/pacmanup.jpg"));
            pacmanDownImage = ImageIO.read(getClass().getResource("img/pacmandown.jpg"));
            pacmanLeftImage = ImageIO.read(getClass().getResource("img/pacmanleft.jpg"));
            pacmanRightImage = ImageIO.read(getClass().getResource("img/pacmanright.jpg"));

            redLeft = ImageIO.read(getClass().getResource("img/RedLeft.jpg"));
            redRight = ImageIO.read(getClass().getResource("img/RedRight.jpg"));
            blueLeft = ImageIO.read(getClass().getResource("img/BlueLeft.jpg"));
            blueRight = ImageIO.read(getClass().getResource("img/BlueRight.jpg"));
            orangeLeft = ImageIO.read(getClass().getResource("img/OrangeLeft.jpg"));
            orangeRight = ImageIO.read(getClass().getResource("img/OrangeRight.jpg"));
            pinkLeft = ImageIO.read(getClass().getResource("img/PinkLeft.jpg"));
            pinkRight = ImageIO.read(getClass().getResource("img/PinkRight.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Start the game loop
        Thread gameLoop = new Thread(() -> {
            while (true) {
                updateGame();
                repaint();

                try {
                    Thread.sleep(185); // Adjust the game speed by changing the sleep duration
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        gameLoop.start();
    }

    public void updateGame() {
        teleportPacman();
        validPacmanMove();
        pickPellet();
        
        // Check if the "Exit" text was clicked
        if (exitClicked) {
            System.exit(0);
        }
    }

    //------------------------------------PACMAN STUFF------------------------------------------//

    public void teleportPacman() {
        if (pacmanX == 0*GRID_SIZE && pacmanY == 14*GRID_SIZE) {
                pacmanX = 26*GRID_SIZE;
        }
        if (pacmanX == 27*GRID_SIZE && pacmanY == 14*GRID_SIZE) {
                pacmanX = 1*GRID_SIZE;
        }
    }

    public void validPacmanMove() {
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
        
        // Check if the next position is valid (not out of bounds and not a wall or a ghost gate)
        if (nextX >= 0 && nextX < WIDTH && nextY >= 0 && nextY < HEIGHT &&
                (mapObjects[nextY / GRID_SIZE][nextX / GRID_SIZE] != 1 && mapObjects[nextY / GRID_SIZE][nextX / GRID_SIZE] != 3)) {
            pacmanX = nextX;
            pacmanY = nextY;
        }
    }

    public void pickPellet() {
        // Check if the current position contains a pellet (represented by value 2 in mapObjects)
        if (mapObjects[pacmanY / GRID_SIZE][pacmanX / GRID_SIZE] == 2) {
            score++; // Increase the score
            mapObjects[pacmanY / GRID_SIZE][pacmanX / GRID_SIZE] = 0; // Remove the pellet from the map
        
            // Update the high score if the current score is higher
            if (score > highScore) {
                highScore = score;
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
    }

    private void changePacmanDirection(int newDirection) {
        pacmanDirection = newDirection;
    }

    //--------------------------------PACMAN STUFF ENDS-----------------------------------//

    //-----------------------------------GHOST STUFF--------------------------------------//

    public void teleportGhost() {
        if (GhostX == 0*GRID_SIZE && GhostY == 14*GRID_SIZE) {
                GhostX = 26*GRID_SIZE;
        }
        if (GhostX == 27*GRID_SIZE && GhostY == 14*GRID_SIZE) {
                GhostX = 1*GRID_SIZE;
        }
    }


    //---------------------------------GHOST STUFF ENDS-----------------------------------//

    //-----------------------------------PAINT STUFF--------------------------------------//

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw Pacman based on the current direction
        Image pacImage = pacmanImage;
        switch (pacmanDirection) {
            case 0:
                pacImage = pacmanRightImage;
                break;
            case 1:
                pacImage = pacmanDownImage;
                break;
            case 2:
                pacImage = pacmanLeftImage;
                break;
            case 3:
                pacImage = pacmanUpImage;
                break;
            case 4:
                pacImage = pacmanImage;
        }

        g.drawImage(pacImage, pacmanX, pacmanY, PACMAN_RADIUS * 2, PACMAN_RADIUS * 2, this);

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

        // Draw Ghost Gate (mapObjects)
        g.setColor(Color.PINK);
        for (int x = 0; x < mapObjects.length; x++) {
            for (int y = 0; y < mapObjects[x].length; y++) {
                if (mapObjects[x][y] == 3) {
                    g.fillRect(y * GRID_SIZE, x * GRID_SIZE, GRID_SIZE, GRID_SIZE/10);
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

    //---------------------------------PAINT STUFF ENDS-----------------------------------//

    //---------------------------------CONTROLLER STUFF-----------------------------------//

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                changePacmanDirection(0);
                break;
            case KeyEvent.VK_DOWN:
                changePacmanDirection(1);
                break;
            case KeyEvent.VK_LEFT:
                changePacmanDirection(2);
                break;
            case KeyEvent.VK_UP:
                changePacmanDirection(3);
                break;
        }
    }

    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Check if the mouse is within the bounds of the "Exit" text
        if (mouseX >= WIDTH - 80 && mouseX <= WIDTH - 20 &&
                mouseY >= HEIGHT - 40 && mouseY <= HEIGHT - 20) {
            exitClicked = true;
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    //--------------------------------CONTROLLER STUFF ENDS---------------------------------//

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pacman Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new TestFile());
        frame.pack();
        frame.setVisible(true);
    }

}