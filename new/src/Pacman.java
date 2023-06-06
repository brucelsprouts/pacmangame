import java.util.*;

public class Pacman extends Mover {

    char direction; //U = up, D = down, L = Left, R = Right

    int x; //x position
    int y; //y position

    int lastX; //old x position
    int lastY; //old y position

    public Pacman(int x, int y) {

    }

    public void move() {
      switch (direction) {
      //if move left decrease x value
      case 'L':
        if (isValidDest(x - increment, y)) x -= increment;
        break;
      
      //if move right increase x value
      case 'R':
        if (isValidDest(x + gridSize, y)) x += increment;
        break;
        
      //if move up decrease y value
      case 'U':
        if (isValidDest(x, y - increment)) y -= increment;
        break;
          
      //if move down increase y value
      case 'D':
        if (isValidDest(x, y + gridSize)) y += increment;
        break;
      }
    }
  }

  public void updatePellet() {
    if (x % gridSize == 0 && y % gridSize == 0) {
      pelletX = x / gridSize - 1;
      pelletY = y / gridSize - 1;
    }
  }
}
