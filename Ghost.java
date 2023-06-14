/* Classname: Ghost
 * Created By: Hassaan Sabir and Bruce Lin
 * Last Modified: 2023/06/13
 * Description: Creates a ghost object, and uses an Ai to determine
 *              where the object is allowed to move, by providing collision detection
 * */

import java.awt.*;

public class Ghost {

  private int ghostDirection; // 0 = Right, 1 = Down, 2 = Left, 3 = Up
  private int ghostX;         // x position
  private int ghostY;         // y position
  
  //Ghost Images
  private Image ghostLeft;
  private Image ghostRight;
  private boolean ghostMove;

  //Constructor for the ghosts
  public Ghost(Image leftImage, Image rightImage, int x, int y) {
    this.ghostX = x;
    this.ghostY = y - 20;
    this.ghostLeft = leftImage;
    this.ghostRight = rightImage;
    this.ghostDirection = 3;
  }

  //teleport ghosts if they enter the teleporters
  public void teleportGhost() {
    if (this.ghostX == 0 * Board.GRID_SIZE && this.ghostY == 14 * Board.GRID_SIZE) {
      this.ghostX = 26 * Board.GRID_SIZE;
    }
    if (this.ghostX == 27 * Board.GRID_SIZE && this.ghostY == 14 * Board.GRID_SIZE) {
      this.ghostX = 1 * Board.GRID_SIZE;
    }
  }//end of teleportGhost

  //Determine if the ghosts move is valid
  public void validGhostMove() {
    // Calculate the next position based on the current direction
    int nextX = this.ghostX;
    int nextY = this.ghostY;

    //Find the next position
    switch (this.ghostDirection) {
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
        (Board.mapObjects[nextY / Board.GRID_SIZE][nextX / Board.GRID_SIZE] != 1)
        && (Board.mapObjects[nextY / Board.GRID_SIZE][nextX / Board.GRID_SIZE] != 3)) {
      //if the move is valid, set the ghosts position to the new position
      this.ghostX = nextX;
      this.ghostY = nextY;

      //let the program know the ghost moved
      this.ghostMove = true;
    } else {
      //let the program know the ghost did not move (find another possible move)
      this.ghostMove = false;
    }
  }//end of validGhostMove

  //Make sure the ghost can not move backwards
  private int noBackwardGhostDirection() {
    int newDirection = 0;

    //set a random direction, and make sure it is not oposite of the current direction
    switch (this.ghostDirection) {
      case 0: // Right
        newDirection = (int) (Math.random() * 3);
        //if next move is right, left can't be a move
        switch (newDirection) {
          case 0:
            newDirection = 0;
            break;
          case 1:
            newDirection = 1;
            break;
          case 2:
            newDirection = 3;
            break;
        }
        break;
      case 1: // Down
        newDirection = (int) (Math.random() * 3);
        //if next move is down, up can't be a move
        switch (newDirection) {
          case 0:
            newDirection = 0;
            break;
          case 1:
            newDirection = 1;
            break;
          case 2:
            newDirection = 2;
            break;
        }
        break;
      case 2: // Left
        newDirection = (int) (Math.random() * 3);
        //if next move is left, right can't be a move
        switch (newDirection) {
          case 0:
            newDirection = 1;
            break;
          case 1:
            newDirection = 2;
            break;
          case 2:
            newDirection = 3;
            break;
        }
        break;
      case 3: // Up
        newDirection = (int) (Math.random() * 3);
        //if next move is up, down can't be a move
        switch (newDirection) {
          case 0:
            newDirection = 0;
            break;
          case 1:
            newDirection = 2;
            break;
          case 2:
            newDirection = 3;
            break;
        }
        break;
    }//switch

    return newDirection;
  }//end of noBackwardGhostDirection

  //Changes the ghosts direction
  public void changeGhostDirection(int newDirection) {
    int tempX = this.ghostX;
    int tempY = this.ghostY;

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

    //check if the new position is valid (not a wall)
    if (tempX >= 0 && tempX < Board.WIDTH && tempY >= 0 && tempX < Board.HEIGHT &&
        Board.mapObjects[tempY / Board.GRID_SIZE][tempX / Board.GRID_SIZE] != 1) {
      //set the ghosts direction to the new direction
      this.ghostDirection = newDirection;
    }
  }//end of changeGhostDirection

  int count = 0; //counts the first 4 moves

  //Method moves the ghosts
  public void ghostMove() {
    //if count is less than 4 move ghosts in a set pattern
    if (count < 4) {
      ghostStart();
      count++;
    
    //once the counter is greater than 4 ghosts begin moving themselves
    } else {
      this.ghostMove = false;
      //if the ghost has not moved in its turn, give it a new direction until it does move
      //prevent ghosts standing still
      while (this.ghostMove != true) {
        //moves the ghosts by themselves
        changeGhostDirection(noBackwardGhostDirection());
        validGhostMove();
      }//while

      //check if ghost needs to teleport
      teleportGhost();
    }//if/else
  }//end of ghostMove

  //First 4 moves for each ghost is pre programmed
  private void ghostStart() {
    int nextX = this.ghostX;
    int nextY = this.ghostY;

    //move ghosts up
    nextY -= Board.GRID_SIZE;

    //allow ghosts to move through ghost gate
    if (nextX >= 0 && nextX < Board.WIDTH && nextY >= 0 && nextY < Board.HEIGHT &&
        (Board.mapObjects[nextY / Board.GRID_SIZE][nextX / Board.GRID_SIZE] != 1)) {
      //change ghosts position to the new position 
      this.ghostX = nextX;
      this.ghostY = nextY;
    }
  }//end of ghostStart

  //returns ghost x
  public int getGhostX() {
    return this.ghostX;
  }//end of getGhostX

  //returns ghost y
  public int getGhostY() {
    return this.ghostY;
  }//end of getGhostY

  //returns ghost direction
  public int getGhostDirection() {
    return this.ghostDirection;
  }//end of getGhostDirection

  //returns ghost left image
  public Image getGhostLeft() {
    return this.ghostLeft;
  }//end of getGhsotLeft

  //returns ghost right image
  public Image getGhostRight() {
    return this.ghostRight;
  }//end of getGhsotRight
}//end of class
