package com.example.gads;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gads.Fragments.LearningLeadersFragment;
import com.example.gads.Fragments.SkillIqLeadersFragment;

public class ViewPageAdapter extends FragmentStateAdapter {

    private static final int NUM = 2;
    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
            {
                return new LearningLeadersFragment();
            }
            case 1:
            {
                return new SkillIqLeadersFragment();
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return NUM;
    }
}
