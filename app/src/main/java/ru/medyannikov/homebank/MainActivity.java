package ru.medyannikov.homebank;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;


import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import de.greenrobot.event.EventBus;
import ru.medyannikov.homebank.Activity.ApartamentsActivity;
import ru.medyannikov.homebank.Adapter.TabPagerFragmentAdapter;
import ru.medyannikov.homebank.Eventbus.BusProvider;
import ru.medyannikov.homebank.Eventbus.OperationChangeEvent;
import ru.medyannikov.homebank.Fragments.MainFragment;

public class MainActivity extends AppCompatActivity {
    private CoordinatorLayout frameMain;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private EventBus eventBus;
    private NavigationView navigationView;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        BusProvider.getInstance().register(this);
        frameMain = (CoordinatorLayout) findViewById(R.id.frameMain);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //viewPager = (ViewPager) findViewById(R.id.viewPager);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.drawerMenu);

        /*TabPagerFragmentAdapter pagerFragmentAdapter = new TabPagerFragmentAdapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(pagerFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);*/

        if (getSupportActionBar() == null)
        {
            toolbar = (Toolbar) findViewById(R.id.toolBar);
            setSupportActionBar(toolbar);
            //активируем акшн бар
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.app_name,R.string.app_name){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }
        };

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment fragment = null;
                try {
                    switch (menuItem.getItemId())
                    {
                        case R.id.menu_main:
                            fragment = (Fragment) MainFragment.getInstance();
                            break;
                        case R.id.menu_apartaments:
                            fragment = (Fragment) ApartamentsActivity.class.newInstance();
                            break;
                        default: fragment = (Fragment) MainFragment.getInstance();

                    }

                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                FragmentManager fragmentManager = getSupportFragmentManager();


                fragmentManager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.flContent, fragment)
                        .commit();


                menuItem.setChecked(true);
                getSupportActionBar().setTitle(menuItem.getTitle());
                drawerLayout.closeDrawers();


                /*if (menuItem.getItemId() == R.id.menu_apartaments)
                {
                    Intent intent = new Intent(getApplicationContext(), ApartamentsActivity.class);
                    startActivity(intent);
                    return true;
                }*/
                return false;
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Subscribe
    public void onEvent(OperationChangeEvent event){

    }
}
