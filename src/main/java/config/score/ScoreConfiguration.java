package config.score;

/**
 * Configuration of the score : how much player earns on each objectives, and the ghost eat multiplier,
 * when player eats ghosts in a row in the same super pac-gum effect.
 */
public final class ScoreConfiguration {

    public final static double CONF_PACGUM_REWARD_SCORE = 10;
    public final static double CONF_SUPER_PACGUM_REWARD_SCORE = 50;
    public final static double CONF_EATING_GHOST_REWARD_SCORE = 100;
    public final static double CONF_CHAIN_EATING_REWARD_SCORE = 1.0;
}