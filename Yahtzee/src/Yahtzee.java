/**
 * Yahtzee.java
 * 
 * This class creates and maintains the yahtzee game
 * 
 * @author Robert Kenney
 */
public class Yahtzee {

    /** the number of dice in the game */
    public static final int NUM_DICE = 5;

    /** the number of scoring categories in the game */
    public static final int NUM_SCORING_CATEGORIES = 14;

    /** the names of the scoring categories in the game */
    public static final String[] SCORECARD_CATEGORIES = {"Ones", "Twos", "Threes", "Fours", "Fives", "Sixes", "3 of a Kind", "4 of a Kind", 
                                                        "Full House", "Small Straight", "Large Straight", "Chance", "Yahtzee", "Yahtzee Bonus"};
    
    /** the index of the three of a kind category */
    public static final int THREE_OF_A_KIND = 6;

    /** the index of the four of a kind category */
    public static final int FOUR_OF_A_KIND = 7;

    /** the index of the full house category */
    public static final int FULL_HOUSE = 8;

    /** the index of the small straight category */
    public static final int SMALL_STRAIGHT = 9;

    /** the index of the large straight category */
    public static final int LARGE_STRAIGHT = 10;

    /** the index of the chance category */
    public static final int CHANCE = 11;

    /** the index of the yahtzee category */
    public static final int YAHTZEE = 12;

    /** the index of the yahtzee bonus category */
    public static final int YAHTZEE_BONUS = 13;

    /** the amount of rolls that a player is allotted on their turn */
    public static final int ROLLS_PER_TURN = 3;

    /** the score a player recieves for a full house */
    public static final int FULL_HOUSE_SCORE = 25;

    /** the score a player recieves for a small straight */
    public static final int SMALL_STRAIGHT_SCORE = 30;

    /** the score a player recieives for a large straight */
    public static final int LARGE_STRAIGHT_SCORE = 40;

    /** the score a player recieves for a yahtzee */
    public static final int YAHTZEE_SCORE = 50;

    /** the score a player recieves for a yahtzee bonus */
    public static final int YAHTZEE_BONUS_SCORE = 150;

    /** the score a player recieves should they attain their bonus */
    public static final int BONUS = 35;

    /** the points needed in from the numeric categories to get the bonus */
    public static final int POINTS_NEEDED_FOR_BONUS = 63;

    /** the number of players */
    private int numPlayers;

    /** the player who is currently rolling */
    private Player currentPlayer;

    /** the number associated with the current player i.e. Player 1 */
    private int currentPlayerID;

    /** a list containing all of the players in the game */
    private Player[] players;

    /** a list containing the dice to be played with */
    private Die[] dice;

    /**
     * This is the constructor which initializes the game
     * and its instance variables.
     * @param numPlayers the number of players in the game
    */
    public Yahtzee(int numPlayers){
        //set num players to the parameterized variable
        this.numPlayers = numPlayers;

        //initialize the player list and set the current player to player 1
        players = new Player[numPlayers];
        for(int player = 0; player < numPlayers; player++){
            players[player] = new Player();
        }
        currentPlayerID = 0;
        currentPlayer = players[currentPlayerID];

        //initialize the dice
        dice = new Die[NUM_DICE];
        for(int die = 0; die < NUM_DICE; die++){
            dice[die] = new Die();
        }

    }

    /**
     * returns a die, based off of its index
     * @param die the index of the die in the list
     * @return the die at the index
    */
    public Die getDie(int die){
        return dice[die];
    }

    /**
     * returns the current player
     * @return the current player
    */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * returns the current player id
     * @return the current player id
    */
    public int getCurrentPlayerID(){
        return currentPlayerID + 1;
    }

    /**
     * returns a player, based off of their index
     * @param payer the index of the payer in the list
     * @return the player at the index
    */
    public Player getPlayer(int player){
        return players[player];
    }

    /**
     * returns the number of players
     * @return the number of players
    */
    public int getNumPlayers(){
        return numPlayers;
    }

    /**
     * changes the current player and resets the dice
    */
    public void nextTurn(){
        if(currentPlayer.hasSelectedScoreThisTurn()){
            if(currentPlayerID + 1 < numPlayers){
                currentPlayerID++;
            } else {
                currentPlayerID = 0;
            }
            currentPlayer = players[currentPlayerID];
            currentPlayer.reset();
            for(int die = 0; die < NUM_DICE; die++){
                dice[die].reset();
            }
        }
    }
    
    /**
     * checks to see if all players have finished playing
     * @return true if all players are finished, false otherwise
    */
    public boolean allPlayersFinished(){
        for(int player = 0; player < numPlayers; player++){
            if(!players[player].isFinished()){
                return false;
            }
        }
        return true;
    }

    /**
     * rolls all of the dice, if the player is not out of rolls
    */
    public void rollDice(){
        if(currentPlayer.canRoll()){
            for(int die = 0; die < NUM_DICE; die++){
                if(!dice[die].isHeld()){
                    dice[die].roll();
                }
            }
        }
        currentPlayer.rollDice();
    }

    /**
     * returns the sum of all of the dice
     * @return the sum of all the dice
    */
    public int getSumAllDice(){
        int sum = 0;
        for(int die = 0; die < NUM_DICE; die++){
            sum += dice[die].getValue();
        }
        return sum;
    }

    /**
     * returns the sum of all of the dice with a certain value
     * @param value the value
     * @return the sum of all the dice of that value
    */
    public int getNumSameValueDice(int value){
        int sum = 0;
        for(int die = 0; die < NUM_DICE; die++){
            if(dice[die].getValue() == value){
                sum++;
            }
        }
        return sum;
    }

    /**
     * returns the largest run of consecutive dice i.e.
     * a roll of 1 2 and 3 would yield the result of 3
     * @return the largest run of consecutive dice
    */
    public int getNumConcecutiveValues(){
        int[] values = new int[NUM_DICE];
        for(int die = 0; die < NUM_DICE; die++){
            values[die] = dice[die].getValue();
        }
        int temp = 0;
        for(int i = 0; i < NUM_DICE; i++){
            for(int j = i + 1; j < NUM_DICE; j++){
                if(values[i] > values[j]){
                    temp = values[i];
                    values[i] = values[j];
                    values[j] = temp;
                }
            }
        }
        int numConcecutiveValues = 1;
        for(int value = 0; value < NUM_DICE - 1; value++){
            if(values[value + 1] - values[value] == 1){
                numConcecutiveValues++;
            }
        }
        return numConcecutiveValues;
    }

    /**
     * updates the score of a numeric category for the current player
     * @param category the die number to be scored
    */
    public void scoreNumeric(int category){
        currentPlayer.setScore(category, Integer.toString(getNumSameValueDice(category + 1) * (category + 1)));
    }

    /**
     * scratches a category for the current player
     * @param category the category to be scratched
    */
    public void scratch(int category){
        currentPlayer.setScore(category, "scratched");
    }

    /**
     * updates the score of a three of a kind for the current player
    */
    public void scoreThreeOfAKind(){
        if(canThreeOfAKind()){
            currentPlayer.setScore(THREE_OF_A_KIND, Integer.toString(getSumAllDice()));
        } else {
            currentPlayer.scratch(THREE_OF_A_KIND);
        }
    }

    /**
     * updates the score of a four of a kind for the current player
    */
    public void scoreFourOfAKind(){
        if(canFourOfAKind()){
            currentPlayer.setScore(FOUR_OF_A_KIND, Integer.toString(getSumAllDice()));
        } else {
            currentPlayer.scratch(FOUR_OF_A_KIND);
        }
    }

    /**
     * updates the score of a full house for the current player
    */
    public void scoreFullHouse(){
        if(canFullHouse()){
            currentPlayer.setScore(FULL_HOUSE, Integer.toString(FULL_HOUSE_SCORE));
        } else {
            currentPlayer.scratch(FULL_HOUSE);
        }
    }

    /**
     * updates the score of a small straight for the current player
    */
    public void scoreSmallStraight(){
        if(canSmallStraight()){
            currentPlayer.setScore(SMALL_STRAIGHT, Integer.toString(SMALL_STRAIGHT_SCORE));
        } else {
            currentPlayer.scratch(SMALL_STRAIGHT);
        }
    }

    /**
     * updates the score of a large straight for the current player
    */
    public void scoreLargeStraight(){
        if(canLargeStraight()){
            currentPlayer.setScore(LARGE_STRAIGHT, Integer.toString(LARGE_STRAIGHT_SCORE));
        } else {
            currentPlayer.scratch(LARGE_STRAIGHT);
        }
    }

    /**
     * updates the score of chance for the current player
    */
    public void scoreChance(){
        currentPlayer.setScore(CHANCE, Integer.toString(getSumAllDice()));
    }

    /**
     * updates the score of yahtzee for the current player
    */
    public void scoreYahtzee(){
        if(canYahtzee()){
            currentPlayer.setScore(YAHTZEE, Integer.toString(YAHTZEE_SCORE));
            currentPlayer.yahtzee();
        } else {
            currentPlayer.scratch(YAHTZEE);
        }
    }

    /**
     * updates the score of yahtzee bonus for the current player
    */
    public void scoreYahtzeeBonus(){
        if(canYahtzeeBonus()){
            currentPlayer.yahtzeeBonus();
        }
    }

    /**
     * checks to see if a player can score a three of a kind
     * @return true if the player has three of the same die value, false otherwise
    */
    public boolean canThreeOfAKind(){
        for(int value = 1; value <= Die.MAX_DIE_VALUE; value++){
            if(getNumSameValueDice(value) >= 3){
                return true;
            }
        }
        return false;
    }

    /**
     * checks to see if a player can score a four of a kind
     * @return true if the player has four of the same die value, false otherwise
    */
    public boolean canFourOfAKind(){
        for(int value = 1; value <= Die.MAX_DIE_VALUE; value++){
            if(getNumSameValueDice(value) >= 4){
                return true;
            }
        }
        return false;
    }

    /**
     * checks to see if a player can score a full house
     * @return true if the player has three of one die value and two of another, false otherwise
    */
    public boolean canFullHouse(){
        //sets the first value to the first die;
        int val1 = dice[0].getValue();
        //gets the number of dice of this value
        int val1Count = getNumSameValueDice(val1);
        //sets the second value to the second die
        int val2 = dice[1].getValue();
        // if the two values are equal, the second value is reassigned to the next die
        int die = 2;
        while(val1 == val2 && die < NUM_DICE){
            val2 = dice[die].getValue();
            die++;
        }
        //gete the number of dice of the second value
        int val2Count = getNumSameValueDice(val2);
        //makes sure that the value that occurs more is in the val1Count spot and vice versa
        if(val2Count > val1Count){
            int temp = val1Count;
            val1Count = val2Count;
            val2Count = temp;
        }
        //checks to see if the requirements for a full house are met
        if(val1Count == 3 && val2Count == 2){
            return true;
        }
        return false;
    }

    /**
     * checks to see if a player can score a small straight
     * @return true if the player has four or more consecutive die values, false otherwise
    */
    public boolean canSmallStraight(){
        
        if(getNumConcecutiveValues() >= 4){
            return true;
        }
        return false;
    }

    /**
     * checks to see if a player can score a large straight
     * @return true if the player has five consecutive die values, false otherwise
    */
    public boolean canLargeStraight(){
        if(getNumConcecutiveValues() == 5){
            return true;
        }
        return false;
    }

    /**
     * checks to see if a player can score a yahtzee
     * @return true if the player has five of the same die value, false otherwise
    */
    public boolean canYahtzee(){
        for(int value = 1; value <= Die.MAX_DIE_VALUE; value++){
            if(getNumSameValueDice(value) == 5){
                return true;
            }
        }
        return false;
    }

    /**
     * checks to see if a player can score a yahtzee bonus
     * @return true if the player has a yahtzee and has already scored a yahtze, false otherwise
    */
    public boolean canYahtzeeBonus(){

        if(canYahtzee() && currentPlayer.hasYahtzee()){
            return true;
        }
        return false;
    }

}