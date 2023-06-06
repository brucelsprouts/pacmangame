

public class Ghost extends Mover {

    char direction; //U = up, D = down, L = Left, R = Right

    int x; //x position
    int y; //y position

    int lastX; //old x position
    int lastY; //old y position
    
    public Ghost(int x, int y) {

    }

    public void move() {
        lastX = x;
        lastY = y;
    
        if () {
          direction = newDirection();
        }
    
        switch (direction) {
        case 'L':
          if (isValidDest(x - increment, y)) x -= increment;
          break;
        case 'R':
          if (isValidDest(x + gridSize, y)) x += increment;
          break;
        case 'U':
          if (isValidDest(x, y - increment)) y -= increment;
          break;
        case 'D':
          if (isValidDest(x, y + gridSize)) y += increment;
          break;
        }
    }

    private char newDirection() {
        return 0;
    }

    public void updatePellet() {
        int tempX, tempY;
        int lastPelletX, lastPelletY;
        int pelletX, pelletY;

        tempX = x / gridSize - 1;
        tempY = y / gridSize - 1;
        if (tempX != pelletX || tempY != pelletY) {
          lastPelletX = pelletX;
          lastPelletY = pelletY;
          pelletX = tempX;
          pelletY = tempY;
        }
    
      }


}
