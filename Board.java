/* Classname: Board
 * Created By: Hassaan Sabir and Bruce Lin
 * Last Modified: 2023/06/13
 * Description: Creates the board, and draws all the required images
 *              onto the board including pacman, the 4 ghosts, maze walls,
 *              pellets, and text
 * */

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Board extends JPanel {
    static final int WIDTH = 560;           //Width of board
    static final int HEIGHT = 700;          //Height of board
    static final int GRID_SIZE = 20;        //Size of each grid
    static final int PACMAN_RADIUS = 10;    //Pacman's size
    static boolean exitClicked = false;     //if exit button is clicked
    static boolean menu = true;             //if menu screen should be shown
    static boolean gameOver = false;        //if game over screen should be shown
    static boolean showLeaderBoard = false; //if leaderboard should be shown

    //Menu and GameOver Image
    private Image menuImage;
    private Image gameOverImage;

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

    //arraylist to store information about the 4 ghosts
    static ArrayList<Ghost> gList = new ArrayList<Ghost>();

    //Shows information for every grid on the map
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

    //Clone mapData to mapObjects
    static int[][] mapObjects = new int[mapData.length][mapData[0].length];

    //Creates the Board
    public Board() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        // Add key listener
        setFocusable(true);
        addKeyListener(new Controller());
        addMouseListener(new Controller());

        // Load the Menu, Pacman and Ghost images
        try {
            //Menu/GameOver Images
            menuImage = ImageIO.read(new File("img/titlescreen.jpg"));
            gameOverImage = ImageIO.read(new File("img/gameover.jpg"));

            //Pacman Images
            pacmanImage = ImageIO.read(new File("img/pacman.jpg"));
            pacmanUpImage = ImageIO.read(new File("img/pacmanup.jpg"));
            pacmanDownImage = ImageIO.read(new File("img/pacmandown.jpg"));
            pacmanLeftImage = ImageIO.read(new File("img/pacmanleft.jpg"));
            pacmanRightImage = ImageIO.read(new File("img/pacmanright.jpg"));

            //Ghost Images
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

        //Create all 4 ghosts objects
        Ghost redGhost = new Ghost(redLeft, redRight, 260, 300);
        Ghost blueGhost = new Ghost(blueLeft, blueRight, 280, 300);
        Ghost orangeGhost = new Ghost(orangeLeft, orangeRight, 260, 280);
        Ghost pinkGhost = new Ghost(pinkLeft, pinkRight, 280, 280);

        //Add the different ghosts to the array list
        gList.add(redGhost);
        gList.add(blueGhost);
        gList.add(orangeGhost);
        gList.add(pinkGhost);

        //Create pacman
        Pacman pacman = new Pacman(pacmanLeftImage, pacmanRightImage, pacmanUpImage, pacmanDownImage, pacmanImage);
    }//end of Board

    //Updates the game
    public static void updateGame() {
        //moves pacman
        Pacman.teleportPacman();
        Pacman.validPacmanMove();
        Pacman.pickPellet();

        //moves ghosts
        for (int i = 0; i < gList.size(); i++) {
            Ghost aGhost = gList.get(i);
            aGhost.ghostMove();
        }//for

        //exits if exit is clicked
        exit();
    }//end of updateGame

    //// Check if the "Exit" text was clicked
    public static void exit() {
        if (exitClicked) {
            System.exit(0);
        }//if
    }//end of exit

    //-----------------------------------PAINT STUFF--------------------------------------//

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //check if the menu should be drawn
        if (menu) {
            // Draw Menu
            g.drawImage(menuImage, 0, 0, WIDTH, HEIGHT, this);
            
        //check if the game over screen should be drawn
        } else if (gameOver) {
            // Draw Game Over Screen
            g.drawImage(gameOverImage, 0, 0, WIDTH, HEIGHT - 20, this);

            // Draw the final score
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + Game.finalScore, 10, HEIGHT - 20);

            // Draw the "Exit" text at the bottom right
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Exit", WIDTH - 70, HEIGHT - 20);

            // Draw the "Restart" text at the bottom right
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Restart", WIDTH - 100, HEIGHT - 50);

            // Draw the "Show LeaderBoard" text at the bottom
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Show LeaderBoard", WIDTH - 370, HEIGHT - 20);

        //if menu and game over should not be drawn, drawn the actual game
        } else {

            // Draw Pacman based on the current direction
            Image pacImage = pacmanImage;
            switch (Pacman.pacmanDirection) {
                case 0: //right
                    pacImage = pacmanRightImage;
                    break;
                case 1: //down
                    pacImage = pacmanDownImage;
                    break;
                case 2: //left
                    pacImage = pacmanLeftImage;
                    break;
                case 3: //up
                    pacImage = pacmanUpImage;
                    break;
                case 4: //standing still
                    pacImage = pacmanImage;
            }//switch

            //draw pacman using provided direction
            g.drawImage(pacImage, Pacman.pacmanX, Pacman.pacmanY, PACMAN_RADIUS * 2, PACMAN_RADIUS * 2, this);

            // Draw Ghost based on the current direction for all 4 ghosts
            for (int i = 0; i < gList.size(); i++) {
                Ghost aGhost = gList.get(i);

                Image ghostImage = aGhost.getGhostLeft();
                switch (aGhost.getGhostDirection()) {
                    case 0: //left
                        ghostImage = aGhost.getGhostLeft();
                        break;
                    case 1: //right
                        ghostImage = aGhost.getGhostRight();
                        break;
                }//switch

                //draw ghost using provided direction
                g.drawImage(ghostImage, aGhost.getGhostX(), aGhost.getGhostY(), PACMAN_RADIUS * 2, PACMAN_RADIUS * 2, this);
            }//for

            // Draw Maze (mapObjects)
            g.setColor(Color.BLUE);
            for (int x = 0; x < mapObjects.length; x++) {
                for (int y = 0; y < mapObjects[x].length; y++) {
                    if (mapObjects[x][y] == 1) {
                        g.fillRect(y * GRID_SIZE, x * GRID_SIZE, GRID_SIZE, GRID_SIZE);
                    }
                }
            }

            // Draw Pellets (mapObjects)
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
                        g.fillRect(y * GRID_SIZE, x * GRID_SIZE, GRID_SIZE, GRID_SIZE / 10);
                    }
                }
            }

            // Draw Pacman Lives
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Lives: " , 10, HEIGHT - 50);
            for (int i = 1; i < Game.pacmanLives; i++) {
                g.drawImage(pacmanRightImage, i*30+50, HEIGHT - 70, this);
            }

            // Draw the score counter
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + Game.score, 10, HEIGHT - 20);

            // Draw the "Exit" text at the bottom right
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Exit", WIDTH - 70, HEIGHT - 20);
        }
    }//end of paintComponent
    // ---------------------------------PAINT STUFF ENDS-----------------------------------//
}//end of class