package ru.medyannikov.homebank.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.medyannikov.homebank.Adapter.TabPagerFragmentAdapter;
import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
import ru.medyannikov.homebank.R;

/**
 * Created by Vladimir on 16.11.2015.
 */
public class MainFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static MainFragment mainFragment;
    public static MainFragment getInstance(){

        if (mainFragment == null) {
            Bundle bundle = new Bundle();
            mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
        }
        return mainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TabPagerFragmentAdapter pagerFragmentAdapter = new TabPagerFragmentAdapter(getChildFragmentManager(), this.getContext());
        viewPager.setAdapter(pagerFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}
