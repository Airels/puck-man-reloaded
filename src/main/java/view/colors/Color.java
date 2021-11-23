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
    ORANGE(255, 127, 0);

    private final fr.r1r0r0.deltaengine.view.colors.Color color;

    Color(int r, int g, int b) {
        color = new fr.r1r0r0.deltaengine.view.colors.Color(r, g, b, 255);
    }

    Color(fr.r1r0r0.deltaengine.view.colors.Color color) {
        this.color = color;
    }

    public fr.r1r0r0.deltaengine.view.colors.Color getEngineColor() {
        return color;
    }
}
