package model.actions.triggers;

import fr.r1r0r0.deltaengine.model.events.Trigger;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.items.PacGum;
import sounds.WakaSound;

public class PacGumEatTrigger implements Trigger {

    private PacGum pacGum;
    private MapLevel mapLevel;
    private WakaSound wakaSound;
    private boolean isSuper;

    public PacGumEatTrigger(MapLevel mapLevel, PacGum pacGum, WakaSound wakaSound) {
        this.pacGum = pacGum;
        this.mapLevel = mapLevel;
        this.wakaSound = wakaSound;
        isSuper = false;
    }

    public PacGumEatTrigger(MapLevel mapLevel, PacGum pacGum, WakaSound wakaSound, boolean isSuper) {
        this(mapLevel, pacGum, wakaSound);
        this.isSuper = isSuper;
    }

    @Override
    public void trigger() {
        if (isSuper)
            System.out.println("SUPAPOWA!");

        mapLevel.removeEntity(pacGum);
        wakaSound.play();
    }
}
