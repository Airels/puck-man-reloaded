package view.colors;

public enum Color {

    BLACK(fr.r1r0r0.deltaengine.view.colors.Color.BLACK),
    WHITE(fr.r1r0r0.deltaengine.view.colors.Color.WHITE),
    RED(fr.r1r0r0.deltaengine.view.colors.Color.RED),
    GREEN(fr.r1r0r0.deltaengine.view.colors.Color.GREEN),
    BLUE(fr.r1r0r0.deltaengine.view.colors.Color.BLUE),
    GOLD(255, 215, 0);

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
