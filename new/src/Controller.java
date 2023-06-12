import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements KeyListener, MouseListener {
    
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_RIGHT:
                Pacman.changePacmanDirection(0);
                break;
            case KeyEvent.VK_DOWN:
                Pacman.changePacmanDirection(1);
                break;
            case KeyEvent.VK_LEFT:
                Pacman.changePacmanDirection(2);
                break;
            case KeyEvent.VK_UP:
                Pacman.changePacmanDirection(3);
                break;
            case KeyEvent.VK_SPACE:
                if (Board.menu) {
                    Board.menu = false;
                }
        }
    }

    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Check if the mouse is within the bounds of the "Exit" text
        if (mouseX >= Board.WIDTH - 80 && mouseX <= Board.WIDTH - 20 &&
                mouseY >= Board.HEIGHT - 40 && mouseY <= Board.HEIGHT - 20) {
            Board.exitClicked = true;
        }
    }

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

}
