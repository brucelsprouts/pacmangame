import java.awt.*;

public class Ghost {

  private int ghostDirection; // 0 = Right, 1 = Down, 2 = Left, 3 = Up
  private int ghostX; // x position
  private int ghostY; // y position
  private Image ghostLeft;
  private Image ghostRight;
  private boolean ghostMove;

  public Ghost(Image leftImage, Image rightImage, int x, int y) {
    this.ghostX = x;
    this.ghostY = y - 20;
    this.ghostLeft = leftImage;
    this.ghostRight = rightImage;
    this.ghostDirection = 3;
  }

  public void teleportGhost() {
    if (this.ghostX == 0 * Board.GRID_SIZE && this.ghostY == 14 * Board.GRID_SIZE) {
      this.ghostX = 26 * Board.GRID_SIZE;
    }
    if (this.ghostX == 27 * Board.GRID_SIZE && this.ghostY == 14 * Board.GRID_SIZE) {
      this.ghostX = 1 * Board.GRID_SIZE;
    }
  }

  public void validGhostMove() {
    // Calculate the next position based on the current direction
    int nextX = this.ghostX;
    int nextY = this.ghostY;

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

    // Check if the next position is valid (not out of bounds and not a wall or a
    // ghost gate)
    if (nextX >= 0 && nextX < Board.WIDTH && nextY >= 0 && nextY < Board.HEIGHT &&
        (Board.mapObjects[nextY / Board.GRID_SIZE][nextX / Board.GRID_SIZE] != 1)
        && (Board.mapObjects[nextY / Board.GRID_SIZE][nextX / Board.GRID_SIZE] != 3)) {
      this.ghostX = nextX;
      this.ghostY = nextY;
      this.ghostMove = true;
    } else {
      this.ghostMove = false;
    }
  }

  private int noBackwardGhostDirection() {
    int newDirection = 0;

    switch (this.ghostDirection) {
      case 0: // Right
        newDirection = (int) (Math.random() * 3);
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
    }
    return newDirection;
  }

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

    if (tempX >= 0 && tempX < Board.WIDTH && tempY >= 0 && tempX < Board.HEIGHT &&
        Board.mapObjects[tempY / Board.GRID_SIZE][tempX / Board.GRID_SIZE] != 1) {
      this.ghostDirection = newDirection;
    }
  }

  int count = 0;

  public void ghostMove() {
    if (count < 4) {
      ghostStart();
      count++;
    } else {
      this.ghostMove = false;
      while (this.ghostMove != true) {
        changeGhostDirection(noBackwardGhostDirection());
        validGhostMove();
      }
      teleportGhost();
    }
  }

  private void ghostStart() {
    int nextX = this.ghostX;
    int nextY = this.ghostY;

    nextY -= Board.GRID_SIZE;

    if (nextX >= 0 && nextX < Board.WIDTH && nextY >= 0 && nextY < Board.HEIGHT &&
        (Board.mapObjects[nextY / Board.GRID_SIZE][nextX / Board.GRID_SIZE] != 1)) {
      this.ghostX = nextX;
      this.ghostY = nextY;
    }
  }

  public int getGhostX() {
    return this.ghostX;
  }

  public int getGhostY() {
    return this.ghostY;
  }

  public int getGhostDirection() {
    return this.ghostDirection;
  }

  public Image getGhostLeft() {
    return this.ghostLeft;
  }

  public Image getGhostRight() {
    return this.ghostRight;
  }
}
