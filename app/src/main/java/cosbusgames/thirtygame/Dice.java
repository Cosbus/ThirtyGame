package cosbusgames.thirtygame;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

public class Dice implements Parcelable {

    private int mValue;
    private boolean mDiceMarked;


    public Dice(){
        mValue = 1;
        mDiceMarked = false;
    }

    public int describeContents() {
        return 0;
    }

    /** save object in parcel */
    public void writeToParcel(Parcel out, int flags) {

        out.writeInt(mValue);
        out.writeByte((byte) (mDiceMarked ? 1 : 0));
    }

    public static final Parcelable.Creator<Dice> CREATOR
            = new Parcelable.Creator<Dice>() {
        public Dice createFromParcel(Parcel in) {
            return new Dice(in);
        }

        public Dice[] newArray(int size) {
            return new Dice[size];
        }
    };

    /** recreate object from parcel */
    private Dice(Parcel in) {
        this();
        this.mValue = in.readInt();
        this.mDiceMarked = in.readByte() != 0;
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
