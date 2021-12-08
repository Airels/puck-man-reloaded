package sounds;

import fr.r1r0r0.deltaengine.exceptions.SoundAlreadyExistException;
import fr.r1r0r0.deltaengine.model.engines.SoundEngine;
import fr.r1r0r0.deltaengine.tools.dialog.Dialog;
import main.Main;

/**
 * Loads all Game sounds, according to configured sounds
 *
 * @see Sounds to see all configured sounds
 */
public class SoundLoader {

    /**
     * Load all sounds into DeltaEngine, to play them later
     */
    public static void loadSounds() {
        SoundEngine soundEngine = Main.getEngine().getSoundEngine();

        try {
            for (Sounds s : Sounds.values()) {
                soundEngine.addSound(s.getSound());
            }
        } catch (SoundAlreadyExistException e) {
            new Dialog(Main.APPLICATION_NAME, "Sound loading error", e).show();
        }
    }
}
