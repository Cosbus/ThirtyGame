package cosbusgames.thirtygame;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResultActivity extends SingleFragmentActivity {
    public static final String EXTRA_RESULT_ID = "cosbusgames.thirtygame.list_id";

    public static Intent newIntent(Context packageContext, ArrayList<String> listId){
        Intent intent = new Intent(packageContext, ResultActivity.class);
        intent.putStringArrayListExtra(EXTRA_RESULT_ID, listId);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        return new ResultFragment();
    }
}
