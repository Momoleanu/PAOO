package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GamePanel;
import static utils.Constants.Directions.*;

public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {

        switch(e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
                gamePanel.setMoving(false);
                break;
        }

    }

    public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()) {
            case KeyEvent.VK_W:
                this.gamePanel.setDirection(UP);
                break;
            case KeyEvent.VK_A:
                this.gamePanel.setDirection(LEFT);
                break;
            case KeyEvent.VK_S:
                this.gamePanel.setDirection(DOWN);
                break;
            case KeyEvent.VK_D:
                this.gamePanel.setDirection(RIGHT);
                break;
        }

    }
}
