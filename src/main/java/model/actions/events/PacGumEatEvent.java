package model.actions.events;

import fr.r1r0r0.deltaengine.model.events.Event;
import model.actions.triggers.PacGumEatTrigger;
import model.elements.entities.items.PacGum;
import model.levels.Level;
import sounds.WakaSound;

public class PacGumEatEvent extends Event {

    private final static WakaSound wakaSound = new WakaSound();

    public PacGumEatEvent(Level level, PacGum pacGum) {
        addTrigger(new PacGumEatTrigger(level, pacGum, wakaSound));
    }

    public PacGumEatEvent(Level level, PacGum pacGum, boolean isSuper) {
        addTrigger(new PacGumEatTrigger(level, pacGum, wakaSound, isSuper));
    }

    @Override
    public void checkEvent() {
        runTriggers();
    }
}
