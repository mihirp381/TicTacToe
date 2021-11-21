package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import observer.WinnerObserver;

/**
 * Represents the person playing the game
 */
@Getter
@AllArgsConstructor
public class Player {
    private int id;
    private String name;
    private String character;

    private int score;

    /**
     * Adds a point to the scoreboard for this player
     */
    public void addPoint() {
        this.score++;
    }
}
