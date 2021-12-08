package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import config.score.ScoreConfiguration;
import main.Main;

class GameTest {

    @Test
    void testGhostEatScore() {
        double score = ScoreConfiguration.CONF_EATING_GHOST_REWARD_SCORE;
        double mult = ScoreConfiguration.CONF_CHAIN_EATING_REWARD_SCORE;

        Game game = new Game(Main.getEngine(), 1, 100);

        assertEquals(0, game.getScore());
        double eatenGhosts;

        game.ghostEaten();
        eatenGhosts = 1;
        double score1 = score * eatenGhosts * mult;
        assertEquals(score1, game.getScore());


        game.ghostEaten();
        eatenGhosts ++;
        double score2 = score * eatenGhosts * mult + score1;
        assertEquals(score2, game.getScore());


        game.ghostEaten();
        eatenGhosts++;
        double score3 = score2 + eatenGhosts*score * mult;
        assertEquals(score3, game.getScore());


    }
}