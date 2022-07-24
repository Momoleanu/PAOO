package main;

import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;

import java.awt.*;

public class Game implements Runnable {

    public final static int TILE_DEFAULT = 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILE_DEFAULT * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
    Sound sound = new Sound(Game.this);
    private final GamePanel gamePanel = new GamePanel(this);
    private Playing playing;
    private Menu menu;
    public final static int UPS_SET = 200;

    public Game() {
        initC();
        this.gamePanel.setFocusable(true);
        GameWindow gameWindow = new GameWindow(this.gamePanel);
        this.gamePanel.requestFocus();

        this.startGameLoop();

    }

    private void initC() {
        menu = new Menu(this);
        playing = new Playing(this);
        playMusic(0);

    }

    private void startGameLoop() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        switch (Gamestate.state) {
            case MENU -> menu.update();
            case PLAYING -> playing.update();
            case OPTIONS, QUIT -> System.exit(0);
            default -> {
            }
        }
    }

    public void render(Graphics g) {

        switch (Gamestate.state) {
            case MENU -> menu.draw(g);
            case PLAYING -> playing.draw(g);
            default -> {
            }
        }
    }

    @Override
    public void run() {
        int FPS_SET = 120;
        double timePerFrame = 1_000_000_000 / FPS_SET;
        double timePerUpdate = 1_000_000_000 / UPS_SET;
        long prevTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;


        while (true) {

            long currTime = System.nanoTime();

            deltaU += (currTime - prevTime) / timePerUpdate;
            deltaF += (currTime - prevTime) / timePerFrame;

            prevTime = currTime;

            if (deltaU >= 1) {

                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                deltaF--;
                frames++;

            }

            if (System.currentTimeMillis() - lastCheck >= 1000L) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING) {
            playing.getPlayer().resetBool();
        }

    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public void stopMusic() {
        sound.stop();
    }

    public int getUPS() {
        return UPS_SET;
    }

    public void playMusic(int i) {
        if (Gamestate.state == Gamestate.MENU) {
            sound.setFile(i);
            sound.play();
        } else
            sound.stop();
    }
}
