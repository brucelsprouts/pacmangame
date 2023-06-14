/* Classname: Game
 * Created By: Hassaan Sabir and Bruce Lin
 * Last Modified: 2023/06/13
 * Description: Creates the game, and runs a continuous thread to update the
 *              game until the program is closed
 * */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {
    private Board board;

    static int pacmanLives = 3;     //Determines how many lives pacman has
    static int score = 0;           //The score pacman has as he collects pellets
    static int finalScore = 0;      //The final score when pacman dies
    static int pelletCounter = 0;   //Counts pellets to determine if all on screen are collected
    private int count = 0;          //Tracking if game is over

    //create the board and set the map objects on the board
    public Game() {
        setTitle("Pacman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        for (int i = 0; i < Board.mapData.length; i++) {
            for (int j = 0; j < Board.mapData[i].length; j++) {
                Board.mapObjects[i][j] = Board.mapData[i][j];
            }//for
        }//for

        board = new Board();
        add(board);
        pack();
        setVisible(true);
    }//end of Game

    //runs thread
    public void run() {
        while (true) {
            //Until space is pressed to start the game remain on the menu screen
            while (Board.menu) {
                repaint();
            }//while

            //if pacman runs out of lives go to game over screen
            if (pacmanLives <= 0) {
                Board.gameOver = true;

                //prevent score from increasing when game ends
                if (count == 0) {
                    finalScore = score;
                    //update score in the files
                    updateScoreFile();
                    count++;
                }//if
            }//if

            //update and repaint board
            Board.updateGame();
            repaint();

            //if pacman dies, reset only ghosts and pacman
            if (death()) {
                try {
                    Thread.sleep(3000);

                    //reset ghosts
                    Board.gList.clear();

                    //create new board
                    board = new Board();

                    //reset pacman position
                    Pacman.pacmanX = 260;
                    Pacman.pacmanY = 460;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }//try/catch
            }//if

            //If all pellets are collected reset the board, but keep score
            if (pelletCounter >= 244 && pelletCounter % 244 == 0) {
                try {
                    Thread.sleep(3000);

                    //reset ghosts
                    Board.gList.clear();

                    //reset mapobjects
                    for (int i = 0; i < Board.mapData.length; i++) {
                        for (int j = 0; j < Board.mapData[i].length; j++) {
                        Board.mapObjects[i][j] = Board.mapData[i][j];
                        }//for
                    }//for

                    //create new board
                    board = new Board();

                    //reset pacman
                    Pacman.pacmanX = 260;
                    Pacman.pacmanY = 460;

                    //set pellet counter to 0
                    pelletCounter = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }//try/catch
            }//if

            try {
                Thread.sleep(175); // Adjust the game speed by changing the sleep duration
            } catch (InterruptedException e) {
                e.printStackTrace();
            }//try/catch
        }//while
    }//end of run

    //Used to updated the score files when the game ends
    private static void updateScoreFile() {
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
                    }//try/catch
                }//if
            }//while
        } catch (IOException e) {
            System.err.println("Failed to read scores from the file: " + e.getMessage());
        }//try/catch

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

            //Output scores to the leaderboard file and stored scores file
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
        }//try/catch
    }//end of updateScoreFile

    //if Pacman and a Ghost touch Pacman dies and he loses a life, return true if pacman has died
    private static boolean death() {
        //check each ghost position and pacman position
        for (int i = 0; i < Board.gList.size(); i++) {
            Ghost aGhost = Board.gList.get(i);
            if ((Pacman.pacmanX == aGhost.getGhostX() && Pacman.pacmanY == aGhost.getGhostY()) || 
                    (Pacman.prevX == aGhost.getGhostX() && Pacman.prevY == aGhost.getGhostY())) {
                //remove a life from pacman
                pacmanLives--;
                return true;
            }
        }
        return false;
    }//end of death

    //main creates game and starts the thread
    public static void main(String[] args) {
        Game game = new Game();
        Thread thread = new Thread(game);
        thread.start();
    }//end of main
}//end of class