/* Classname: Pacman
 * Created By: Hassaan Sabir and Bruce Lin
 * Last Modified: 2023/06/13
 * Description: Creates a pacman object, and controls where the object
 *              is allowed to move, by providing collision detection
 * */

 
import java.awt.*;

public class Pacman {
    static int pacmanDirection = 4; // 0 = Right, 1 = Down, 2 = Left, 3 = Up, 4 = Start
    static int pacmanX = 260; // x position
    static int pacmanY = 460; // y position
    static int prevX;         // pacman previous x position
    static int prevY;         // pacman previous y position

    //pacman images
    static Image pacmanLeft;
    static Image pacmanRight;
    static Image pacmanUp;
    static Image pacmanDown;
    static Image pacman;

    //constructor for pacman
    public Pacman(Image leftImage, Image rightImage, Image upImage, Image downImage, Image pacmanStill) {
        pacmanLeft = leftImage;
        pacmanRight = rightImage;
        pacmanDown = downImage;
        pacmanUp = upImage;
        pacman = pacmanStill;
    }

    //teleport pacman if he reachs one of the teleporters
    public static void teleportPacman() {
        if (pacmanX == 0*Board.GRID_SIZE && pacmanY == 14*Board.GRID_SIZE) {
                pacmanX = 26*Board.GRID_SIZE;
        }
        if (pacmanX == 27*Board.GRID_SIZE && pacmanY == 14*Board.GRID_SIZE) {
                pacmanX = 1*Board.GRID_SIZE;
        }
    }//end of teleportPacman

    //Determine of the move is valid for pacman
    public static void validPacmanMove() {
        // Calculate the next position based on the current direction
        int nextX = pacmanX;
        int nextY = pacmanY;
        prevY = pacmanY;
        prevX = pacmanX;

        //check where pacmans next position on the board is based on his direction
        switch (pacmanDirection) {
            case 0: // Right
                nextX += Board.GRID_SIZE;
                break;
            case 1: // Down
                nextY += Board.GRID_SIZE;
                break;
            case 2: // Left
                nextX -= Board.GRID_SIZE;
                break;
            case 3: // Up
                nextY -= Board.GRID_SIZE;
                break;
        }
        
        // Check if the next position is valid (not out of bounds and not a wall or a ghost gate)
        if (nextX >= 0 && nextX < Board.WIDTH && nextY >= 0 && nextY < Board.HEIGHT &&
                (Board.mapObjects[nextY / Board.GRID_SIZE][nextX / Board.GRID_SIZE] != 1 && Board.mapObjects[nextY / Board.GRID_SIZE][nextX / Board.GRID_SIZE] != 3)) {
            //if next position is valid, set pacmans position to the new ones
            pacmanX = nextX;
            pacmanY = nextY;
        }
    }//end of validPacmanMove

    //Pacman picks up pellets
    public static void pickPellet() {
        // Check if the current position contains a pellet (represented by value 2 in mapObjects)
        if (Board.mapObjects[pacmanY / Board.GRID_SIZE][pacmanX / Board.GRID_SIZE] == 2) {
            Game.score++; // Increase the score
            Game.pelletCounter++; //increase pellet counter
            Board.mapObjects[pacmanY / Board.GRID_SIZE][pacmanX / Board.GRID_SIZE] = 0; // Remove the pellet from the map
        }
    }//end of pickPellet

    //change pacmans direction using information from the controller
    public static void changePacmanDirection(int newDirection) {
        int tempX = pacmanX;
        int tempY = pacmanY;

        switch (newDirection) {
            case 0: // Right
                tempX += Board.GRID_SIZE;
                break;
            case 1: // Down
                tempY += Board.GRID_SIZE;
                break;
            case 2: // Left
                tempX -= Board.GRID_SIZE;
                break;
            case 3: // Up
                tempY -= Board.GRID_SIZE;
                break;
        }

        //check if the next location is a wall
        if (tempX >= 0 && tempX < Board.WIDTH && tempY >= 0 && tempX < Board.HEIGHT &&
              Board.mapObjects[tempY / Board.GRID_SIZE][tempX / Board.GRID_SIZE] != 1) {
            //if the next location is not a wall let pacman change directions
            pacmanDirection = newDirection;
        }//if
    }//end of changePacmanDirection
}//end of class
