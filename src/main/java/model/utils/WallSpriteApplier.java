package model.utils;

import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.InvisibleSprite;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import model.elements.cells.Wall;
import view.images.Image;

import java.util.*;

public class WallSpriteApplier {

    static class Neighbor {
        private final Cell c;

        private final Direction direction;

        Neighbor(Cell c, Direction direction) {
            this.c = c;
            this.direction = direction;
        }

        Direction getDirection() {
            return direction;
        }

        public Cell getCell() {
            return c;
        }
    }

    public static void apply(MapLevel mapLevel) {
        Map<Cell, Collection<Neighbor>> cells = new HashMap<>();

        for (int x = 0; x < mapLevel.getWidth(); x++) {
            for (int y = 0; y < mapLevel.getHeight(); y++) {
                Cell c = mapLevel.getCell(x, y);
                if (!c.getClass().equals(Wall.class)) continue;

                Collection<Neighbor> neighbors = new LinkedList<>();
                neighbors.add(new Neighbor(mapLevel.getCell(x, y - 1), Direction.UP));
                neighbors.add(new Neighbor(mapLevel.getCell(x, y + 1), Direction.DOWN));
                neighbors.add(new Neighbor(mapLevel.getCell(x - 1, y), Direction.LEFT));
                neighbors.add(new Neighbor(mapLevel.getCell(x + 1, y), Direction.RIGHT));

                cells.put(c, neighbors);
            }
        }

        for (Map.Entry<Cell, Collection<Neighbor>> entry : cells.entrySet()) {
            int nbNeighbors = 0;

            for (Neighbor neighbor : entry.getValue())
                if (neighbor.getCell().getClass().equals(Wall.class))
                    nbNeighbors++;

            switch (nbNeighbors) {
                case 0 -> entry.getKey().setSprite(Image.FOUR_SIDED_WALL.getSprite());
                case 1 -> {
                    Sprite s = Image.ONE_SIDED_WALL.getSprite();
                    int rotate = 0;

                    for (Neighbor neighbor : entry.getValue()) {
                        if (!neighbor.getCell().getClass().equals(Wall.class)) {
                            switch (neighbor.getDirection()) {
                                case LEFT -> rotate = -90;
                                case DOWN -> rotate = 180;
                                case RIGHT -> rotate = 90;
                            }

                            break;
                        }
                    }

                    s.setRotate(rotate);
                    entry.getKey().setSprite(s);
                }
                case 2 -> {
                    Neighbor firstNeighbor = null;
                    Sprite s = null;
                    int rotate = 0;

                    for (Neighbor neighbor : entry.getValue()) {
                        if (!neighbor.getCell().getClass().equals(Wall.class)) continue;

                        firstNeighbor = neighbor;
                        break;
                    }

                    if (firstNeighbor == null)
                        System.exit(1000); // TODO

                    for (Neighbor neighbor : entry.getValue()) {
                        if (neighbor == firstNeighbor || !neighbor.getCell().getClass().equals(Wall.class)) continue;

                        if (neighbor.getDirection().getOpposite().equals(firstNeighbor.getDirection())) {
                            s = Image.TWO_SIDED_WALL_TUBE.getSprite();
                            switch (firstNeighbor.getDirection()) {
                                case UP, DOWN -> rotate = 90;
                            }
                            break;
                        } else {
                            s = Image.TWO_SIDED_WALL_CORNER.getSprite();
                            if (firstNeighbor.getDirection() == Direction.RIGHT) {
                                if (neighbor.getDirection() == Direction.UP) {
                                    rotate = -90;
                                    break;
                                }
                            } else if (firstNeighbor.getDirection() == Direction.DOWN) {
                                if (neighbor.getDirection() == Direction.LEFT) {
                                    rotate = 90;
                                    break;
                                }
                            } else if (firstNeighbor.getDirection() == Direction.LEFT) {
                                rotate = 90;
                                if (neighbor.getDirection() == Direction.UP) {
                                    rotate += 90;
                                    break;
                                }
                            } else {
                                rotate = 180;
                                if (neighbor.getDirection() == Direction.RIGHT) {
                                    rotate += 90;
                                    break;
                                }
                            }
                        }
                    }

                    s.setRotate(rotate);
                    entry.getKey().setSprite(s);
                }
                case 3 -> {
                    Sprite s = Image.THREE_SIDED_WALL.getSprite();
                    entry.getValue().forEach((neighbor -> {
                        if (!neighbor.getCell().getClass().equals(Wall.class)) {
                            switch (neighbor.direction) {
                                case UP -> s.setRotate(-90);
                                case DOWN -> s.setRotate(90);
                                case LEFT -> s.setRotate(180);
                            }
                        }
                    }));

                    entry.getKey().setSprite(s);
                }
                case 4 -> entry.getKey().setSprite(InvisibleSprite.getInstance());
            }
        }
    }
}