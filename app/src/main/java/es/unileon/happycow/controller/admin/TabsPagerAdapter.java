package es.unileon.happycow.controller.admin;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dorian on 20/04/15.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> pages;
    private ArrayList<String> titles;

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
        pages=new ArrayList<>();
        titles=new ArrayList<>();
    }

    public void addPage(Fragment page, String title){
        pages.add(page);
        titles.add(title);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int index) {
        if(index<getCount()){
            return pages.get(index);
        }else{
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position<getCount()){
            return titles.get(position);
        }else{
            return "";
        }
    }

    @Override
    public int getCount() {
        return pages.size();
    }
}
