package view.colors;

/**
 * Colors of the Game
 */
public enum Color {

    BLACK(fr.r1r0r0.deltaengine.view.colors.Color.BLACK),
    WHITE(fr.r1r0r0.deltaengine.view.colors.Color.WHITE),
    RED(fr.r1r0r0.deltaengine.view.colors.Color.RED),
    GREEN(fr.r1r0r0.deltaengine.view.colors.Color.GREEN),
    BLUE(fr.r1r0r0.deltaengine.view.colors.Color.BLUE),
    GOLD(255, 215, 0),
    GAINSBORO(220, 220, 220),
    PINK(255, 192, 203),
    ORANGE(255, 127, 0),
    YELLOW(255, 255, 0),
    CYAN(fr.r1r0r0.deltaengine.view.colors.Color.CYAN);

    private final fr.r1r0r0.deltaengine.view.colors.Color color;

    /**
     * Constructor allowing to set custom colors according to RGB standard
     *
     * @param r Red shade (0-255)
     * @param g Green shade (0-255)
     * @param b Blue shade (0-255)
     */
    Color(int r, int g, int b) {
        color = new fr.r1r0r0.deltaengine.view.colors.Color(r, g, b, 255);
    }

    /**
     * Constructor allowing to create a color according to given colors in the Engine
     *
     * @param color Engine Color
     */
    Color(fr.r1r0r0.deltaengine.view.colors.Color color) {
        this.color = color;
    }

    /**
     * Returns Color into Color understandable by the Engine
     *
     * @return Engine Color
     */
    public fr.r1r0r0.deltaengine.view.colors.Color getEngineColor() {
        return color;
    }
}
