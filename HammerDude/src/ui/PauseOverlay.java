package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.ActButtons.ACT_SIZE;
import static utils.Constants.UI.PauseButtons.SOUND_SIZE;
import static utils.Constants.UI.VolumeButtons.SLIDER_WIDTH;
import static utils.Constants.UI.VolumeButtons.VOLUME_HEIGHT;

public class PauseOverlay {

    private Playing playing;
    private BufferedImage bgImg;
    private int bgX, bgY, bgW, bgH;
    private SoundButton musicButton, sFxButton;
    private ActButtons menuB, replayB, unpauseB;
    private VolumeButton volumeButton;

    public boolean isMusicMuted=false;

    public PauseOverlay(Playing playing) {

        this.playing = playing;
        loadBackground();
        createSoundButtons();
        createActButtons();
        createVolumeButton();
    }

    private void createVolumeButton() {
        int vX = (int) (309 * Game.SCALE);
        int vY = (int) (278 * Game.SCALE);
        volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    private void createActButtons() {

        int menuX = (int) (313 * Game.SCALE);
        int replayX = (int) (387 * Game.SCALE);
        int unpauseX = (int) (462 * Game.SCALE);

        int bY = (int) (325 * Game.SCALE);

        menuB = new ActButtons(menuX, bY, ACT_SIZE, ACT_SIZE, 2);
        replayB = new ActButtons(replayX, bY, ACT_SIZE, ACT_SIZE, 1);
        unpauseB = new ActButtons(unpauseX, bY, ACT_SIZE, ACT_SIZE, 0);
    }

    private void createSoundButtons() {
        int soundX = (int) (450 * Game.SCALE);
        int musicY = (int) (140 * Game.SCALE);
        int sfxY = (int) (186 * Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sFxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    private void loadBackground() {
        bgImg = LoadSave.getPlayerSprite(LoadSave.PAUSE_BACKGROUND);
        bgW = (int) (bgImg.getWidth() * Game.SCALE);
        bgH = (int) (bgImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (25 * Game.SCALE);
    }

    public void update() {

        musicButton.update();
        sFxButton.update();

        menuB.update();
        replayB.update();
        unpauseB.update();

        volumeButton.update();

    }

    public void draw(Graphics g) {

        //BG

        g.drawImage(bgImg, bgX, bgY, bgW, bgH, null);

        // Butoane sunet
        musicButton.draw(g);
        sFxButton.draw(g);

        //Butoane actiuni
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);

        volumeButton.draw(g);


    }

    public void mouseDragged(MouseEvent e) {
        if (volumeButton.isMousePressed()) {
            volumeButton.moveSlider(e.getX());
        }

    }

    public void mousePressed(MouseEvent e) {
        if (hover(e, musicButton)) {
            musicButton.setMousePressed(true);
            isMusicMuted=true;
        } else if (hover(e, sFxButton)) {
            sFxButton.setMousePressed(true);
        } else if (hover(e, menuB)) {
            menuB.setMousePressed(true);
        } else if (hover(e, replayB)) {
            replayB.setMousePressed(true);
        } else if (hover(e, unpauseB)) {
            unpauseB.setMousePressed(true);
        } else if (hover(e, volumeButton)) {
            volumeButton.setMousePressed(true);
        }

    }

    public void mouseReleased(MouseEvent e) {
        if (hover(e, musicButton)) {
            if (musicButton.isMousePressed())
                musicButton.setMuted(musicButton.isMuted());
                isMusicMuted=true;

        } else if (hover(e, sFxButton)) {
            if (sFxButton.isMousePressed())
                sFxButton.setMuted(sFxButton.isMuted());
        } else if (hover(e, menuB)) {
            if (menuB.isMousePressed())
                Gamestate.state = Gamestate.MENU;
            playing.unPauseGame();
        } else if (hover(e, replayB)) {
            if (replayB.isMousePressed())
                playing.resetAll();
        } else if (hover(e, unpauseB)) {
            if (unpauseB.isMousePressed())
                playing.unPauseGame();
        }
        musicButton.resetBools();
        sFxButton.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
        menuB.resetBools();

        volumeButton.resetBools();

    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sFxButton.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        menuB.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if (hover(e, musicButton))
            musicButton.setMouseOver(true);
        else if (hover(e, sFxButton))
            sFxButton.setMouseOver(true);
        else if (hover(e, menuB))
            menuB.setMouseOver(true);
        else if (hover(e, replayB))
            replayB.setMouseOver(true);
        else if (hover(e, unpauseB))
            unpauseB.setMouseOver(true);
        else if (hover(e, volumeButton))
            volumeButton.setMouseOver(true);
    }

    private boolean hover(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
