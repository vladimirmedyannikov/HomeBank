package ru.medyannikov.homebank.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.medyannikov.homebank.Fragments.BillsFragment;
import ru.medyannikov.homebank.Fragments.ChartFragment;
import ru.medyannikov.homebank.Fragments.OperationFragment;
import ru.medyannikov.homebank.Model.Bill;

/**
 * Created by Vladimir on 07.11.2015.
 */
public class TabPagerAdapterBillInfo extends FragmentPagerAdapter {
    private Context context;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private String[] tabs;
    private Bill bill;

    public TabPagerAdapterBillInfo(FragmentManager fm, Context context, Bill bill) {
        super(fm);
        this.context = context;
        this.fragmentManager = fm;
        this.bill = bill;
        tabs = new String [] {"Operation", "Chart"};
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = OperationFragment.getInstance(bill);
                break;
            case 1:
                fragment = ChartFragment.getInstance(bill);
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
