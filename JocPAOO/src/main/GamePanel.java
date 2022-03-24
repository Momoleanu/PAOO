package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import java.awt.*;
import javax.swing.*;

import static main.Game.GAME_WIDTH;
import static main.Game.GAME_HEIGHT;
import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;


public class GamePanel extends JPanel {
    private Game game;

    public GamePanel(Game game) {
        this.addKeyListener(new KeyboardInputs(this));
        this.addMouseListener(new MouseInputs(this));
        this.game = game;
        setPanelSize();
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

    }

    public void updateGame() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);

    }

    public Game getGame() {

        return game;
    }

}
