package com.example.gads.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gads.Adapter.LearningLeadersAdapter;
import com.example.gads.Adapter.SkillIqLeadersAdapter;
import com.example.gads.Models.LearningLeadersModel;
import com.example.gads.Models.SkillIqLeadersModel;
import com.example.gads.R;
import com.example.gads.Retrofit.ApiClient;
import com.example.gads.Retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkillIqLeadersFragment extends Fragment {
    List<SkillIqLeadersModel> mSkillIqLeadersModelList;
    RecyclerView recyclerView;


    public SkillIqLeadersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_skill_iq_leaders, container, false);

        recyclerView = view.findViewById(R.id.leaderboard_recyclerview2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        SkillIqLeadersAdapter skillIqLeadersAdapter = new SkillIqLeadersAdapter(mSkillIqLeadersModelList, getContext());
        recyclerView.setAdapter(skillIqLeadersAdapter);

        ApiInterface apiService2 = ApiClient.getClient().create(ApiInterface.class);
        Call<List<SkillIqLeadersModel>> call2 = apiService2.getSkillIqLeaders();

        call2.enqueue(new Callback<List<SkillIqLeadersModel>>() {
            @Override
            public void onResponse(Call<List<SkillIqLeadersModel>> call, Response<List<SkillIqLeadersModel>> response) {
                mSkillIqLeadersModelList = response.body();
                skillIqLeadersAdapter.setSkillIqLeadersModels(mSkillIqLeadersModelList);
            }

            @Override
            public void onFailure(Call<List<SkillIqLeadersModel>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
                Toast.makeText(view.getContext(), "Failed to retrieve skill iq data", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}