package model.builders;

import fr.r1r0r0.deltaengine.model.Builder;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.HUDElement;
import fr.r1r0r0.deltaengine.model.sprites.Text;
import view.colors.Color;

/**
 * A Builder Pattern to build in-game text
 */
public class TextBuilder implements Builder<HUDElement> {

    private String name, text, font;
    private Coordinates<Double> coords;
    private Dimension dimension;
    private int size;
    private Color color;

    /**
     * Default constructor.
     * @param text String of the Text
     */
    public TextBuilder(String text) {
        this.name = text;
        this.text = text;
        this.coords = new Coordinates<>(0.0, 0.0);
        this.dimension = Dimension.DEFAULT_DIMENSION;
        this.color = Color.WHITE;
    }

    /**
     * Set an Engine name for the Text, must be unique! (It's an ID)
     * @param name String name of the text
     * @return the current builder
     */
    public TextBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set the text of the future in-game Text
     * @param text String text to set
     * @return the current builder
     */
    public TextBuilder setText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Set coordinates of the Text by giving x and y coordinates
     * @param x abscissa coordinate
     * @param y ordinate coordinate
     * @return the current builder
     */
    public TextBuilder setCoordinates(double x, double y) {
        this.coords = new Coordinates<>(x, y);
        return this;
    }

    /**
     * Set coordinates of the Text by giving Coordinates object
     * @param coords Coordinates to set
     * @return the current builder
     */
    public TextBuilder setCoordinates(Coordinates<Double> coords) {
        this.coords = coords;
        return this;
    }

    /**
     * Set the font of the Text
     * @param font String font name
     * @return the current builder
     */
    public TextBuilder setFont(String font) {
        this.font = font;
        return this;
    }

    /**
     * Set the size of the Text
     * @param size int size of the text
     * @return the current builder
     */
    public TextBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    /**
     * Set the color of the text
     * @param c Color of the text
     * @return the current builder
     */
    public TextBuilder setColor(Color c) {
        this.color = c;
        return this;
    }

    @Override
    public HUDElement build() {
        Text textGUI = new Text(this.text);
        textGUI.setColor(color.getEngineColor());

        if (font != null)
            textGUI.setFont(font);
        if (size > 0)
            textGUI.setSize(size);

        return new HUDElement(name, coords, textGUI, dimension);
    }
}
