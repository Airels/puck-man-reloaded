package sounds;

import fr.r1r0r0.deltaengine.exceptions.SoundDoesNotExistException;
import fr.r1r0r0.deltaengine.model.Sound;
import fr.r1r0r0.deltaengine.model.engines.SoundEngine;
import fr.r1r0r0.deltaengine.tools.dialog.Dialog;
import main.Main;

/**
 * All Game sounds, could be anything : music, sound effect, ...
 */
public enum Sounds {

    MENU_SELECTION("menu-selection", "menu-selection.wav"),
    MAIN_THEME("main-theme", "main-theme.mp3"), // TODO CREDITS : https://www.youtube.com/watch?v=qtZ0hl-unM4
    GAME_BEGIN("game-begin", "pacman_beginning.wav"),
    PACMAN_WA("pacman-wa", "pacman_wa.wav"),
    PACMAN_KA("pacman-ka", "pacman_ka.wav");

    private Sound sound;

    Sounds(String name, String path) {
        try {
            String p = "/sounds/" + path;
            this.sound = new Sound(name, getClass().getResource(p).getPath());
        } catch (Exception e) {
            new Dialog(Main.APPLICATION_NAME, "Sounds loading error", e).show();
        }
    }

    /**
     * Returns Sound object of the Sound
     * @return Sound engine representation
     */
    public Sound getSound() {
        return sound;
    }

    /**
     * Returns name of the Sound
     * @return String name of the sound
     */
    public String getName() {
        return sound.getName();
    }

    /**
     * Play the sound
     */
    public void play() {
        SoundEngine soundEngine = Main.getEngine().getSoundEngine();
        this.stop();
        try {
            soundEngine.play(getName());
        } catch (SoundDoesNotExistException e) {
            new Dialog(Main.APPLICATION_NAME, "Sound control error", e).show();
        }
    }

    /**
     * Pause the sound
     */
    public void pause() {
        SoundEngine soundEngine = Main.getEngine().getSoundEngine();
        try {
            soundEngine.pause(getName());
        } catch (SoundDoesNotExistException e) {
            new Dialog(Main.APPLICATION_NAME, "Sound control error", e).show();
        }
    }

    /**
     * Stop the sound
     */
    public void stop() {
        SoundEngine soundEngine = Main.getEngine().getSoundEngine();
        try {
            soundEngine.stop(getName());
        } catch (SoundDoesNotExistException e) {
            new Dialog(Main.APPLICATION_NAME, "Sound control error", e).show();
        }
    }
}
