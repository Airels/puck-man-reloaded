package sounds;

import fr.r1r0r0.deltaengine.model.Sound;
import model.elements.cells.Wall;

public class WakaSound {

    private final static Sounds wa = Sounds.PACMAN_WA;
    private final static Sounds ka = Sounds.PACMAN_KA;
    private int step;

    public WakaSound() {
        step = 0;
    }

    public void play() {
        if (step == 0) {
            ka.stop();
            wa.play();
        } else {
            wa.stop();
            ka.play();
        }
        step = (step +1) % 2;
    }
}
