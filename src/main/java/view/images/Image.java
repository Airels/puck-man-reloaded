package view.images;

import fr.r1r0r0.deltaengine.tools.dialog.Dialog;
import main.Main;

/**
 * All games Images
 */
public enum Image {

    MAIN_IMG("main.jpg"),
    GAME_LOGO("logo.png"),
    PAC_MAN("pac.gif"),
    PAC_MAN_DEATH("dead.gif"),
    BLINKY_LEFT("blinky_left.png"),
    BLINKY_RIGHT("blinky_right.png"),
    BLINKY_UP("blinky_up.png"),
    BLINKY_DOWN("blinky_down.png"),
    CLYDE_LEFT("clyde_left.png"),
    CLYDE_RIGHT("clyde_right.png"),
    CLYDE_UP("clyde_up.png"),
    CLYDE_DOWN("clyde_down.png"),
    INKY_LEFT("inky_left.png"),
    INKY_RIGHT("inky_right.png"),
    INKY_UP("inky_up.png"),
    INKY_DOWN("inky_down.png"),
    PINKY_LEFT("pinky_left.png"),
    PINKY_RIGHT("pinky_right.png"),
    PINKY_UP("pinky_up.png"),
    PINKY_DOWN("pinky_down.png"),
    BLACKY_LEFT("blacky_left.png"),
    BLACKY_RIGHT("blacky_right.png"),
    BLACKY_UP("blacky_up.png"),
    BLACKY_DOWN("blacky_down.png"),
    SKEAZLY_LEFT("skeazly_left.png"),
    SKEAZLY_RIGHT("skeazly_right.png"),
    SKEAZLY_UP("skeazly_up.png"),
    SKEAZLY_DOWN("skeazly_down.png"),
    SCARED_GHOST("scared.png"),
    EYES_LEFT("eyes_left.png"),
    EYES_RIGHT("eyes_right.png"),
    EYES_UP("eyes_up.png"),
    EYES_DOWN("eyes_down.png"),
    FOUR_SIDED_WALL("wall_4.png"),
    THREE_SIDED_WALL("wall_3.png"),
    TWO_SIDED_WALL_TUBE("wall_2b.png"),
    TWO_SIDED_WALL_CORNER("wall_2a.png"),
    ONE_SIDED_WALL("wall_1.png");


    private final String path;

    Image(String path) {
        this.path = "/images/" + path;
    }

    /**
     * Returns wanted image into a Sprite
     *
     * @return Sprite of the Image
     */
    public fr.r1r0r0.deltaengine.model.sprites.Image getSprite() {
        try {
            return new fr.r1r0r0.deltaengine.model.sprites.Image(path);
        } catch (Exception e) {
            new Dialog(Main.APPLICATION_NAME, "Image loading error", e).show();
            return null;
        }
    }

    /**
     * Returns the path of the Image
     *
     * @return String path of the image
     */
    public String getPath() {
        return path;
    }
}
