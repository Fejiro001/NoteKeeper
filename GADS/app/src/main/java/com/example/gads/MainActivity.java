package com.example.gads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.gads.Adapter.LearningLeadersAdapter;
import com.example.gads.Adapter.SkillIqLeadersAdapter;
import com.example.gads.Models.LearningLeadersModel;
import com.example.gads.Models.SkillIqLeadersModel;
import com.example.gads.Retrofit.ApiClient;
import com.example.gads.Retrofit.ApiInterface;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static ViewPager2 sViewPager2;
    private static TabLayout tabLayout;
    private FragmentStateAdapter viewpageradapter;

    //Titles for the tabs
    private String[] title = new String[]{"Learning Leaders", "Skill IQ Leaders"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sViewPager2 = findViewById(R.id.viewpager2);
        viewpageradapter = new ViewPageAdapter(MainActivity.this);
        sViewPager2.setAdapter(viewpageradapter);

        tabLayout = findViewById(R.id.tabLayout2);

        new TabLayoutMediator(tabLayout, sViewPager2, (tab, position) -> tab.setText(title[position])).attach();

        submitProject();
    }

    public void submitProject() {
        Button submitButton = findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Submission.class);
                startActivity(intent);
            }
        });
    }

}