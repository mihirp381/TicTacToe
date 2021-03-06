package model;

import observer.TicTacToeObserver;
import observer.WinnerObserver;

public interface TicTacToeModelInterface {
    // Player Details
    String getPlayer();
    String getCharacter();
    String getNamePlayer1();
    String getNamePlayer2();
    int getScorePlayer1();
    int getScorePlayer2();

    // Board Details
    int getBoardSize();
    int getClickedRow();
    int getClickedCol();

    // Game Actions
    void updateGameBoard(String buttonName);

    // Other actions
    void exit();
    void reset();

    // Observer actions
    void registerObserver(TicTacToeObserver o);
    void registerObserver(WinnerObserver o);
}
