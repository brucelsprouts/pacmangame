/* Classname: Controller
 * Created By: Hassaan Sabir and Bruce Lin
 * Last Modified: 2023/06/13
 * Description: Detects when a key necessary for controller pacman is
 *              pressed, and if the mouse is pressed over the exit button
 * */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements KeyListener, MouseListener {
    
    //Check if a key is pressed
    public void keyPressed(KeyEvent e) {
        int input = e.getKeyCode();

        //change pacmans direction depending on which key is pressed
        switch (input) {
            case KeyEvent.VK_RIGHT: //Right Arrow Key
                Pacman.changePacmanDirection(0);
                break;
            case KeyEvent.VK_DOWN: //Down Arrow Key
                Pacman.changePacmanDirection(1);
                break;
            case KeyEvent.VK_LEFT: //Left Arrow Key
                Pacman.changePacmanDirection(2);
                break;
            case KeyEvent.VK_UP: //Up Arrow Key
                Pacman.changePacmanDirection(3);
                break;

            //check if the space bar is pressed
            case KeyEvent.VK_SPACE: //Space bar
                //if the menu is being shown, remove it
                if (Board.menu) {
                    Board.menu = false;
                }//if
        }//switch
    }//end of keyPressed

    //check if the mouse is pressed
    public void mousePressed(MouseEvent e) {
        //find the location for where the mosue was pressed
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Check if the mouse is within the bounds of the "Exit" text
        if (mouseX >= Board.WIDTH - 80 && mouseX <= Board.WIDTH - 20 &&
                mouseY >= Board.HEIGHT - 40 && mouseY <= Board.HEIGHT - 20) {
            //if clicked on the "Exit" text set exitClicked to true
            Board.exitClicked = true;
        }//if

        //When pacman has no lives allow a button to be clicked to show the leaderboard
        if (Game.pacmanLives <= 0) {
            if (mouseX >= Board.WIDTH - 370 && mouseX <= Board.WIDTH - 190 &&
                    mouseY >= Board.HEIGHT - 40 && mouseY <= Board.HEIGHT - 20) {
                // if clicked on the "Show LeaderBoard" text set showLeaderBoard to true
                Board.showLeaderBoard = true;
            }//if

            if (mouseX >= Board.WIDTH - 100 && mouseX <= Board.WIDTH - 20 &&
                    mouseY >= Board.HEIGHT - 70 && mouseY <= Board.HEIGHT - 50) {
                // if clicked on the "Restart" text set restart to true
                Game.restart = true;
            }//if
        }//if
    }//end of mousePressed

    public void mouseClicked(MouseEvent e) { 
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) { 
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

}//end of class
