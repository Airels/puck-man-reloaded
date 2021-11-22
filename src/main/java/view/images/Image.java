package view.images;

public enum Image {

    MAIN_IMG("main.jpg"),
    PAC_MAN("pac.gif"),
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
    SCARED_GHOST("scared.png");

    private fr.r1r0r0.deltaengine.model.sprites.Image image;

    Image(String path) {
        try {
            String p = "/images/" + path;
            this.image = new fr.r1r0r0.deltaengine.model.sprites.Image(getClass().getResource(p).getPath());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public fr.r1r0r0.deltaengine.model.sprites.Image getSprite() {
        return image;
    }
}
