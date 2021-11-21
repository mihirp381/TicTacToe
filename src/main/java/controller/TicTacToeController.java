package controller;

import model.TicTacToeModelInterface;
import view.TicTacToeView;

public class TicTacToeController implements TicTacToeControllerInterface{
    private TicTacToeModelInterface model;
    private TicTacToeView view;

    public TicTacToeController(TicTacToeModelInterface model) {
        this.model = model;
        this.view = new TicTacToeView(this.model, this);
    }

    /**
     * Update the game after a players turn
     */
    @Override
    public void updateGameBoard(String buttonName) {
        model.updateGameBoard(buttonName);
        view.switchTurns();
    }

    /**
     * Call model to exit the game
     */
    @Override
    public void exit() {
        model.exit();
    }

    /**
     * Call model to reset the game
     */
    @Override
    public void reset() {
        model.reset();
        view.reset();
    }
}