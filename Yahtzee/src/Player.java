import java.util.*;

/**
 * Player.java
 * 
 * This class creates and maintains a player
 * 
 * @author Robert Kenney
 */
public class Player{

    /** the scores a for each category, a list of strings for scratching */
    private String[] scores;

    /** whether each category has been scored or not */
    private boolean[] scoredCategories;

    /** whether the player has selected a scoring category this turn */
    private boolean hasSelectedScoreThisTurn;

    /** the amount of times a player has rolled on their current turn */
    private int currentTurnRollCount;

    /** whether the player has yahtzee or not */
    private boolean hasYahtzee;

    /** whether the player has yahtzeeBonused on this turn or not */
    private boolean yahtzeeBonusedThisTurn;

    /**
     * This is the constructor which initializes a player
     * and their instance variables.
    */
    public Player(){
        scores = new String[Yahtzee.NUM_SCORING_CATEGORIES];
        scoredCategories = new boolean[Yahtzee.NUM_SCORING_CATEGORIES];

        hasYahtzee = false;

        for(int category = 0; category < Yahtzee.NUM_SCORING_CATEGORIES; category++){
            scores[category] = "--";
            scoredCategories[category] = false;
        }
    }

    /**
     * gets the score for a particular category
     * @param category the index of the category
     * @return the scor eof the selected category
     */
    public String getScore(int category){
        return scores[category];
    }

    /**
     * gets whether the player has their bonus,
     * meaning the score of their numeric
     * category is greater than the points needed
     * @return true if has enough points, false otherwise
     */
    public boolean hasBonus(){
        
        if(getTopScore() >= Yahtzee.POINTS_NEEDED_FOR_BONUS){
            return true;
        }
        return false;
    }
    
    /**
     * gets the score of the players numeric categories
     * @return the total score of the numeric categories
     */
    public int getTopScore(){
        int sum = 0;
        Scanner scnr;
        for(int category = 0; category < Die.MAX_DIE_VALUE; category++){
            scnr = new Scanner(scores[category]);
            if(scnr.hasNextInt()){
                sum += scnr.nextInt();
            }
        }
        return sum;
    }

    /**
     * gets the total score of the player
     * @return the players total score
     */
    public int getTotalScore(){
        int sum = 0;
        Scanner scnr;
        for(int category = 0; category < Yahtzee.NUM_SCORING_CATEGORIES; category++){
            scnr = new Scanner(scores[category]);
            if(scnr.hasNextInt()){
                sum += scnr.nextInt();
            }
        }

        if(hasBonus()){
            sum += 35;
        }
        return sum;
    }
    
    /**
     * returns if a categoy has been scored
     * @param category the category to be checked
     * @return true if the category has been scored, false otherwise
     */
    public boolean isCategoryScored(int category){
        return scoredCategories[category];
    }

    /**
     * checks to see if the player has finished playing
     * @return true if all categories have been scored, false otherwise
     */
    public boolean isFinished(){
        for(int category = 0; category < Yahtzee.NUM_SCORING_CATEGORIES - 1; category++){
            if(!scoredCategories[category]){
                return false;
            }
        }
        return true;
    }

    /**
     * resets the players variables for a new turn
     */
    public void reset(){
        hasSelectedScoreThisTurn = false;
        currentTurnRollCount = 0;
        yahtzeeBonusedThisTurn = false;
    }

    /**
     * sets the score of a particular category
     * @param category the category to be scored
     * @param score the score which it is to be assigned
     */
    public void setScore(int category, String score){
        if(yahtzeeBonusedThisTurn){
            scores[category] = "X";
            scoredCategories[category] = true;
            hasSelectedScoreThisTurn = true;
        }
        if(!hasSelectedScoreThisTurn && !scoredCategories[category] && currentTurnRollCount > 0 && !yahtzeeBonusedThisTurn){
            scores[category] = score;
            scoredCategories[category] = true;
            hasSelectedScoreThisTurn = true;
        }
    }

    /**
     * scratches a particular category
     * @param category the category to be scratched
     */
    public void scratch(int category){
        setScore(category, "X");
    }
    
    /**
     * scores the yahtzee bonus for the player
     */
    public void yahtzeeBonus(){
        Scanner scnr = new Scanner(scores[Yahtzee.YAHTZEE_BONUS]);
        int score = 0;
        if(scnr.hasNextInt()){
            score = scnr.nextInt();
        }
        if(!hasSelectedScoreThisTurn && !yahtzeeBonusedThisTurn && currentTurnRollCount > 0){
            scores[Yahtzee.YAHTZEE_BONUS] = Integer.toString(score +  Yahtzee.YAHTZEE_BONUS_SCORE);
            yahtzeeBonusedThisTurn = true;
        }
    }

    /**
     * returns whether the player can roll again
     * @return true if the players rolls are less than the rolls per turn and hasnt selected a score, false otherwise
     */
    public boolean canRoll(){
        if(currentTurnRollCount < Yahtzee.ROLLS_PER_TURN && !hasSelectedScoreThisTurn){
            return true;
        }
        return false;
    }

    /**
     * gets the roll count of the current turn
     * @return the roll count of the current turn
     */
    public int getCurrentTurnRollCount(){
        return currentTurnRollCount;
    }

    /**
     * simulates rolling the dice of the player
     * by increasing the roll count by one
     */
    public void rollDice(){
        currentTurnRollCount++;
    }

    /**
     * returns whether the player has a yahtzee or not
     * @return true if has yahtzee, false otherwise
     */
    public boolean hasYahtzee(){
        return hasYahtzee;
    }

    /**
     * simulates the player getting a yahtzee, by setting hasYahtzee to true
     */
    public void yahtzee(){
        hasYahtzee = true;
    }

    /**
     * returns whether the player has selected a score this turn
     * @return true if the player has selected a score, false otherwise
     */
    public boolean hasSelectedScoreThisTurn(){
        return hasSelectedScoreThisTurn;
    }
}