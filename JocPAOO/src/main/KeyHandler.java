package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upP,downP,leftP,rightP;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        int numarCheie= e.getKeyCode();

        if(numarCheie==KeyEvent.VK_W)
        {
            upP=true;
        }
        if(numarCheie==KeyEvent.VK_S)
        {
            downP=true;
        }
        if(numarCheie==KeyEvent.VK_A)
        {
            leftP=true;
        }
        if(numarCheie==KeyEvent.VK_D)
        {
            rightP=true;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

        int numarCheie =e.getKeyCode();

        if(numarCheie==KeyEvent.VK_W)
        {
            upP=false;
        }
        if(numarCheie==KeyEvent.VK_S)
        {
            downP=false;
        }
        if(numarCheie==KeyEvent.VK_A)
        {
            leftP=false;
        }
        if(numarCheie==KeyEvent.VK_D)
        {
            rightP=false;
        }

    }
}
