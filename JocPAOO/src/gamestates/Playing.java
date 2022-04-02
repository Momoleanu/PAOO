package gamestates;

import entities.Player;
import levels.LevelHandler;
import main.Game;
import main.Sound;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Playing extends State implements Statemethods {

    private Player player;
    private BufferedImage img;
    private LevelHandler levelHandler;

    public Playing(Game game) {
        super(game);
        initC();
    }

    private void initC() {


        levelHandler = new LevelHandler(game);
        player = new Player(200, 200, (int) (78 * Game.SCALE), (int) (58 * Game.SCALE));
        player.loadLvlData(levelHandler.getCrntLevel().getLevelData());
        Sound s = new Sound(game);

    }

    @Override
    public void update() {
        levelHandler.update();
        player.update();

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, 0, 0, null);
        levelHandler.draw(g);
        player.render(g);

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {

            player.setAttacking(true);
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.setUp(true);
            case KeyEvent.VK_A -> player.setLeft(true);
            case KeyEvent.VK_S -> player.setDown(true);
            case KeyEvent.VK_D -> player.setRight(true);
            case KeyEvent.VK_SPACE -> player.setJump(true);
            //case KeyEvent.VK_BACK_SPACE -> Gamestate.state = Gamestate.MENU;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.setUp(false);
            case KeyEvent.VK_A -> player.setLeft(false);
            case KeyEvent.VK_S -> player.setDown(false);
            case KeyEvent.VK_D -> player.setRight(false);
            case KeyEvent.VK_SPACE -> player.setJump(false);
        }

    }

    public int getKey(KeyEvent e) {
        return e.getKeyCode();
    }

    public Player getPlayer() {
        return player;
    }

    public void windowFocusLost() {
        player.resetBool();
    }

}
