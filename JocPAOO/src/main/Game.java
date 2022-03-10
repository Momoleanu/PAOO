package main;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel = new GamePanel();
    private Thread gameThread;
    private final int FPS_SET = 60;

    public Game() {
        this.gameWindow = new GameWindow(this.gamePanel);
        this.gamePanel.setFocusable(true);
        this.gamePanel.requestFocus();
        this.startGameLoop();
    }

    private void startGameLoop() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    public void run() {
        double timePerFrame = 1000000000/FPS_SET;
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();
        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        while(true) {
            now = System.nanoTime();
            if ((double)(now - lastFrame) >= timePerFrame) {
                this.gamePanel.repaint();
                lastFrame = now;
                ++frames;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000L) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }
}
