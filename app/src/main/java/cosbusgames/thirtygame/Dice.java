package cosbusgames.thirtygame;

import java.util.Random;

public class Dice {

    private int mValue;
    private boolean mDiceMarked;

    public Dice(){
        mValue = 1;
        mDiceMarked = false;
    }

    public void rollDice(){
        Random rand = new Random();
        mValue = rand.nextInt(6)+1;
    }

    public void toggleDice(){
        mDiceMarked = !mDiceMarked;
    }

    public boolean isDiceMarked(){
        return mDiceMarked;
    }

    public int getDiceValue(){
        return mValue;
    }

    public void unmarkDice(){
        mDiceMarked = false;
    }
}
