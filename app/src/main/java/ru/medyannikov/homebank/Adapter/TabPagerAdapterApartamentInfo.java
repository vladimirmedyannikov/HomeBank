package ru.medyannikov.homebank.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.medyannikov.homebank.R;

/**
 * Created by Vladimir on 03.12.2015.
 */
public class TabPagerAdapterApartamentInfo extends FragmentPagerAdapter
{
    private Context context;
    private Fragment fragment = null;
    private String[] tabs;

    public TabPagerAdapterApartamentInfo(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        //tabs = new String[] {"Bills", "Diagrams"};
        tabs = context.getResources().getStringArray(R.array.tabsApartaments);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                fragment = new Fragment();
                break;
            case 1:
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
