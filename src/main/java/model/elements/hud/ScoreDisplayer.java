package model.elements.hud;

import static config.score.ScoreDisplayerConfiguration.CONF_SCORE_DISPLAYER_COORDINATES;

import fr.r1r0r0.deltaengine.model.elements.HUDElement;

public class ScoreDisplayer extends HUDElement {

    private double score;


    public ScoreDisplayer(double score) {
        super("Score displayer", CONF_SCORE_DISPLAYER_COORDINATES, null, null);
        // TODO
    }
}
