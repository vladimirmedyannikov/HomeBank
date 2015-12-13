package ru.medyannikov.homebank.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ru.medyannikov.homebank.Adapter.TabPagerAdapterApartamentInfo;
import ru.medyannikov.homebank.DataManager.SQLiteDataSource;
import ru.medyannikov.homebank.Eventbus.ApartamentChangeEvent;
import ru.medyannikov.homebank.Eventbus.BusProvider;
import ru.medyannikov.homebank.Model.Apartament;
import ru.medyannikov.homebank.R;
import ru.medyannikov.homebank.Utils.ClassUtils;

/**
 * Created by Vladimir on 03.12.2015.
 */
public class ActivityApartamentInfo extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView aboutText;
    private int idApartament;
    private Apartament apartament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        setContentView(R.layout.activity_apartament_info);

        tabLayout = (TabLayout) findViewById(R.id.tabLayoutApartament);
        viewPager = (ViewPager) findViewById(R.id.viewPagerApartament);
        aboutText = (TextView) findViewById(R.id.aboutApartament);

        Bundle bundle = getIntent().getExtras();
        idApartament = bundle.getInt(ClassUtils.INTENT_APARTAMENT_INFO, 0);
        updateItems();

        viewPager.setAdapter(new TabPagerAdapterApartamentInfo(getSupportFragmentManager(), getBaseContext()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    private void updateItems(){
        apartament = SQLiteDataSource.apartamentList.get(idApartament);
        aboutText.setText(apartament.getAbout());
    }

    public void onEvent(ApartamentChangeEvent event){

    }
}
