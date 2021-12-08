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
    MAIN_THEME("main-theme", "main-theme.mp3"),
    GAME_BEGIN("game-begin", "pacman_beginning.wav"),
    PACMAN_WA("pacman-wa", "pacman_wa.wav"),
    PACMAN_KA("pacman-ka", "pacman_ka.wav"),
    GAME_OVER("game-over","dead.wav"),
    SIREN("siren","sirene.wav");

    private Sound sound;

    Sounds(String name, String path) {
        try {
            String p = "/sounds/" + path;
            this.sound = new Sound(name, p);
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

    /**
     * Set if the sound must be looped
     * @param loop boolean true to bool, false otherwise
     */
    public void setLoop(boolean loop){
        try {
            Main.getEngine().getSoundEngine().setLoop(this.getName(),loop);
        } catch (SoundDoesNotExistException e) {
            new Dialog(Main.APPLICATION_NAME,"Sound property error", e).show();
        }
    }

    /**
     * Allowing to set the speed of the sound
     * @param speed double speed to set (must be between 0 and 8)
     */
    public void setSpeed(double speed) {
        try {
            Main.getEngine().getSoundEngine().setSpeed(getName(), speed);
        } catch (SoundDoesNotExistException e) {
            new Dialog(Main.APPLICATION_NAME,"Sound property error", e).show();
        }
    }

    /**
     * Allowing to set the volume of the sound
     * @param volume double volume to set (must be between 0 and 1)
     */
    public void setVolume(double volume) {
        try {
            Main.getEngine().getSoundEngine().setVolume(getName(), volume);
        } catch (SoundDoesNotExistException e) {
            new Dialog(Main.APPLICATION_NAME,"Sound property error", e).show();
        }
    }
}
