import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Board extends JPanel /*implements KeyListener, MouseListener*/ {
    static final int WIDTH = 560;
    static final int HEIGHT = 700;
    static final int GRID_SIZE = 20;
    static final int PACMAN_RADIUS = 10;
    static boolean exitClicked = false;
    static boolean menu = true;

    //
    private Image menuImage;

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

    static ArrayList<Ghost> gList = new ArrayList<>();

    //0 = empty space, 1 = wall, 2 = pellet, 3 = ghost gate
    static int[][] mapData = {
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

    static int[][] mapObjects = new int[mapData.length][mapData[0].length];

    public Board() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        // Add key listener
        setFocusable(true);
        addKeyListener(new Controller());
        addMouseListener(new Controller());

        // Load the Menu, Pacman and Ghost images
        try {
            menuImage = ImageIO.read(new File("img/titlescreen.jpg"));

            pacmanImage = ImageIO.read(new File("img/pacman.jpg"));
            pacmanUpImage = ImageIO.read(new File("img/pacmanup.jpg"));
            pacmanDownImage = ImageIO.read(new File("img/pacmandown.jpg"));
            pacmanLeftImage = ImageIO.read(new File("img/pacmanleft.jpg"));
            pacmanRightImage = ImageIO.read(new File("img/pacmanright.jpg"));

            redLeft = ImageIO.read(new File("img/RedLeft.jpg"));
            redRight = ImageIO.read(new File("img/RedRight.jpg"));
            blueLeft = ImageIO.read(new File("img/BlueLeft.jpg"));
            blueRight = ImageIO.read(new File("img/BlueRight.jpg"));
            orangeLeft = ImageIO.read(new File("img/OrangeLeft.jpg"));
            orangeRight = ImageIO.read(new File("img/OrangeRight.jpg"));
            pinkLeft = ImageIO.read(new File("img/PinkLeft.jpg"));
            pinkRight = ImageIO.read(new File("img/PinkRight.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Ghost redGhost = new Ghost(redLeft, redRight, 260, 300);
        gList.add(redGhost);
        Ghost blueGhost = new Ghost(blueLeft, blueRight, 280, 300);
        gList.add(blueGhost);
        Ghost orangeGhost = new Ghost(orangeLeft, orangeRight, 260, 280);
        gList.add(orangeGhost);
        Ghost pinkGhost = new Ghost(pinkLeft, pinkRight, 280, 280);
        gList.add(pinkGhost);

        Pacman pacman = new Pacman(pacmanLeftImage, pacmanRightImage, pacmanUpImage, pacmanDownImage, pacmanImage);
    }

    public static void updateGame() {
        Pacman.teleportPacman();
        Pacman.validPacmanMove();
        for (int i = 0; i < gList.size(); i++) {
            Ghost aGhost = gList.get(i);
            aGhost.ghostMove();
        }
        Pacman.pickPellet();
        exit();
    }

    public static void exit() {
        // Check if the "Exit" text was clicked
        if (exitClicked) {
            System.exit(0);
        }
    }

    //-----------------------------------PAINT STUFF--------------------------------------//

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (menu) {
        //Draw Menu
        g.drawImage(menuImage, 0, 0, WIDTH, HEIGHT, this);
        } else {


        // Draw Pacman based on the current direction
        Image pacImage = pacmanImage;
        switch (Pacman.pacmanDirection) {
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

        g.drawImage(pacImage, Pacman.pacmanX, Pacman.pacmanY, PACMAN_RADIUS * 2, PACMAN_RADIUS * 2, this);

        
        // Draw Ghost based on the current direction for all 4 ghosts
        for (int i = 0; i < gList.size(); i++) {
            Ghost aGhost = gList.get(i);

            Image ghostImage = aGhost.getGhostLeft();
            switch (aGhost.getGhostDirection()) {
                case 0:
                    ghostImage = aGhost.getGhostLeft();
                    break;
                case 1:
                    ghostImage = aGhost.getGhostRight();
                break;
            }

            g.drawImage(ghostImage, aGhost.getGhostX(), aGhost.getGhostY(), PACMAN_RADIUS * 2, PACMAN_RADIUS * 2, this); 
        }
        

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
        g.drawString("Score: " + Game.score, 10, HEIGHT - 20);

        // Draw the "Exit" text at the bottom right
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Exit", WIDTH - 70, HEIGHT - 20);
    }
}
    //---------------------------------PAINT STUFF ENDS-----------------------------------//
}