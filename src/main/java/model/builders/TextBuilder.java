package model.builders;

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

    public TextBuilder(String text) {
        this.name = text;
        this.text = text;
        this.coords = new Coordinates<>(0.0, 0.0);
        this.dimension = new Dimension(1, 1);
        this.color = Color.WHITE;
    }

    public TextBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TextBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public TextBuilder setCoordinates(double x, double y) {
        this.coords = new Coordinates<>(x, y);
        return this;
    }

    public TextBuilder setCoordinates(Coordinates<Double> coords) {
        this.coords = coords;
        return this;
    }

    public TextBuilder setFont(String font) {
        this.font = font;
        return this;
    }

    public TextBuilder setSize(int size) {
        this.size = size;
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
