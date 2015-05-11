package es.unileon.happycow.controller;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import es.unileon.happycow.R;
import es.unileon.happycow.controller.admin.DeleteCriterion;
import es.unileon.happycow.controller.admin.DeleteUser;
import es.unileon.happycow.controller.admin.Excel;
import es.unileon.happycow.controller.admin.NewCriterion;
import es.unileon.happycow.controller.admin.NewUser;
import es.unileon.happycow.controller.admin.BackupFragment;
import es.unileon.happycow.controller.admin.TabsPagerAdapter;

public class Administrator extends FragmentActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    // Tab titles
    //private String[] tabs = { "Top Rated", "Games", "Movies" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);

        mAdapter.addPage(new NewUser(), "Nuevo Usuario");
        mAdapter.addPage(new NewCriterion(), "Nuevo Criterio");
        mAdapter.addPage(new DeleteUser(), "Borrar Usuario");
        mAdapter.addPage(new DeleteCriterion(), "Borrar Criterio");
        mAdapter.addPage(new BackupFragment(), "BackupFragment");
        mAdapter.addPage(new Excel(), "Excel");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_administrator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }
}
