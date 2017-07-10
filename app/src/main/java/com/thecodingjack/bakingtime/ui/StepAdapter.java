package com.thecodingjack.bakingtime.ui;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thecodingjack.bakingtime.R;
import com.thecodingjack.bakingtime.ui.recipePOJO.RecipeStep;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        @BindView(R.id.steps_shortDescription) TextView stepTextView;
        @BindView(R.id.step_image) ImageView stepImage;
        @Override
        public void onClick(View v) {
            int stepPosition = getAdapterPosition();
            listener.onListItemClick(stepPosition);
        }

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(Context context, int position) {
            RecipeStep currentStep = stepList.get(position);
            stepTextView.setText("Step " + position +": " + currentStep.getShortDescription());
            String stepImageUrl = currentStep.getThumbnailURL();
            if(TextUtils.isEmpty(stepImageUrl)){
                Picasso.with(context).load(R.drawable.recipe_default).placeholder(R.drawable.loading_icon).error(R.drawable.error_icon).into(stepImage);
            }else{
                Picasso.with(context).load(Uri.parse(stepImageUrl)).placeholder(R.drawable.loading_icon).error(R.drawable.error_icon).into(stepImage);
            }
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
