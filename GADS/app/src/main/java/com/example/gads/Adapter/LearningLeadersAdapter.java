package com.example.gads.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gads.Models.LearningLeadersModel;
import com.example.gads.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LearningLeadersAdapter extends RecyclerView.Adapter<LearningLeadersAdapter.ViewHolder> {

    private List<LearningLeadersModel> mLearningLeadersModelList;
    private Context mContext;

    public LearningLeadersAdapter(List<LearningLeadersModel> learningLeadersModels, Context context){
        this.mLearningLeadersModelList = learningLeadersModels;
        this.mContext = context;
    }

    public void setLearningLeadersModelList(List<LearningLeadersModel> learningLeadersModelList) {
        this.mLearningLeadersModelList = learningLeadersModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LearningLeadersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.learning_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LearningLeadersAdapter.ViewHolder holder, int position) {
        LearningLeadersModel learningLeadersModel = mLearningLeadersModelList.get(position);
        holder.name.setText(learningLeadersModel.getName());
        holder.hours.setText(String.valueOf(learningLeadersModel.getHours()) + " learning hours, ");
        holder.country.setText(learningLeadersModel.getCountry());
        Picasso.get()
                .load(learningLeadersModel.getBadgeUrl())
                .into(holder.badgeUrl);
    }

    @Override
    public int getItemCount() {
        if (mLearningLeadersModelList != null){
            return mLearningLeadersModelList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name,hours,country;
        public ImageView badgeUrl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.learner_name);
            hours = itemView.findViewById(R.id.learning_hours);
            country = itemView.findViewById(R.id.learner_country);
            badgeUrl = itemView.findViewById(R.id.learner_badge);
        }
    }
}
