package main;

import javax.swing.JFrame;

public class GameWindow {
    private JFrame jframe = new JFrame();

    public GameWindow(GamePanel gamePanel) {
        this.jframe.setDefaultCloseOperation(3);
        this.jframe.add(gamePanel);
        this.jframe.setTitle("Hammer Guy");
        this.jframe.setResizable(false);
        this.jframe.pack();
        this.jframe.setLocationRelativeTo(null);
        this.jframe.setVisible(true);
    }
}
