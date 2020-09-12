package com.example.gads.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gads.Models.SkillIqLeadersModel;
import com.example.gads.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SkillIqLeadersAdapter extends RecyclerView.Adapter<SkillIqLeadersAdapter.ViewHolder> {

    private List<SkillIqLeadersModel> mSkillIqLeadersModelList;
    private Context mContext;

    public SkillIqLeadersAdapter(List<SkillIqLeadersModel> skillIqLeadersModelList, Context context){
        this.mSkillIqLeadersModelList = skillIqLeadersModelList;
        this.mContext = context;
    }

    public void setSkillIqLeadersModels(List<SkillIqLeadersModel> skillIqLeadersModelList) {
        this.mSkillIqLeadersModelList = skillIqLeadersModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SkillIqLeadersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.skills_item, parent, false);
        return new SkillIqLeadersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillIqLeadersAdapter.ViewHolder holder, int position) {
        SkillIqLeadersModel skillIqLeadersModel = mSkillIqLeadersModelList.get(position);
        holder.name.setText(skillIqLeadersModel.getName());
        holder.score.setText(String.valueOf(skillIqLeadersModel.getScore()) + " skill IQ score, ");
        holder.country.setText(skillIqLeadersModel.getCountry());
        Picasso.get()
                .load(skillIqLeadersModel.getBadgeUrl())
                .into(holder.badgeUrl);
    }

    @Override
    public int getItemCount() {
        if (mSkillIqLeadersModelList != null){
            return mSkillIqLeadersModelList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name,score,country;
        public ImageView badgeUrl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.iq_name);
            score = itemView.findViewById(R.id.iq_score);
            country = itemView.findViewById(R.id.iq_country);
            badgeUrl = itemView.findViewById(R.id.iq_badge);
        }
    }
}
