package cosbusgames.thirtygame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ThirtyGame mThirtyGame;
    private static final String KEY_THIRTYGAME = "thirtyGame";
    private ArrayList<ImageButton> mDiceButtons;
    private int mResID;
    private int mIndex;
    private Button mRollButton;
    private TextView mTextViewOne;
    private TextView mTextViewTwo;
    private Spinner mSpinner;
    private TextView mTextViewThree;
    private TextView mTextViewFour;
    private Button mChoiceButton;
    private ArrayAdapter<String> mSpinnerAdapter;
    private String[] mSpinnerItems;
    private static final String KEY_SPINNER = "spinner";
    private SharedPreferences activityPreferences;
    private static final String TAG = "ThirtyGame";

    private List<String> mSpinnerList;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
            mThirtyGame = new ThirtyGame();
            mDiceButtons = new ArrayList<ImageButton>();
            mSpinnerItems = new String[]{"Choose", "Low", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
            // Load saved instancestate if it exist
            if(savedInstanceState != null){
                mThirtyGame = savedInstanceState.getParcelable(KEY_THIRTYGAME);
                mSpinnerItems = savedInstanceState.getStringArray(KEY_SPINNER);
                }
            mSpinnerList = new ArrayList<>(Arrays.asList(mSpinnerItems));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game, container, false);

        Log.d(TAG, "onCreateView called");
        mThirtyGame = new ThirtyGame();
        mDiceButtons = new ArrayList<ImageButton>();
        mSpinnerItems = new String[]{"Choose", "Low", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        // Load saved instancestate if it exist
        if(savedInstanceState != null){
            Log.d(TAG, "Went in and got savedinstancestate");
            mThirtyGame = savedInstanceState.getParcelable(KEY_THIRTYGAME);
            mSpinnerItems = savedInstanceState.getStringArray(KEY_SPINNER);
        }
        mSpinnerList = new ArrayList<>(Arrays.asList(mSpinnerItems));

        // Set up the dicebuttons
        Log.d(TAG, "Setting up dicebuttons");
        mDiceButtons.add((ImageButton) v.findViewById(R.id.dice_one));
        mDiceButtons.add((ImageButton) v.findViewById(R.id.dice_two));
        mDiceButtons.add((ImageButton) v.findViewById(R.id.dice_three));
        mDiceButtons.add((ImageButton) v.findViewById(R.id.dice_four));
        mDiceButtons.add((ImageButton) v.findViewById(R.id.dice_five));
        mDiceButtons.add((ImageButton) v.findViewById(R.id.dice_six));
        Log.d(TAG, "mDiceButtons.size()="+mDiceButtons.size());
        for (mIndex = 0; mIndex < mDiceButtons.size(); mIndex++) {
            Log.d(TAG, "setting listeners on button");
            mDiceButtons.get(mIndex).setOnClickListener(this);
            Log.d(TAG, "before drawDiceButtons");
            drawDiceButtons(mIndex);
            mDiceButtons.get(mIndex).setEnabled(false);         // Initially disabled
        }

        // Set up roll button
        Log.d(TAG, "Setting up roll button");
        mRollButton = (Button) v.findViewById(R.id.roll_button);
        mRollButton.setOnClickListener(this);

        //Set up choice button
        mChoiceButton = (Button) v.findViewById(R.id.game_sum_choice_button);
        mChoiceButton.setOnClickListener(this);
        mChoiceButton.setEnabled(false);

        // Set up TextViews
        mTextViewOne = (TextView) v.findViewById(R.id.game_textview_1);
        mTextViewTwo = (TextView) v.findViewById(R.id.game_textview_2);
        mTextViewThree = (TextView) v.findViewById(R.id.game_textview_3);
        mTextViewFour = (TextView) v.findViewById(R.id.game_textview_4);


        // Set up drop-down
        mSpinner = (Spinner) v.findViewById(R.id.game_spinner);
        mSpinnerAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, mSpinnerList);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mSpinnerAdapter);
        mSpinner.setOnItemSelectedListener(this);

        updateTextView();

        return v;
    }

    @Override
    // Listener for the drop-down menu
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        if (parent.getItemAtPosition(pos).equals("Choose")) {
            //do nothing
        }
        else {
            mThirtyGame.setChoice((String) parent.getItemAtPosition(pos));
            mChoiceButton.setEnabled(true);
        }
    }

    public void onNothingSelected(AdapterView<?> parent){
    }

    // Listeners for the regular buttons
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dice_one:
                toggleDiceButton(0);
                break;
            case R.id.dice_two:
                toggleDiceButton(1);
                break;
            case R.id.dice_three:
                toggleDiceButton(2);
                break;
            case R.id.dice_four:
                toggleDiceButton(3);
                break;
            case R.id.dice_five:
                toggleDiceButton(4);
                break;
            case R.id.dice_six:
                toggleDiceButton(5);
                break;
            case R.id.roll_button:
                if (mThirtyGame.getTurn()>10){
                    ArrayList<String> pointsString = mThirtyGame.getStringResults();
                    Intent intent = ResultActivity.newIntent(getActivity(), pointsString);
                    startActivity(intent);

                }
                else{
                    mThirtyGame.rollDices();
                    mChoiceButton.setEnabled(false);
                    for (mIndex = 0; mIndex < mDiceButtons.size(); mIndex++) {
                        if (!mThirtyGame.getDiceMarked().get(mIndex))
                            drawDiceButtons(mIndex);
                    }
                    updateTextView();
                    for (mIndex = 0; mIndex < mDiceButtons.size(); mIndex++) {
                        mDiceButtons.get(mIndex).setEnabled(true);         // Enable
                    }
                    if (mThirtyGame.getRoll()>2) {
                        mRollButton.setEnabled(false);
                        }
                }

                break;
            case R.id.game_sum_choice_button:
                mSpinnerAdapter.remove(mThirtyGame.getChoice());     // First remove the choice from the dropdown

                // Also remove the item from spinnerItems for savedinstancestate
                ArrayList<String> mSpinnerItemsBuffer = new ArrayList<String>();
                for (int i = 0; i < mSpinnerItems.length; i++){
                    if(!mSpinnerItems[i].equals(mThirtyGame.getChoice()))
                        mSpinnerItemsBuffer.add(mSpinnerItems[i]);           // Copy the element
                }
                // Then overwrite mSpinnerItems with the new list
                mSpinnerItems = mSpinnerItemsBuffer.toArray(new String[mSpinnerItemsBuffer.size()]);

                // Change dropdown default to first element ("Choose")
                mSpinner.setSelection(0);
                mSpinnerAdapter.notifyDataSetChanged();

                mThirtyGame.makeChoice();
                updateTextView();
                if (mThirtyGame.getTurn()<11) {
                    mRollButton.setEnabled(true);
                    mChoiceButton.setEnabled(false);
                }
                else{
                    mRollButton.setText("See results");
                    mRollButton.setEnabled(true);
                    mChoiceButton.setEnabled(false);
                }
                for (mIndex = 0; mIndex < mDiceButtons.size(); mIndex++) {
                    mDiceButtons.get(mIndex).setEnabled(false);         // Disable
                    mChoiceButton.setEnabled(false);
                }
                break;
            default:
                break;

        }
    }

    // If the dicebuttons are pressed they are redrawn
    public void toggleDiceButton(int diceButtonNumber) {
        if (mThirtyGame.getDiceMarked().get(diceButtonNumber)) {
            String mDrawableName = "white" + mThirtyGame.getDiceValues().get(diceButtonNumber);
            mResID = getResources().getIdentifier(mDrawableName, "drawable", getContext().getPackageName());
            mDiceButtons.get(diceButtonNumber).setImageResource(mResID);
        } else {
            String mDrawableName = "grey" + mThirtyGame.getDiceValues().get(diceButtonNumber);
            mResID = getResources().getIdentifier(mDrawableName, "drawable", getContext().getPackageName());
            mDiceButtons.get(diceButtonNumber).setImageResource(mResID);
        }
        mThirtyGame.toggleDice(diceButtonNumber);
    }

    // Draws the dicebuttons depending on the dice value
    public void drawDiceButtons(int diceButtonNumber) {
        Log.d(TAG, "inside drawDiceButtons");
        Log.d(TAG, "mThirtyGame.getDiceValues().size = "+mThirtyGame.getDiceValues().size());

        Log.d(TAG, "mThirtyGame.getDiceMarked().get(diceButtonNumber))="+mThirtyGame.getDiceMarked().get(diceButtonNumber));


        if (mThirtyGame.getDiceMarked().get(diceButtonNumber)) {
            String mDrawableName = "grey" + mThirtyGame.getDiceValues().get(diceButtonNumber);
            mResID = getResources().getIdentifier(mDrawableName, "drawable", getContext().getPackageName());
            mDiceButtons.get(diceButtonNumber).setImageResource(mResID);
        } else {
            Log.d(TAG, "mThirtyGame.getDiceValues().get(diceButtonNumber))="+mThirtyGame.getDiceValues().get(diceButtonNumber));

            String mDrawableName = "white" + mThirtyGame.getDiceValues().get(diceButtonNumber);
            mResID = getResources().getIdentifier(mDrawableName, "drawable", getContext().getPackageName());
            mDiceButtons.get(diceButtonNumber).setImageResource(mResID);
        }
    }

    // Set the textviews depending on the current case/situation
    public void updateTextView(){
        if (mThirtyGame.getTurn()<2 && mThirtyGame.getRoll()<1)
            mTextViewOne.setText("Begin by rolling the dices.");
        else{
            mTextViewOne.setText(R.string.turn);
            mTextViewOne.append(" " + mThirtyGame.getTurn());
        }
        mTextViewTwo.setText(R.string.roll);
        mTextViewTwo.append(" " + mThirtyGame.getRoll());
        if (mThirtyGame.getRoll()==0)
            mTextViewThree.setText(R.string.just_roll);
        else if (mThirtyGame.getRoll()<3)
            mTextViewThree.setText(R.string.keep_dices);
        else
            mTextViewThree.setText(R.string.how_to_add);
        mTextViewFour.setText(R.string.points_turn);
        mTextViewFour.append(" " + mThirtyGame.getPointLatestTurn());


    }

    @Override
    // Save instancestate
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState called");
        savedInstanceState.putParcelable(KEY_THIRTYGAME, mThirtyGame);
        savedInstanceState.putStringArray(KEY_SPINNER,mSpinnerItems);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated called");
        /*if(savedInstanceState != null){
            mThirtyGame = savedInstanceState.getParcelable(KEY_THIRTYGAME);
            mSpinnerItems = savedInstanceState.getStringArray(KEY_SPINNER);
        }*/
    }

    @Override
    // Save as shared preference (for back-button etc.)
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause called");
        /*activityPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putStringSet(KEY_SPINNER,mSpinnerItems);
*/
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart called");
    }
}
