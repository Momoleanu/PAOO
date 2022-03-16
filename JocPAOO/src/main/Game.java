package main;

import entities.Player;
import levels.LevelHandler;

import java.awt.*;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel  gamePanel = new GamePanel(this);
    private Thread     gameThread;
    private final int  FPS_SET = 120;
    private final int  UPS_SET=200;

    public final static int TILE_DEFAULT=32;
    public final static float SCALE=2.0f;
    public final static int TILES_IN_WIDTH=26;
    public final static int TILES_IN_HEIGHT=14;
    public final static int TILES_SIZE= (int) (TILE_DEFAULT*SCALE);

    public final static int GAME_WIDTH=TILES_SIZE*TILES_IN_WIDTH;
    public final static int GAME_HEIGHT=TILES_SIZE*TILES_IN_HEIGHT;

    private Player player;
    private LevelHandler levelHandler;

    public Game() {
        initC();
        this.gameWindow = new GameWindow(this.gamePanel);
        this.gamePanel.setFocusable(true);
        this.gamePanel.requestFocus();
        this.startGameLoop();

    }

    private void initC() {

        levelHandler=new LevelHandler(this);
        player=new Player(200,200, (int)(78*SCALE), (int)(58*SCALE));
        player.loadLvlData(levelHandler.getCrntLevel().getLevelData());

    }

    private void startGameLoop() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    public void update(){
        levelHandler.update();
        player.update();
    }

    public void render(Graphics g){

        levelHandler.draw(g);
        player.render(g);


    }

    @Override
    public void run() {
        double timePerFrame = 1000000000/FPS_SET;
        double timePerUpdate=1000000000/UPS_SET;
        long prevTime=System.nanoTime();

        int frames = 0;
        int updates=0;
        long lastCheck = System.currentTimeMillis();

        double deltaU=0;
        double deltaF=0;


        while(true) {

            long currTime=System.nanoTime();

            deltaU+=(currTime-prevTime)/timePerUpdate;
            deltaF+=(currTime-prevTime)/timePerFrame;

            prevTime=currTime;

            if(deltaU>=1){

                update();
                updates++;
                deltaU--;
            }

            if(deltaF>=1){
                gamePanel.repaint();
                deltaF--;
                frames++;

            }

            if (System.currentTimeMillis() - lastCheck >= 1000L) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames+" | UPS: "+updates);
                frames = 0;
                updates=0;
            }
        }
    }
    public Player getPlayer()
    {
        return player;
    }

    public void windowFocusLost() {
        player.resetBool();
    }
}
