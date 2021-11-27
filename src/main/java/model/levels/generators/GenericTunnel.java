package model.levels.generators;


import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.sprites.InvisibleSprite;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

public class GenericTunnel extends Entity {

    public static int ID = 0;

    private Coordinates<Double> source;
    private Coordinates<Double> destination;

    private GenericTunnel (Coordinates<Double> source, Coordinates<Double> destination) {
        super("tunnel" + ID,source, InvisibleSprite.getInstance(),Dimension.DEFAULT_DIMENSION);
        ID++;
        this.source = source;
        this.destination = destination;
    }

    public static GenericTunnel createTunnel (double sourceX, double sourceY,
                                              double destinationX, double destinationY) {
        return new GenericTunnel(new Coordinates<>(sourceX,sourceY),new Coordinates<>(destinationX,destinationY));
    }

    public void addCollisionEvent (Entity entity) {
        setCollisionEvent(entity, new Event() {
            @Override
            public void checkEvent() {
                entity.setCoordinates(destination.copy());
            }
        });
    }

}
