package model;

import observer.TicTacToeObserver;
import observer.WinnerObserver;

import javax.swing.*;
import java.util.ArrayList;

public class TicTacToeModel implements TicTacToeModelInterface{
    // Board Size = 3 signifies size of 3x3
    private static final int BOARD_SIZE = 3;

    private ArrayList<TicTacToeObserver> observers;
    private ArrayList<WinnerObserver> winObservers;

    private Player player1;
    private Player player2;
    private Player currentPlayer;

    private int[][] board;
    private int clickedRow;
    private int clickedCol;

    public TicTacToeModel() {
        observers = new ArrayList<>();
        winObservers = new ArrayList<>();

        player1 = new Player(1, "Player 1", "X");
        player2 = new Player(2, "Player 2", "0");
        currentPlayer = player1;

        board = new int[BOARD_SIZE][BOARD_SIZE];
        for (int row=0; row<BOARD_SIZE; row++)
            for (int col=0; col<BOARD_SIZE; col++)
                board[row][col] = 0;
    }

    /***************************************
     * Player Details
     ***************************************/
    @Override
    public String getPlayer() {
        return currentPlayer.getName();
    }

    @Override
    public String getCharacter() {
        return currentPlayer.getCharacter();
    }

    @Override
    public int getScorePlayer1() {
        return 0;
    }

    @Override
    public int getScorePlayer2() {
        return 0;
    }

    /***************************************
     * Board Details
     ***************************************/
    @Override
    public int getBoardSize() {
        return BOARD_SIZE;
    }

    @Override
    public int getClickedRow() {
        return clickedRow;
    }

    @Override
    public int getClickedCol() {
        return clickedCol;
    }

    /***************************************
     * Ongoing game actions
     ***************************************/
    /**
     * Switching players
     */
    private void switchPlayers() {
        if(currentPlayer == player1){
            currentPlayer = player2;
        }else if(currentPlayer == player2){
            currentPlayer = player1;
        }
    }

    @Override
    public void updateGameBoard(String buttonName) {
        // Get button row and col
        clickedRow = Integer.parseInt(buttonName.split("x")[0]);
        clickedCol = Integer.parseInt(buttonName.split("x")[1]);
        // Updating the board
        board[clickedRow][clickedCol] = currentPlayer.getId();
        // Notifying the game observers
        notifyGameObservers();
        // Checking if any winner
        if (checkWin()) {
            JOptionPane.showMessageDialog(null, currentPlayer.getName()
                    +" ("+currentPlayer.getCharacter()+") wins!");
            notifyWinnerObservers();
            switchPlayers();
        }
        // Checking if board is full
        if (boardFull()) {
            JOptionPane.showMessageDialog(null, "The game ended as draw!");
            notifyWinnerObservers();
            switchPlayers();
        }
        switchPlayers();
    }

    /***************************************
     * Checking if anybody won or game ends as draw
     ***************************************/
    /**
     * Checks if there has been a victory
     * @return true if yes, false otherwise
     */
    private boolean checkWin() {
        return checkHorizontalWin() ||
                checkVerticalWin() ||
                checkDiagonalWin();
    }

    /**
     * Checks if there has been a victory in horizontal manner
     * @return true if yes, false otherwise
     */
    private boolean checkHorizontalWin() {
        for (int row=0; row<BOARD_SIZE; row++) {
            int baseInput = board[row][0];
            if (baseInput != 0) {
                boolean win = true;
                for (int col = 1; col<BOARD_SIZE; col++) {
                    if (board[row][col] != baseInput) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }
        return false;
    }

    /**
     * Checks if there has been a victory in vertical manner
     * @return true if yes, false otherwise
     */
    private boolean checkVerticalWin() {
        for (int col=0; col<BOARD_SIZE; col++) {
            int baseInput = board[0][col];
            if (baseInput != 0) {
                boolean win = true;
                for (int row = 1; row<BOARD_SIZE; row++) {
                    if (board[row][col] != baseInput) {
                        win = false;
                        break;
                    }
                }
                if (win) return true;
            }
        }
        return false;
    }

    /**
     * Checks if there has been a victory in diagonal manner
     * @return true if yes, false otherwise
     */
    private boolean checkDiagonalWin() {
        // Checking Left Diagonal
        int baseInput = board[0][0];
        if (baseInput != 0) {
            boolean win = true;
            for (int i = 1; i < BOARD_SIZE; i++) {
                if (board[i][i] != baseInput) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        //Checking Right Diagonal
        baseInput = board[0][BOARD_SIZE-1];
        if (baseInput != 0) {
            boolean win = true;
            for (int i = 1; i < BOARD_SIZE; i++) {
                if (board[i][BOARD_SIZE - 1 - i] != baseInput) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        return false;
    }

    /**
     * Checks if the board has been filled by the players
     * @return true if yes, false otherwise
     */
    private boolean boardFull() {
        for (int row=0; row<BOARD_SIZE; row++)
            for (int col=0; col<BOARD_SIZE; col++)
                if (board[row][col] == 0)
                    return false;
        return true;
    }

    /***************************************
     * Other Operations
     ***************************************/
    @Override
    public void exit() {
        System.exit(0);
    }

    @Override
    public void reset() {
        for (int row=0; row<BOARD_SIZE; row++)
            for (int col=0; col<BOARD_SIZE; col++)
                board[row][col] = 0;
        currentPlayer = player1;
    }

    /***************************************
     * Observer operations
     ***************************************/
    @Override
    public void registerObserver(TicTacToeObserver o) {
        observers.add(o);
    }

    @Override
    public void registerObserver(WinnerObserver o) {
        winObservers.add(o);
    }

    private void notifyGameObservers() {
        for (TicTacToeObserver obs: observers)
            obs.updateGame();
    }

    private void notifyWinnerObservers() {
        for (WinnerObserver obs: winObservers)
            obs.updateWinner();
    }
}

