package cosbusgames.thirtygame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ResultFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mResultRecycleView;
    private ThirtyAdapter mAdapter;
    private ArrayList<String> mListItems;
    private Button mBackButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        mResultRecycleView = (RecyclerView) view.findViewById(R.id.result_recyclerview);
        mResultRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBackButton = (Button) view.findViewById(R.id.back_button);
        mBackButton.setOnClickListener(this);

        updateUI();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_button:
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivity(intent);
        }

    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mListItems = (ArrayList<String>) getActivity().getIntent().getStringArrayListExtra(ResultActivity.EXTRA_RESULT_ID);
    }

    private void updateUI(){
        mAdapter = new ThirtyAdapter(mListItems);
        mResultRecycleView.setAdapter(mAdapter);
    }

    private class ThirtyHolder extends RecyclerView.ViewHolder {
        private TextView mItemTextView;
        private String mItem;

        public ThirtyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.result_list, parent, false));

            mItemTextView = (TextView) itemView.findViewById(R.id.list_item);
        }

        public void bind(String item){
            mItem = item;
            mItemTextView.setText(mItem);
        }
    }

    private class ThirtyAdapter extends RecyclerView.Adapter<ThirtyHolder>{
        private ArrayList<String> mItems;

        public ThirtyAdapter(ArrayList<String> items){
            mItems = items;
        }

        @Override
        public ThirtyHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ThirtyHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ThirtyHolder holder, int position){
            String item = mItems.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount(){
            return mItems.size();
        }

    }
}

