package sounds;

import fr.r1r0r0.deltaengine.exceptions.SoundAlreadyExistException;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.SoundEngine;
import main.Main;

public class SoundLoader {

    public static void loadSounds() {
        SoundEngine soundEngine = Main.getEngine().getSoundEngine();

        for (Sounds s : Sounds.values()) {
            try {
                soundEngine.addSound(s.getSound());
            } catch (SoundAlreadyExistException e) {
                e.printStackTrace(); // Should never happen
                System.exit(1);
            }
        }
    }
}
