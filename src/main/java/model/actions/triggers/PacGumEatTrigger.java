package model.actions.triggers;

import fr.r1r0r0.deltaengine.model.events.Trigger;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.items.PacGum;
import model.levels.Level;
import sounds.WakaSound;

/**
 * Trigger triggered when PacMan eat a PacGum
 */
public class PacGumEatTrigger implements Trigger {

    private final PacGum pacGum;
    private final Level level;
    private final MapLevel mapLevel;
    private final WakaSound wakaSound;
    private boolean isSuper;

    /**
     * Default constructor
     * @param level Level who belongs the PacGum
     * @param pacGum The PacGum
     * @param wakaSound The Waka sound
     */
    public PacGumEatTrigger(Level level, PacGum pacGum, WakaSound wakaSound) {
        this.level = level;
        this.mapLevel = level.getMapLevelLoadable().getMapLevel();
        this.pacGum = pacGum;
        this.wakaSound = wakaSound;
        isSuper = false;
    }

    /**
     * Second constructor, used if eaten PacGum is a Super PacGum
     * @param level Level who belongs the PacGum
     * @param pacGum The PacGum
     * @param wakaSound The waka sound
     * @param isSuper boolean if PacGum is a Super PacGum
     */
    public PacGumEatTrigger(Level level, PacGum pacGum, WakaSound wakaSound, boolean isSuper) {
        this(level, pacGum, wakaSound);
        this.isSuper = isSuper;
    }

    @Override
    public void trigger() {
        mapLevel.removeEntity(pacGum);
        wakaSound.play();

        level.getGame().pacGumEaten(isSuper);
    }
}
