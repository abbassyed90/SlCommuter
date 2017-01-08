package project.as224qc.dv606.slcommuter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import project.as224qc.dv606.slcommuter.fragment.DeviationFragment;
import project.as224qc.dv606.slcommuter.fragment.RealTimeFragment;
import project.as224qc.dv606.slcommuter.fragment.SubscribedDeviationsFragment;
import project.as224qc.dv606.slcommuter.fragment.TravelFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<Fragment> fragments;

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragments = new ArrayList<>(4);
        fragments.add(new TravelFragment());
        fragments.add(new RealTimeFragment());
        fragments.add(new DeviationFragment());
        fragments.add(new SubscribedDeviationsFragment());

        // show travel fragment as first screen
        showFragment(fragments.get(0));

        // init toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        // init navigation view
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        // init drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // start alarm
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // fire the alarm the first time after 5 seconds
        // then repeated every hour
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (1000 * 5), 3600000, pendingIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawer.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawer.removeDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(null);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawers();

        if (item.getItemId() == R.id.trip) {
            showFragment(fragments.get(0));
            return true;
        } else if (item.getItemId() == R.id.realtime) {
            showFragment(fragments.get(1));
            return true;
        } else if (item.getItemId() == R.id.deviations) {
            showFragment(fragments.get(2));
            return true;
        } else if (item.getItemId() == R.id.subscribeddeviations) {
            showFragment(fragments.get(3));
            return true;
        } else if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, PreferenceActivity.class);
            startActivity(intent);
        }
        return false;
    }

    /**
     * Change fragment
     *
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.frame, fragment).commit();
    }
}
