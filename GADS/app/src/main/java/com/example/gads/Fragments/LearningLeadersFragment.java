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
import com.example.gads.Models.LearningLeadersModel;
import com.example.gads.R;
import com.example.gads.Retrofit.ApiClient;
import com.example.gads.Retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LearningLeadersFragment extends Fragment {
    List<LearningLeadersModel> mLearningLeadersModelList;
    RecyclerView recyclerView;

    public LearningLeadersFragment() {
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
        View view = inflater.inflate(R.layout.fragment_learning_leaders, container, false);

        mLearningLeadersModelList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.leaderboard_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        LearningLeadersAdapter learningLeadersAdapter = new LearningLeadersAdapter(mLearningLeadersModelList, getContext());
        recyclerView.setAdapter(learningLeadersAdapter);

        //Consume REST Web service
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<LearningLeadersModel>> call = apiService.getLearningLeaders();

        call.enqueue(new Callback<List<LearningLeadersModel>>() {
            @Override
            public void onResponse(Call<List<LearningLeadersModel>> call, Response<List<LearningLeadersModel>> response) {
                mLearningLeadersModelList = response.body();
                learningLeadersAdapter.setLearningLeadersModelList(mLearningLeadersModelList);
            }

            @Override
            public void onFailure(Call<List<LearningLeadersModel>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
                Toast.makeText(view.getContext(), "Failed to retrieve learner data", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}