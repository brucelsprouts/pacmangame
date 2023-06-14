import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {
    private Board board;

    static int pacmanLives = 1;
    static int score = 0;
    static int finalScore = 0;
    static int counter = 0;
    static int highScore = 0;
    private int count = 0;

    public Game() {
        setTitle("Pacman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        for (int i = 0; i < Board.mapData.length; i++) {
            for (int j = 0; j < Board.mapData[i].length; j++) {
                Board.mapObjects[i][j] = Board.mapData[i][j];
            }
        }
        board = new Board();
        add(board);
        pack();
        setVisible(true);
        
    }

    public void run() {
        while (true) {
            while (Board.menu) {
                repaint();
            }
            if (pacmanLives <= 0) {
                Board.gameOver = true;
                if (count == 0) {
                    finalScore = score;
                    updateScore();
                    count++;
                }
            }
            Board.updateGame();
            repaint();
            if (death()) {
                try {
                    Thread.sleep(3000);
                    Board.gList.clear();
                    board = new Board();
                    Pacman.pacmanX = 260;
                    Pacman.pacmanY = 460;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (counter >= 244 && counter % 244 == 0) {
                try {
                    Thread.sleep(3000);
                    Board.gList.clear();
                    for (int i = 0; i < Board.mapData.length; i++) {
                        for (int j = 0; j < Board.mapData[i].length; j++) {
                        Board.mapObjects[i][j] = Board.mapData[i][j];
                        }
                    }
                    board = new Board();
                    Pacman.pacmanX = 260;
                    Pacman.pacmanY = 460;
                    counter = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(175); // Adjust the game speed by changing the sleep duration
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void updateScore() {
        List<Integer> topScores = new ArrayList<>();
        // Read scores from the text file
        try (BufferedReader reader = new BufferedReader(new FileReader("StoredScores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String currScore = line.trim();
                    int parsedScore;
                    try {
                        parsedScore = Integer.parseInt(currScore);
                        topScores.add(parsedScore);
                    } catch (Throwable e) {

                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read scores from the file: " + e.getMessage());
        }

        // Add the newest score
        topScores.add(finalScore);

        // Sort the scores in descending order
        Collections.sort(topScores, Collections.reverseOrder());

        // Trim the list to keep only the top 10 scores
        if (topScores.size() > 10) {
            topScores = topScores.subList(0, 10);
        }

        // Output the leaderboard to the text file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("LeaderBoard.txt"));
             BufferedWriter writer2 = new BufferedWriter(new FileWriter("StoredScores.txt"))) {
            // Write the top scores
            writer.write("|-------------LeaderBoard-------------|\n");
            writer2.write("LeaderBoard:\n");
            for (int i = 0; i < topScores.size(); i++) {
                writer.write("\t\t" + (i+1) + ". " + topScores.get(i) + "\n");
                writer2.write(topScores.get(i) + "\n");
            }
            writer.write("|-------------------------------------|\n");

            // Write the newest score
            writer.write("\nNewest Score: " + finalScore);
            writer2.write("\nNewest Score: " + finalScore);
        } catch (IOException e) {
            System.err.println("Failed to write to the file: " + e.getMessage());
        }

        System.out.println("Leaderboard generated and saved to LeaderBoard.txt");
    }

    private static boolean death() {
        for (int i = 0; i < Board.gList.size(); i++) {
            Ghost aGhost = Board.gList.get(i);
            if ((Pacman.pacmanX == aGhost.getGhostX() && Pacman.pacmanY == aGhost.getGhostY()) || 
                    (Pacman.prevX == aGhost.getGhostX() && Pacman.prevY == aGhost.getGhostY())) {
                pacmanLives--;
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        Game game = new Game();
        Thread thread = new Thread(game);
        thread.start();
    }
}