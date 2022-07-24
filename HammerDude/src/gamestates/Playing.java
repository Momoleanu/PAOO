package gamestates;

import DataBase.DataBase;
import entities.EnemyHandler;
import entities.Player;
import levels.LevelHandler;
import main.Game;
import main.Sound;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import ui.SoundButton;
import ui.WinOverlay;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import static utils.Constants.Env.*;

public class Playing extends State implements Statemethods {

    private Player player;
    private LevelHandler levelHandler;
    private EnemyHandler enemyHandler;
    private GameOverOverlay gameOverOverlay;
    private WinOverlay winOverlay;
    private PauseOverlay pOver;
    private SoundButton sb = new SoundButton(10, 0, 500, 1);
    private BufferedImage backgroundImg, bigCloud, smallCloud;
    public  DataBase db;
    private Random rnd = new Random();

    private int[] smallCloudsPos;

    private int xLvlOffset;
    private int leftCamera = (int) (0.2 * Game.GAME_WIDTH);
    private int rightCamera = (int) (0.8 * Game.GAME_WIDTH);
    private int levelTilesWide = LoadSave.getLevel()[0].length;
    private int maxTilesOffset = levelTilesWide - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;
    public String scoreString;

    private boolean gameOver = false;
    private boolean paused = false;
    private boolean win = false;
    private boolean flagWins = false;
    private boolean flagGameOver = false;


    public Playing(Game game) {
        super(game);
        initC();

        backgroundImg = LoadSave.getPlayerSprite(LoadSave.LEVEL_ONE_BG);
        bigCloud = LoadSave.getPlayerSprite(LoadSave.BIG_CLOUDS);
        smallCloud = LoadSave.getPlayerSprite(LoadSave.SMALL_CLOUDS);
        smallCloudsPos = new int[8];
        for (int i = 0; i < smallCloudsPos.length; ++i)
            smallCloudsPos[i] = (int) (90 * Game.SCALE) + rnd.nextInt((int) (100 * Game.SCALE));

    }

    private void initC() {


        levelHandler = new LevelHandler(game);
        enemyHandler = new EnemyHandler(this);
        scoreString = String.valueOf(enemyHandler.score);
        player = new Player(200, 200, (int) (78 * Game.SCALE), (int) (58 * Game.SCALE), this, enemyHandler);
        player.loadLvlData(levelHandler.getCrntLevel().getLevelData());
        Sound s = new Sound(game);
        //db.loadFromDB();
        pOver = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        winOverlay = new WinOverlay(this);
        db = new DataBase(pOver, player);
    }

    @Override
    public void update() {
        if (!paused && !gameOver) {
            levelHandler.update();
            player.update();
            enemyHandler.update(levelHandler.getCrntLevel().getLevelData(), player);
            checkCamera();
        } else {
            pOver.update();
        }
    }

    private void checkCamera() {
        int playerX = (int) player.getHitBox().x;
        int diff = playerX - xLvlOffset;

        if (diff > rightCamera) {
            xLvlOffset += diff - rightCamera;
        } else if (diff < leftCamera)
            xLvlOffset += diff - leftCamera;

        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        //g.drawString("Score:", 100, 100);
        //g.drawString(scoreString, 140, 100);
        drawClouds(g);
        levelHandler.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyHandler.draw(g, xLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pOver.draw(g);


        } else if (gameOver) {
            gameOverOverlay.draw(g);
            while (!flagGameOver) {
                db.saveToDB();
                flagGameOver = true;
            }
        } else if (enemyHandler.size == 0) {
            winOverlay.draw(g);
            //player.loadLvlData(levelHandler.getLevelTwo().getLevelData());
            while (!flagWins) {
               db.saveToDB();
                flagWins = true;
            }
        }

    }

    private void drawClouds(Graphics g) {

        for (int i = 0; i < 3; ++i)
            g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH - (int) (xLvlOffset * 0.3), (int) (204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);

        for (int i = 0; i < smallCloudsPos.length; ++i)
            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLvlOffset * 0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver || !win)
            if (e.getButton() == MouseEvent.BUTTON3) {

                player.setAttacking(true);
            }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver || !win)
            if (paused)
                pOver.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver || !win)
            if (paused)
                pOver.mouseReleased(e);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver || !win)
            if (paused)
                pOver.mouseMoved(e);

    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver || !win)
            if (paused)
                pOver.mouseDragged(e);
    }

    @Override

    public void keyPressed(KeyEvent e) {
        if (gameOver || win) {
            gameOverOverlay.keyPressed(e);
            winOverlay.keyPressed(e);
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W -> player.setUp(true);
                case KeyEvent.VK_A -> player.setLeft(true);
                case KeyEvent.VK_S -> player.setDown(true);
                case KeyEvent.VK_D -> player.setRight(true);
                case KeyEvent.VK_SPACE -> player.setJump(true);
                case KeyEvent.VK_ESCAPE -> paused = !paused;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver || !win)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W -> player.setUp(false);
                case KeyEvent.VK_A -> player.setLeft(false);
                case KeyEvent.VK_S -> player.setDown(false);
                case KeyEvent.VK_D -> player.setRight(false);
                case KeyEvent.VK_SPACE -> player.setJump(false);
            }

    }

    public void resetAll() {
        gameOver = false;
        paused = false;
        win = false;
        player.resetAll();
        enemyHandler.resetAll();

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

    public void unPauseGame() {
        paused = false;
    }

    public int getMaxLvlOffsetX() {
        return maxTilesOffset;
    }

    public void checkEnemyHit(Rectangle2D.Float attackArea) {
        enemyHandler.checkHit(attackArea);
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setWin(boolean win) {
        this.win = win;
    }
}
