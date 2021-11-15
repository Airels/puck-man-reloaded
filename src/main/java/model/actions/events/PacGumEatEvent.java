package model.actions.events;

import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.actions.triggers.PacGumEatTrigger;
import model.elements.entities.PacMan;
import model.elements.entities.items.PacGum;
import sounds.WakaSound;

public class PacGumEatEvent extends Event {

    private final static WakaSound wakaSound = new WakaSound();

    public PacGumEatEvent(MapLevel mapLevel, PacGum pacGum) {
        addTrigger(new PacGumEatTrigger(mapLevel, pacGum, wakaSound));
    }

    public PacGumEatEvent(MapLevel mapLevel, PacGum pacGum, boolean isSuper) {
        addTrigger(new PacGumEatTrigger(mapLevel, pacGum, wakaSound, isSuper));
    }

    @Override
    public void checkEvent() {
        runTriggers();
    }
}
