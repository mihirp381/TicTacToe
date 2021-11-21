package controller;

public interface TicTacToeControllerInterface {
    /**
     * Update the game after a players turn
     */
    void updateGameBoard(String buttonName);

    /**
     * Call model to exit the game
     */
    void exit();

    /**
     * Call model to reset the game
     */
    void reset();
}