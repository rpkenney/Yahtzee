import java.util.*;

/**
 * Die.java
 * 
 * This class creates and maintains a die
 * 
 * @author Robert Kenney
 */
public class Die {

    /** the maximum value of a die */
    public static final int MAX_DIE_VALUE = 6;

    /** the current value of the die */
    private int value;

    /** whether the die is held, not to be rolled again */
    private boolean held;

    /** the amount of times the die has been rolled this turn*/
    private int currentTurnRollCount;

    /**
     * This is the constructor which initializes a die
     * and its instance variables.
    */
    public Die(){
        held = false;
        value = 0;
        currentTurnRollCount = 0;
    }

    /**
     * gets the value of the die
     * @return the value of the die
     */
    public int getValue(){
        return value;
    }

    /**
     * resets the die for a new turn
     */
    public void reset(){
        value = 0;
        held = false;
        currentTurnRollCount = 0;
    }

    /**
     * simulates rolling a die by setting its value to a
     * random number, between one and the maximum die value
     * and adding to the roll count
     */
    public void roll(){
        Random rand = new Random();
        value = rand.nextInt(MAX_DIE_VALUE) + 1;
        currentTurnRollCount++;
    }

    /**
     * toggles wheter the die is being held or not
     */
    public void toggleHeld(){
        if(currentTurnRollCount > 0){
            if(held){
                held = false;
            } else {
                held = true;
            }
        }
    }

    /**
     * gets whether the die is held or not
     * @return true if held, false otherwise
     */
    public boolean isHeld(){
        return held;
    }

    /**
     * gets the amount of times the die has been rolled this turn
     * @return the current turn roll count
     */
    public int getCurrentTurnRollCount(){
        return currentTurnRollCount;
    }
}