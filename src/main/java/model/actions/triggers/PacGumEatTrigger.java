package model.actions.triggers;

import config.pacman.PacManConfiguration;
import config.score.ScoreConfiguration;
import fr.r1r0r0.deltaengine.model.events.Trigger;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.PacMan;
import model.elements.entities.items.PacGum;
import model.maps.Level;
import sounds.WakaSound;

public class PacGumEatTrigger implements Trigger {

    private final PacGum pacGum;
    private final Level level;
    private final MapLevel mapLevel;
    private final WakaSound wakaSound;
    private boolean isSuper;

    public PacGumEatTrigger(Level level, PacGum pacGum, WakaSound wakaSound) {
        this.level = level;
        this.mapLevel = level.getMapLevelLoadable().getMapLevel();
        this.pacGum = pacGum;
        this.wakaSound = wakaSound;
        isSuper = false;
    }

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
