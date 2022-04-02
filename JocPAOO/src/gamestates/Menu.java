package gamestates;

import main.Game;
import ui.MenuButton;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements Statemethods {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImg;
    private BufferedImage bgImg2;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();

    }

    private void loadBackground() {
        backgroundImg = LoadSave.getPlayerSprite(LoadSave.MENU_BG);
        bgImg2 = LoadSave.getPlayerSprite(LoadSave.BG);
        menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 + 25 - menuWidth / 2;
        menuY = (int) (45 * Game.SCALE);

    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 2, Gamestate.QUIT);
    }

    @Override
    public void update() {
        for (MenuButton m : buttons)
            m.update();
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(bgImg2, 0, 0, null);
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        for (MenuButton m : buttons)
            m.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton m : buttons) {
            if (isPressed(e, m)) {
                m.setMousePressed(true);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton m : buttons) {
            if (isPressed(e, m)) {
                if (m.isMousePressed())
                    m.applyGamestate();
                break;
            }
        }

        resetButtons();

    }

    private void resetButtons() {
        for (MenuButton m : buttons)
            m.resetBools();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton m : buttons)
            m.setMouseOver(false);

        for (MenuButton m : buttons)
            if (isPressed(e, m)) {
                m.setMouseOver(true);
                break;
            }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            Gamestate.state = Gamestate.PLAYING;

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}