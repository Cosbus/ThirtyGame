package cosbusgames.thirtygame;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ThirtyGame implements Parcelable {
    private ArrayList<Dice> mDices;
    private int mTurn;
    private int mRoll;
    private boolean mChoiceMade;

    private ArrayList<ArrayList<Integer>> mSums;
    private String mChoice;
    private int mSum;
    private int mPointLatestTurn;


    private String[] mPoints;


    public ThirtyGame() {
        mDices = new ArrayList<>();
        setDices();
        mTurn = 1;
        mRoll = 0;
        mChoiceMade = false;
        mSums = new ArrayList<ArrayList<Integer>>();
        mChoice = "Low";
        mSum = 0;
        mPoints = new String[11];  // To hold the results
        mPointLatestTurn = 0;
    }


        public int describeContents() {
            return 0;
        }

        /** save object in parcel */
        public void writeToParcel(Parcel out, int flags) {
            out.writeTypedList(mDices);
            out.writeInt(this.mTurn);
            out.writeInt(this.mRoll);
            out.writeByte((byte) (mChoiceMade ? 1 : 0));
            out.writeList(mSums);
            out.writeString(this.mChoice);
            out.writeInt(this.mSum);
            out.writeArray(mPoints);
            out.writeInt(mPointLatestTurn);
        }

        public static final Parcelable.Creator<ThirtyGame> CREATOR
                = new Parcelable.Creator<ThirtyGame>() {
            public ThirtyGame createFromParcel(Parcel in) {
                return new ThirtyGame(in);
            }

            public ThirtyGame[] newArray(int size) {
                return new ThirtyGame[size];
            }
        };

        /** recreate object from parcel */
        private ThirtyGame(Parcel in) {
            this();
            in.readTypedList(this.mDices, Dice.CREATOR);
            this.mTurn = in.readInt();
            this.mRoll = in.readInt();
            this.mChoiceMade = in.readByte() != 0;
            this.mSums = in.readArrayList(null);
            this.mChoice = in.readString();
            this.mSum = in.readInt();
            Object[] objects = in.readArray(null);
            mPoints = new String[objects.length];
            for (int i=0;i<mPoints.length; i++){
                this.mPoints[i] = (String) objects[i];
            }
            this.mPointLatestTurn = in.readInt();
        }



    public void setChoice(String choice) {
        mChoiceMade = true;
        mChoice = choice;
    }

    public void setDices() {
        for (int i = 0; i < 6; i++) {
            mDices.add(new Dice());
        }
    }

    public String[] getPoints(){
        mPoints[10] = Integer.toString(mSum);
        return mPoints;
    }

    public int getTurn() {
        return mTurn;
    }

    public int getSum() {
        return mSum;
    }

    public int getRoll() {
        return mRoll;
    }

    public String getChoice(){
        return mChoice;
    }

    public ArrayList<ArrayList<Integer>> getSums() {
        return mSums;
    }

    public void rollDices() {
        for (int i = 0; i < mDices.size(); i++) {
            if (!mDices.get(i).isDiceMarked())  // Make sure that the die is supposed to be rolled
                mDices.get(i).rollDice();       // roll dice
        }
        mRoll++;
    }

    public void makeChoice(){
        calcAddChoice();    // calculate points for given choice
        mRoll = 0;          // Reset roll
        mTurn++;            // Update turn
        for (int i = 0; i< mDices.size(); i++){
            mDices.get(i).unmarkDice();
        }
    }

    public ArrayList<Integer> getDiceValues() {
        ArrayList<Integer> diceValues = new ArrayList<Integer>();
        for (int i = 0; i < mDices.size(); i++)
            diceValues.add(mDices.get(i).getDiceValue());
        return diceValues;
    }

    public ArrayList<Boolean> getDiceMarked() {
        ArrayList<Boolean> diceMarked = new ArrayList<Boolean>();
        for (int i = 0; i < mDices.size(); i++)
            diceMarked.add(mDices.get(i).isDiceMarked());
        return diceMarked;
    }

    public void toggleDice(int dice) {
        mDices.get(dice).toggleDice();
    }

    // This is a function which checks which choice has been made for calculating the sums, then
    // a function for calculating the number of sums according to that choice is called with the
    // target value given (the selection is made using a switch-statement). The points obtained
    // are then saved in a variable, as well as updating the total number of points.
    private void calcAddChoice() {
        switch (mChoice) {
            case "low":
            default:
                int sum = 0;
                for (int i = 0; i < mDices.size(); i++) {
                    if (mDices.get(i).getDiceValue() <= 3)
                        sum += mDices.get(i).getDiceValue();
                }
                mSum += sum;
                mPointLatestTurn = sum;
                mPoints[0] = Integer.toString(sum);
                break;
            case "4":
                mSums = sumPermutations(4, this.getDiceValues(), new ArrayList<Integer>(), new ArrayList<ArrayList<Integer>>());
                sum = chooseSums(mSums)*4;
                mSum += sum;
                mPoints[1] = Integer.toString(sum);
                mPointLatestTurn = sum;
                break;
            case "5":
                mSums = sumPermutations(5, this.getDiceValues(), new ArrayList<Integer>(), new ArrayList<ArrayList<Integer>>());
                sum = chooseSums(mSums)*5;
                mSum += sum;
                mPoints[2] = Integer.toString(sum);
                mPointLatestTurn = sum;
                break;
            case "6":
                mSums = sumPermutations(6, this.getDiceValues(), new ArrayList<Integer>(), new ArrayList<ArrayList<Integer>>());
                sum = chooseSums(mSums)*6;
                mSum += sum;
                mPoints[3] = Integer.toString(sum);
                mPointLatestTurn = sum;
                break;
            case "7":
                mSums = sumPermutations(7, this.getDiceValues(), new ArrayList<Integer>(), new ArrayList<ArrayList<Integer>>());
                sum = chooseSums(mSums)*7;
                mSum += sum;
                mPoints[4] = Integer.toString(sum);
                mPointLatestTurn = sum;
                break;
            case "8":
                mSums = sumPermutations(8, this.getDiceValues(), new ArrayList<Integer>(), new ArrayList<ArrayList<Integer>>());
                sum = chooseSums(mSums)*8;
                mSum += sum;
                mPoints[5] = Integer.toString(sum);
                mPointLatestTurn = sum;
                break;
            case "9":
                mSums = sumPermutations(9, this.getDiceValues(), new ArrayList<Integer>(), new ArrayList<ArrayList<Integer>>());
                sum = chooseSums(mSums)*9;
                mSum += sum;
                mPoints[6] = Integer.toString(sum);
                mPointLatestTurn = sum;
                break;
            case "10":
                mSums = sumPermutations(10, this.getDiceValues(), new ArrayList<Integer>(), new ArrayList<ArrayList<Integer>>());
                sum = chooseSums(mSums)*10;
                mSum += sum;
                mPoints[7] = Integer.toString(sum);
                mPointLatestTurn = sum;
                break;
            case "11":
                mSums = sumPermutations(11, this.getDiceValues(), new ArrayList<Integer>(), new ArrayList<ArrayList<Integer>>());
                sum = chooseSums(mSums)*11;
                mSum += sum;
                mPoints[8] = Integer.toString(sum);
                mPointLatestTurn = sum;
                break;
            case "12":
                mSums = sumPermutations(12, this.getDiceValues(), new ArrayList<Integer>(), new ArrayList<ArrayList<Integer>>());
                sum = chooseSums(mSums)*12;
                mSum += sum;
                mPoints[9] = Integer.toString(sum);
                mPointLatestTurn = sum;
                break;

        }

    }

    public int getPointLatestTurn(){
        return mPointLatestTurn;
    }

    public ArrayList<String> getStringResults(){
        ArrayList<String> pointsString = new ArrayList<>();
        String[] pointsList = getPoints();
        pointsString.add("Here is a rundown of the results:");
        pointsString.add("Points collected by 'Low' choice: ");
        pointsString.add("Points collected by '4' choice: ");
        pointsString.add("Points collected by '5' choice: ");
        pointsString.add("Points collected by '6' choice: ");
        pointsString.add("Points collected by '7' choice: ");
        pointsString.add("Points collected by '8' choice: ");
        pointsString.add("Points collected by '9' choice: ");
        pointsString.add("Points collected by '10' choice: ");
        pointsString.add("Points collected by '11' choice: ");
        pointsString.add("Points collected by '12' choice: ");
        pointsString.add("Total points collected: ");
        for (int i = 0; i < pointsList.length; i++) {
            pointsString.set(i+1, pointsString.get(i+1)+pointsList[i]);
        }
        return pointsString;
    }

    // A function which calculates and returns all the sums from a list of Dicevalues which add up to a given
    // target value
    private ArrayList<ArrayList<Integer>> sumPermutations(int target, ArrayList<Integer> diceValues, ArrayList<Integer> partialDices, ArrayList<ArrayList<Integer>> sums) {
        // Sorting the dicevalues in descending order
        Collections.sort(diceValues);
        Collections.reverse(diceValues);
        // initialize a sum variable
        int s = 0;
        // go through an arraylist of dicevalues which contains part of the dicevalues given (starting from an
        // arraylist which is empty. The arraylist will be populated with dices
        // through a recursive use of the function. Since the dices were sorted the list will first get a dice
        // having the highest value, then the next dice will be added and so forth.
        for (int x : partialDices)
            s += x;
        if (s == target)
            sums.add(partialDices);         // If the sum is equal to the target we have found a sum, which we save
        if (s >= target)                    // If the sum is greater than the target we have found all viable sums
            return sums;                    // so we return here.

        // Now a loop is created in which we extract the dices one at a time, add them to the partial list
        // and call this recursive function with the new partial list (containing one more dice). The remaining
        // dices are kept in an arraylist which is also sent into the function.
        for (int i = 0; i < diceValues.size(); i++) {
            ArrayList<Integer> remainingDices = new ArrayList<Integer>();
            int n = diceValues.get(i);
            for (int j = i + 1; j < diceValues.size(); j++)
                remainingDices.add(diceValues.get(j));
            ArrayList<Integer> new_partial = new ArrayList<Integer>(partialDices);
            new_partial.add(n);
            sums = sumPermutations(target, remainingDices, new_partial, sums);
        }
        return sums;
    }

    private int chooseSums(ArrayList<ArrayList<Integer>> sums) {
        int numberOfSums = 0;
        ArrayList<Integer> diceValues = getDiceValues();
        // Order diceValues in descending order
        Collections.sort(diceValues);
        Collections.reverse(diceValues);
        // Order sums-elements in descending order
        for (int i = 0; i < sums.size(); i++) {
            Collections.sort(sums.get(i));
            Collections.reverse(sums.get(i));
        }

        for (int i = 0; i < sums.size(); i++){                  // Go through each sum
            for (int j = 0; j < sums.get(i).size(); j++){       // Go through the elements of each sum
                if (diceValues.size()>0) {
                    for (int k = 0; k < diceValues.size(); k++) {    // Go through the dices
                        if (sums.get(i).get(j).equals(diceValues.get(k))) { // Find the first dice
                            diceValues.remove(k);                           // that equals the value
                            k--;                                            // in the sum. Then
                            sums.get(i).remove(j);                          // remove that dice and
                            j--;                                            // the corresponding value in the sum.
                            break;
                        }
                    }
                }
                if(sums.get(i).size()<1)    // If we found all numbers of a given sum in our dices we
                    numberOfSums++;         // know that we have found one sum that adds up to the target
            }                               // Then we can increase our number of sums.
        }
        return numberOfSums;                // Here we return the number of sums which add up to the target value
    }
}




