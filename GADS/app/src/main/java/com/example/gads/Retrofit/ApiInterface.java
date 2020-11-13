package com.example.gads.Retrofit;

import com.example.gads.Models.LearningLeadersModel;
import com.example.gads.Models.SkillIqLeadersModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("api/hours")
    Call<List<LearningLeadersModel>> getLearningLeaders();

    @GET("api/skilliq")
    Call<List<SkillIqLeadersModel>> getSkillIqLeaders();
}
