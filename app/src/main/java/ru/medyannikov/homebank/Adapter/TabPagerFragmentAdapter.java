package ru.medyannikov.homebank.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.medyannikov.homebank.Fragments.BillsFragment;
import ru.medyannikov.homebank.Fragments.ChartFragment;
import ru.medyannikov.homebank.Fragments.OperationFragment;
import ru.medyannikov.homebank.R;

/**
 * Created by Vladimir on 23.09.2015.
 */
public class TabPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] tabs;
    private Context context;
    private FragmentManager fragmentManager;
    private Fragment actiFragment;
    private Resources res;

    public TabPagerFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragmentManager = fm;
        this.context = context;
        res = context.getResources();
        tabs = context.getResources().getStringArray(R.array.tabs);
        //tabs = new String[] {"Bills", "Operation","Diagram", "More info"};
                //this.context.getResources().getStringArray(R.array.tabs);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position)
        {
            case 0:
                fragment = BillsFragment.getInstance();
                break;
            case 1:
                fragment = OperationFragment.getInstance();
                //fragment = new Fragment();
                break;
            case 2:
                fragment = ChartFragment.getInstance();
                break;
            case 3:
                fragment = new Fragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
