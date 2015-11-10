package ru.medyannikov.homebank.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.w3c.dom.Text;

import ru.medyannikov.homebank.Adapter.TabPagerAdapterBillInfo;
import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
import ru.medyannikov.homebank.Model.Bill;
import ru.medyannikov.homebank.R;
import ru.medyannikov.homebank.Utils.ClassUtils;

/**
 * Created by Vladimir on 05.11.2015.
 */
public class ActivityBillInfo extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TextView aboutBill;
    private TextView valueBill;
    private Bill bill;

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_info);

        tabLayout = (TabLayout) findViewById(R.id.tabLayoutBill);
        viewPager = (ViewPager) findViewById(R.id.viewPagerBill);
        toolbar = (Toolbar) findViewById(R.id.toolBarBill);
        aboutBill = (TextView) findViewById(R.id.aboutBill);
        valueBill = (TextView) findViewById(R.id.valueBill);
        toolbar.setNavigationIcon(getResources().getDrawable(android.R.drawable.ic_menu_info_details));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        bill = SQLiteDataSource.billList.get(bundle.getInt(ClassUtils.INTENT_BILL_INFO));
        getSupportActionBar().setTitle(bill.getName());
        aboutBill.setText(bill.getAbout());
        valueBill.setText(bill.getValue().toString());

        viewPager.setAdapter(new TabPagerAdapterBillInfo(getSupportFragmentManager(), this , bill));
        tabLayout.setupWithViewPager(viewPager);



    }
}
