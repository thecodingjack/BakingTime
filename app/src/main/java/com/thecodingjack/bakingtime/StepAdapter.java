package com.thecodingjack.bakingtime;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lamkeong on 7/8/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private ArrayList<RecipeStep> stepList;
    private StepClickListener listener;

    public StepAdapter(ArrayList<RecipeStep> stepList, StepClickListener listener) {
        this.stepList = stepList;
        this.listener = listener;
    }

    public interface StepClickListener{
        void onListItemClick(int stepPosition);
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView stepTextView;
        @Override
        public void onClick(View v) {
            int stepPosition = getAdapterPosition();
            listener.onListItemClick(stepPosition);
        }

        public StepViewHolder(View itemView) {
            super(itemView);
            stepTextView = (TextView) itemView.findViewById(R.id.steps_shortDescription);
            itemView.setOnClickListener(this);
        }

        public void bind(Context context, int position) {
            RecipeStep currentStep = stepList.get(position);
            stepTextView.setText("Step " + position +": " + currentStep.getShortDescription());
        }
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item,parent,false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        holder.bind(context, position);
    }

    @Override
    public int getItemCount() {
        if (stepList == null) return 0;
        return stepList.size();
    }
}
