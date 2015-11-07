package ru.medyannikov.homebank.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
import ru.medyannikov.homebank.Model.Bill;
import ru.medyannikov.homebank.R;

/**
 * Created by Vladimir on 05.11.2015.
 */
public class ActivityBillInfo extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_info);

        tabLayout = (TabLayout) findViewById(R.id.tabLayoutBill);
        viewPager = (ViewPager) findViewById(R.id.viewPagerBill);
        toolbar = (Toolbar) findViewById(R.id.toolBarBill);
        toolbar.setNavigationIcon(getResources().getDrawable(android.R.drawable.ic_media_previous));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        Bill bill = SQLiteDataSource.billList.get(bundle.getInt("billInfo"));
        getSupportActionBar().setTitle(bill.getName());




    }
}
