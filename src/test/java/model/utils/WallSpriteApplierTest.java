package model.utils;

import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevelBuilder;
import fr.r1r0r0.deltaengine.model.sprites.InvisibleSprite;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import model.elements.cells.Wall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WallSpriteApplierTest {

    MapLevelBuilder builder;
    Sprite invisibleSprite = InvisibleSprite.getInstance();

    @BeforeEach
    public void init() {
        builder = new MapLevelBuilder("test", 5, 5);
    }

    @Test
    public void bigSquare() throws Exception {
        for (int i = 1; i < 4; i++)
            for (int j = 1; j < 4; j++)
                builder.setCell(new Wall(i, j));

        MapLevel map = builder.build();
        WallSpriteApplier.apply(map);

        DeltaEngine.launch().addMap(map);
        DeltaEngine.getKernelEngine().setCurrentMap("test");
        for (; ; ) ;
    }

    @Test
    public void tFigure() throws Exception {
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                if (j == 1 || i == 2)
                    builder.setCell(new Wall(i, j));
            }
        }


        MapLevel map = builder.build();
        WallSpriteApplier.apply(map);

        DeltaEngine.launch().addMap(map);
        DeltaEngine.getKernelEngine().setCurrentMap("test");
        for (; ; ) ;
    }
}