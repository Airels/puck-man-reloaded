package sounds;

/**
 * WakaSound handler, played when PacMan eats PacGums.
 */
public class WakaSound {

    private final static Sounds wa = Sounds.PACMAN_WA;
    private final static Sounds ka = Sounds.PACMAN_KA;
    private int step;

    /**
     * Default constructor
     */
    public WakaSound() {
        step = 0;
    }

    /**
     * Play "wa" or "ka" sound depending on what previous sound was played
     */
    public void play() {
        if (step == 0) {
            ka.stop();
            wa.play();
        } else {
            wa.stop();
            ka.play();
        }
        step = (step + 1) % 2;
    }
}
