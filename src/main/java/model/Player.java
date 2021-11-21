package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
