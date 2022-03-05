package main;

import entity.Player;

import java.util.Random;
import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel implements Runnable {

    //Setari ecran
    final int originalTileSize = 16; //tile 16x16
    final int scale = 3;
    final int nano = 1000000000;
    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    //Setare FPS
    final int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player=new Player(this, keyH);

    //setare pozitie originala player
    Random rand = new Random();

    int playerX = rand.nextInt(500) + 50;
    int playerY = rand.nextInt(500) + 50;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start(); //apeleaza automat run()
    }

    @Override
    public void run() { //implementam game Loop

        //Utilizare metoda Sleep pt update si fps
        final double drawInterval = nano / FPS; //0.016 sec
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            long currentTime = System.nanoTime(); //1.000.000.000 =1 sec;

            update();

            repaint();//apel paintComponent
            double remainingTime = (nextDrawTime - System.nanoTime())/1000000; //castare milisecs ;
            try {

                if(remainingTime<0)
                    remainingTime=0;//castare pe long pentru ca nu se accepta altfel
                Thread.sleep((long) remainingTime);
                nextDrawTime+=drawInterval;

            } catch (InterruptedException e) {

                e.printStackTrace();
            }

        }
    }

    public void update() {

        player.update();

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g); // de fiecare data cand apare paintComponent Good Practice

        Graphics2D g2 = (Graphics2D) g;

        player.draw(g2);

        g2.dispose();

    }
}
