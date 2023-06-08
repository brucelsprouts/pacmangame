import java.awt.*;
import javax.swing.*;

public class Board extends JPanel {
     

    //Wall = 0, Right Wall = 3, Left Wall = 4, Up Wall = 5, Down Wall = 6, Pellet = 20
    private int[] mapData;
    private int rows, columns;




    protected void drawBoard(Graphics g) {
        super.paintComponent(g);

        int tileSize = 20;  // Size of each tile in pixels

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int index = row * columns + col;
                int element = mapData[index];
                int x = col * tileSize;
                int y = row * tileSize;

                switch (element) {
                    case 0:
                        g.setColor(Color.BLUE);  // Wall
                        break;
                    case 3:
                        g.setColor(Color.RED);  // Right Wall
                        break;
                    case 4:
                        g.setColor(Color.RED);  // Left Wall
                        break;
                    case 5:
                        g.setColor(Color.RED);  // Up Wall
                        break;
                    case 6:
                        g.setColor(Color.RED);  // Down Wall
                        break;
                    case 20:
                        g.setColor(Color.WHITE);  // Pellet
                        break;
                    default:
                        g.setColor(Color.BLACK);  // Empty space
                        break;
                }

                g.fillRect(x, y, tileSize, tileSize);
            }
        }
    }

    public Board(int[] mapData, int rows, int columns) {
        this.mapData = mapData;
        this.rows = rows;
        this.columns = columns;
    }

    public static void main(String[] args) {
        int mapData[] = {
            29, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 28,
            24, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 23,
            24, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 23,
            24, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 23,
            24, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 23,
            24, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 23,
            20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 23,
            24, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 23,
            20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 23,
            24, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 23,
            24, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 23,
            24, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 23,
            24, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 23,
            24, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 23,
            30, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 29,
        };

        int rows = 12;
        int columns = 15;

        JFrame frame = new JFrame("Pac Man");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(columns * 50, rows * 50);  // Adjust the window size based on map dimensions
        frame.add(new Board(mapData, rows, columns));
        frame.setVisible(true);

    }

}
