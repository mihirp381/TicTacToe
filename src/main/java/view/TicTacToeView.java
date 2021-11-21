package view;

import controller.TicTacToeController;
import controller.TicTacToeControllerInterface;
import model.TicTacToeModel;
import model.TicTacToeModelInterface;
import observer.TicTacToeObserver;
import observer.WinnerObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeView
        extends JFrame
        implements TicTacToeObserver, WinnerObserver, ActionListener {
    private TicTacToeModelInterface model;
    private TicTacToeControllerInterface controller;

    /**
     * Constructor for TicTacToe View
     * @param model model of the game
     * @param controller controller of the game
     */
    public TicTacToeView(
            TicTacToeModelInterface model, TicTacToeControllerInterface controller) {
        super();
        this.model = model;
        this.controller = controller;
        model.registerObserver((WinnerObserver) this);
        model.registerObserver((TicTacToeObserver) this);
        buildView();
        pack();
        setVisible(true);
    }

    /**
     * Builds the entire UI
     */
    private void buildView() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildMainPanel();
        add(mainPanel);
    }

    // Main Panel
    private JPanel mainPanel;

    /**
     * Builds the main panel
     */
    private void buildMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        buildInfoPanel();
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        buildGamePanel();
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        buildOperationsPanel();
        mainPanel.add(operationsPanel, BorderLayout.SOUTH);
    }

    // Info Panel
    private JPanel infoPanel;
    private JLabel currentPlayer;
    private JLabel player1Score;
    private JLabel player2Score;
    /**
     * Builds the Information Panel UI
     */
    private void buildInfoPanel() {
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 1));

        // Title
        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Tic Tac Toe");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        titlePanel.add(title);
        infoPanel.add(titlePanel);
        // Game info
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new GridLayout(1, 2));
        // Game info - Turn
        JPanel turnPanel = new JPanel();
        currentPlayer = new JLabel("Turn: " + model.getPlayer());
        turnPanel.add(currentPlayer);
        playerPanel.add(turnPanel);
        // Game info - Scorecard
        JPanel scorecardPanel = new JPanel();
        scorecardPanel.setLayout(new GridLayout(2, 2));
        // Scorecard - Player 1
        scorecardPanel.add(new JPanel().add(new JLabel(model.getNamePlayer1()+":")));
        player1Score = new JLabel(""+model.getScorePlayer1());
        scorecardPanel.add(new JPanel().add(player1Score));
        // Scorecard - Player 2
        scorecardPanel.add(new JPanel().add(new JLabel(model.getNamePlayer2()+":")));
        player2Score = new JLabel(""+model.getScorePlayer2());
        scorecardPanel.add(new JPanel().add(player2Score));

        playerPanel.add(scorecardPanel);

        infoPanel.add(playerPanel);
    }

    // Game Panel
    private JPanel gamePanel;
    private JPanel[][] squares;
    private JButton[][] squareButtons;
    /**
     * Builds the Game Panel UI
     */
    private void buildGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(model.getBoardSize(), model.getBoardSize()));

        squares = new JPanel[model.getBoardSize()][model.getBoardSize()];
        squareButtons = new JButton[model.getBoardSize()][model.getBoardSize()];
        for (int row=0; row<model.getBoardSize(); row++)
            for (int col=0; col<model.getBoardSize(); col++){
                // Building each button on the frame
                squareButtons[row][col] = new JButton(" ");
                squareButtons[row][col].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
                squareButtons[row][col].setName(row+"x"+col);
                squareButtons[row][col].addActionListener(this);
                // Building each panels to store buttons
                squares[row][col] = new JPanel();
                // Adding respective button to the Panel
                squares[row][col].add(squareButtons[row][col]);
                // Finishing up the decoration
                squares[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                gamePanel.add(squares[row][col]);
            }
    }

    // Operations Panel
    private JPanel operationsPanel;
    private JButton exit;
    private JButton reset;
    /**
     * Builds the Operations Panel UI
     */
    private void buildOperationsPanel() {
        operationsPanel = new JPanel();

        exit = new JButton("Exit");
        exit.addActionListener(this);
        operationsPanel.add(exit);

        reset = new JButton("Reset");
        reset.addActionListener(this);
        operationsPanel.add(reset);
    }

    /**
     * Switches the player turns
     */
    public void switchTurns() {
        currentPlayer.setText("Turn: " + model.getPlayer());
    }

    /**
     * Get the button pressed by the player
     * @param e The action event
     * @return The button name
     */
    private String getButtonPressed(ActionEvent e) {
        for (int row=0; row<model.getBoardSize(); row++)
            for (int col=0; col< model.getBoardSize(); col++)
                if (e.getSource() == squareButtons[row][col])
                    return squareButtons[row][col].getName();
        return null;
    }

    /**
     * Resets the view of the game
     */
    public void reset() {
        for (int row=0; row<model.getBoardSize(); row++) {
            for (int col=0; col< model.getBoardSize(); col++) {
                squareButtons[row][col].setText(" ");
                squareButtons[row][col].setEnabled(true);
            }
        }
        // Updating the scorecard
        player1Score.setText(""+model.getScorePlayer1());
        player2Score.setText(""+model.getScorePlayer2());
    }

    public static void main(String[] args) {
        TicTacToeModelInterface model = new TicTacToeModel();
        TicTacToeControllerInterface controller = new TicTacToeController(model);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit) {
            controller.exit();
        }else if (e.getSource() == reset) {
            controller.reset();
        }else {
            controller.updateGameBoard(getButtonPressed(e));
        }
    }

    @Override
    public void updateGame() {
        squareButtons[model.getClickedRow()][model.getClickedCol()].setEnabled(false);
        squareButtons[model.getClickedRow()][model.getClickedCol()].setText(model.getCharacter());
    }

    @Override
    public void updateWinner() {
        controller.reset();
    }
}

