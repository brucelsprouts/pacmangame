//Class is used to determine if both the ghost and pacman moves are allowed

public class Mover {
    
    boolean[][] state;
    int gridSize;
    int max;
    int increment;
    
    public Mover() {
        gridSize = 20;
        increment = 4;
        max = 400;
        state = new boolean[20][20];
        for (int i = 0; i < 20; i++) {
          for (int j = 0; j < 20; j++) {
            state[i][j] = false;
          }
        }
    }

    
    public boolean isValidDest(int x, int y) {
        return true;
    }
}
