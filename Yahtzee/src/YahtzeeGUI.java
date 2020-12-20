import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * YahtzeeGUI.java
 * 
 * This class creates and maintains gui for the yahtzee game
 * 
 * @author Robert Kenney
 */
public class YahtzeeGUI extends JFrame implements ActionListener{

    /** the width of the window */
    public static final int WIDTH = 700;

    /** the height of the gui */
    public static final int HEIGHT = 500;

    /** the width of the main button */
    public static final int STATUS_BUTTON_WIDTH = 150;

     /** the height of the main button */
    public static final int STATUS_BUTTON_HEIGHT = 150;

    /** the width of the scorecard buttons  */
    public static final int SCORECARD_BUTTON_WIDTH = 15;

    /** the height of the scorecard button */
    public static final int SCORECARD_BUTTON_HEIGHT = 15;

    /** the width of the dice */
    public static final int DIE_WIDTH = 75;

    /** the height of the dice */
    public static final int DIE_HEIGHT = 75;

    /** the default font used throughout the window */
    public static final Font DEFAULT_FONT = new Font("Calibri", Font.PLAIN, 25);

    /** the bold font used throughout the window */
    public static final Font BOLD_FONT = new Font("Calibri", Font.BOLD, 25);

    /** the imageicon for a blank die */
    public static final ImageIcon BLANK_DIE = new ImageIcon("dice/blank.png");

    /** the image icon for a checkmark */
    public static final ImageIcon CHECK_MARK = new ImageIcon("dice/check.png");

    /** the background color of the gui */
    public static final Color BACKGROUND_COLOR = new Color(255,178,102);

    /** the file extension for the dice images */
    public static final String FILE_EXTENSION = ".png";

    /** the relative file path of the dice images */
    public static final String FILE_PATH = "dice/";

    /** the status label */
    private JLabel status;

    /** the main button */
    private JButton statusButton;

    /** the label displaying the rolls remaining */
    private JLabel rollsRemaining;

    /** the buttons for the dice */
    private JButton[] diceButtons;

    /** the labels for the scores */
    private JLabel[] scores;

    /** the buttons for the scorecard */
    private JButton[] scorecardButtons;

    /** the label for the bonus */
    private JLabel bonus;

    /** the label for the total score */
    private JLabel totalScore;

    /** the instance of the game */
    private Yahtzee game;

    /**
     * This is the constructor which initializes a the gui
     * and sets it up for the start of the game
    */
    public YahtzeeGUI(){
        
        game = new Yahtzee(getNumPlayers());

        JPanel statusPanel = new JPanel();
        
        JPanel statusBar = new JPanel(new FlowLayout());
        status = new JLabel();
        status.setFont(BOLD_FONT);
        statusButton = new JButton();
        statusButton.setPreferredSize(new Dimension(STATUS_BUTTON_WIDTH, STATUS_BUTTON_HEIGHT));
        statusButton.setFont(DEFAULT_FONT);
        statusButton.addActionListener(this);

        rollsRemaining = new JLabel();
        rollsRemaining.setFont(DEFAULT_FONT);

        statusPanel.add(status, BorderLayout.NORTH);
        statusPanel.add(statusButton, BorderLayout.CENTER);
        statusPanel.add(rollsRemaining, BorderLayout.SOUTH);
        statusPanel.setBackground(BACKGROUND_COLOR);
        
        JPanel dicePanel = new JPanel(new BorderLayout());

        JPanel dice = new JPanel(new GridLayout(3, 2));
        dice.setBackground(BACKGROUND_COLOR);

        diceButtons = new JButton[Yahtzee.NUM_DICE];

        for(int die = 0; die < Yahtzee.NUM_DICE; die++){
            diceButtons[die] = new JButton(BLANK_DIE);
            diceButtons[die].addActionListener(this);
            diceButtons[die].setPreferredSize(new Dimension(DIE_WIDTH, DIE_HEIGHT));

            JPanel diePanel = new JPanel(new FlowLayout());
            diePanel.add(diceButtons[die]);
            diePanel.setBackground(BACKGROUND_COLOR);
            dice.add(diePanel);
        }

        JLabel diceTitle = new JLabel("Dice", SwingConstants.CENTER);
        diceTitle.setFont(BOLD_FONT);

        dicePanel.add(diceTitle, BorderLayout.NORTH);
        dicePanel.add(dice, BorderLayout.CENTER);
        dicePanel.setBackground(BACKGROUND_COLOR);

        JPanel scorecardPanel = new JPanel();

        JLabel scorecardTitle = new JLabel("Scorecard", SwingConstants.CENTER);
        scorecardTitle.setFont(BOLD_FONT);
        

        JPanel scorecard = new JPanel(new GridLayout(Yahtzee.NUM_SCORING_CATEGORIES + 1, 1));
        scorecard.setBackground(BACKGROUND_COLOR);

        scorecardButtons = new JButton[Yahtzee.NUM_SCORING_CATEGORIES];
        scores = new JLabel[Yahtzee.NUM_SCORING_CATEGORIES];

        setupScoreboard(0, Die.MAX_DIE_VALUE, scorecard); 
        bonus = new JLabel("Bonus: no (" + (Yahtzee.POINTS_NEEDED_FOR_BONUS - game.getCurrentPlayer().getTopScore()) + " points needed)", SwingConstants.CENTER);
        scorecard.add(bonus);
        setupScoreboard(Die.MAX_DIE_VALUE, Yahtzee.NUM_SCORING_CATEGORIES, scorecard);

        totalScore = new JLabel("Total Score: " + game.getCurrentPlayer().getTotalScore(), SwingConstants.CENTER);
        totalScore.setFont(DEFAULT_FONT);

        scorecardPanel.add(scorecardTitle, BorderLayout.NORTH);
        scorecardPanel.add(scorecard, BorderLayout.CENTER);
        scorecardPanel.add(totalScore, BorderLayout.SOUTH);
        scorecardPanel.setBackground(BACKGROUND_COLOR);

        setLayout(new GridLayout(1, 3));

        add(statusPanel);
        add(dicePanel);
        add(scorecardPanel);
        setUp();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Yahtzee");
        setVisible(true);

    }

    /**
     * this method prompts the user for the number of players playing
     * @return the number of players
     */
    public int getNumPlayers(){
        int numPlayers = 0;
        Scanner scnr;
        while(numPlayers < 1){
            scnr = new Scanner(JOptionPane.showInputDialog(null,"How many players (minimum 1)"));
            if(scnr.hasNextInt()){
                numPlayers = scnr.nextInt();
            }
        }
        return numPlayers;
    }

    /**
     * sets up a portion of the scoreboard
     * @param start the first category to setup
     * @param end the last category to setup
     * @param scorecard the scorecard panel to add it to
     */
    public void setupScoreboard(int start, int end, JPanel scorecard){
        for(int category = start; category < end; category++){
            JPanel scorecardCategory = new JPanel();
            scorecardButtons[category] = new JButton();
            scorecardCategory.setBackground(BACKGROUND_COLOR);
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(BACKGROUND_COLOR);
            JLabel name = new JLabel(Yahtzee.SCORECARD_CATEGORIES[category], SwingConstants.CENTER);
            scores[category] = new JLabel(game.getCurrentPlayer().getScore(category), SwingConstants.CENTER);

            scorecardCategory.setLayout(new GridLayout(1, 3));

            scorecardButtons[category].addActionListener(this);
            scorecardButtons[category].setPreferredSize(new Dimension(SCORECARD_BUTTON_WIDTH, SCORECARD_BUTTON_HEIGHT));

            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(scorecardButtons[category]);

            scorecardCategory.add(buttonPanel);
            scorecardCategory.add(name);
            scorecardCategory.add(scores[category]);

            scorecard.add(scorecardCategory);
        }
    }

    /**
     * handles user input, i.e. holding dice, rolling dice, ending turn,
     * ending the game, and adding to score
     */
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == statusButton){
            if(game.getCurrentPlayer().canRoll()){
                game.rollDice();
                for(int die = 0; die < Yahtzee.NUM_DICE; die++){
                    updateDie(die);
                 }
            } else if(game.getCurrentPlayer().hasSelectedScoreThisTurn()){
                game.nextTurn();
                status.setText("Player " + game.getCurrentPlayerID() + "'s turn");
                for(int die = 0; die < Yahtzee.NUM_DICE; die++){
                    updateDie(die);
                }
            } else {
                JOptionPane.showMessageDialog(null,"You must select or scratch a scoring option before next turn");
            }
        }

        for(int die = 0; die < Yahtzee.NUM_DICE; die++){
            if(e.getSource() == diceButtons[die]){
                game.getDie(die).toggleHeld();
                updateDie(die);
           }
        }

        for(int category = 0; category < Yahtzee.NUM_SCORING_CATEGORIES; category++){
            if(e.getSource() == scorecardButtons[category]){
                if(game.getCurrentPlayer().getCurrentTurnRollCount() < 1){
                    JOptionPane.showMessageDialog(null,"You must roll at least once before selecting a scoring option");
                }
                if(game.getCurrentPlayer().hasSelectedScoreThisTurn()){
                    JOptionPane.showMessageDialog(null,"You have already selected an option, end your turn");
                }
            }
        }

        for(int category = 0; category < 6; category++){
            if(e.getSource() == scorecardButtons[category]){
                game.scoreNumeric(category);
            }
        }

        if(e.getSource() == scorecardButtons[Yahtzee.THREE_OF_A_KIND]){
            game.scoreThreeOfAKind();
        }

        if(e.getSource() == scorecardButtons[Yahtzee.FOUR_OF_A_KIND]){
            game.scoreFourOfAKind();
        }
        
        if(e.getSource() == scorecardButtons[Yahtzee.FULL_HOUSE]){
            game.scoreFullHouse();
        }
        
        if(e.getSource() == scorecardButtons[Yahtzee.SMALL_STRAIGHT]){
            game.scoreSmallStraight();
        }
        
        if(e.getSource() == scorecardButtons[Yahtzee.LARGE_STRAIGHT]){
            game.scoreLargeStraight();
        }
        
        if(e.getSource() == scorecardButtons[Yahtzee.CHANCE]){
            game.scoreChance();
        }
        
        if(e.getSource() == scorecardButtons[Yahtzee.YAHTZEE]){
            game.scoreYahtzee();
        }
        
        if(e.getSource() == scorecardButtons[Yahtzee.YAHTZEE_BONUS]){
            game.scoreYahtzeeBonus();
            if(game.canYahtzeeBonus()){
                JOptionPane.showMessageDialog(null,"You must scratch another category");
            }
        }

        rollsRemaining.setText("Rolls Remaining: " + (Yahtzee.ROLLS_PER_TURN - game.getCurrentPlayer().getCurrentTurnRollCount()));
        updateButtonText();
        updateScorecard();

        if(game.allPlayersFinished()){
            String message = "Game Over!\n";
            for(int player = 0; player < game.getNumPlayers(); player++){
                message += "Player ";
                message += (player + 1);
                message += ": ";
                message += game.getPlayer(player).getTotalScore();
                message += "\n";
            }
            message += "Play Again?";
            
            int choice = JOptionPane.showConfirmDialog(null, message);
            if (choice == JOptionPane.YES_OPTION) {
                game = new Yahtzee(getNumPlayers());
                setUp();
            } else {
                this.dispose();
            }

        }
    }

    /**
     * sets up the labels of the objects on the gui
     */
    public void setUp(){
        status.setText("Player " + game.getCurrentPlayerID() + "'s turn");
        rollsRemaining.setText("Rolls Remaining: " + (Yahtzee.ROLLS_PER_TURN - game.getCurrentPlayer().getCurrentTurnRollCount()));
        updateButtonText();
        updateScorecard();
        for(int die = 0; die < Yahtzee.NUM_DICE; die++){
            updateDie(die);
        }
    }

    /**
     * updates the text on the main button
     */
    public void updateButtonText(){
        if(game.getCurrentPlayer().canRoll()){
            statusButton.setText("Roll");
        } else {
            statusButton.setText("End Turn");
        }
    }

    /**
     * updates the images on the die
     * @param die the die to update
     */
    public void updateDie(int die){
        if(game.getDie(die).getValue() == 0){
            diceButtons[die].setIcon(BLANK_DIE);
        } else if(game.getDie(die).isHeld()){
            diceButtons[die].setIcon(new ImageIcon(FILE_PATH + "held" + Integer.toString(game.getDie(die).getValue()) + FILE_EXTENSION));
        } else {
            diceButtons[die].setIcon(new ImageIcon(FILE_PATH + Integer.toString(game.getDie(die).getValue()) + FILE_EXTENSION));
        }
    }

    /**
     * updates the the images and text on the scorecard
     */
    public void updateScorecard(){
        for(int category = 0; category < Yahtzee.NUM_SCORING_CATEGORIES; category++){
            scores[category].setText(game.getCurrentPlayer().getScore(category));
            if(game.getCurrentPlayer().isCategoryScored(category)){
                scorecardButtons[category].setIcon(CHECK_MARK);
            } else {
                scorecardButtons[category].setIcon(new ImageIcon());
            }
        }
        if(game.getCurrentPlayer().hasBonus()){
            bonus.setText("Bonus: yes (35)");
        } else {
            bonus.setText("Bonus: no (" + (Yahtzee.POINTS_NEEDED_FOR_BONUS - game.getCurrentPlayer().getTopScore()) + " points needed)");
        }
        totalScore.setText("Total Score: " + game.getCurrentPlayer().getTotalScore());
    }

    /**
     * This is the main method, which creates the YatzeeGUI object
     * @param args Command line arguments, not used
     */
    public static void main(String[]args){
        YahtzeeGUI gui = new YahtzeeGUI();
    }
}