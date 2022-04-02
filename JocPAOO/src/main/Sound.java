package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];
    private Thread t;

    public Sound(Game game) {
        soundURL[0] = getClass().getResource("/sounds/menuMusic.wav");

    }

    public void setFile(int i) throws UnsupportedAudioFileException, IOException {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        try {
            clip.stop();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}