package sounds;

import fr.r1r0r0.deltaengine.exceptions.SoundDoesNotExistException;
import fr.r1r0r0.deltaengine.model.Sound;
import fr.r1r0r0.deltaengine.model.engines.SoundEngine;
import main.Main;

public enum Sounds {

    MENU_SELECTION("menu-selection", "menu-selection.wav"),
    MAIN_THEME("main-theme", "main-theme.mp3"); // TODO CREDITS : https://www.youtube.com/watch?v=qtZ0hl-unM4

    private Sound sound;

    Sounds(String name, String path) {
        try {
            String p = "/sounds/" + path;
            this.sound = new Sound(name, getClass().getResource(p).getPath());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Sound getSound() {
        return sound;
    }

    public String getName() {
        return sound.getName();
    }

    public void play() {
        SoundEngine soundEngine = Main.getEngine().getSoundEngine();
        try {
            soundEngine.play(getName());
        } catch (SoundDoesNotExistException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void pause() {
        SoundEngine soundEngine = Main.getEngine().getSoundEngine();
        try {
            soundEngine.pause(getName());
        } catch (SoundDoesNotExistException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        SoundEngine soundEngine = Main.getEngine().getSoundEngine();
        try {
            soundEngine.stop(getName());
        } catch (SoundDoesNotExistException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
