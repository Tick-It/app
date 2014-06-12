package com.tickit.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tickit.GroupsFragment;
import com.tickit.HomeFragment;
import com.tickit.SettingsFragment;
import com.tickit.TasksFragment;

public class TabAdapter extends FragmentPagerAdapter {

	private String[] tabs = { "Home", "Tasks", "Groups", "Settings" };
	public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
        case 0:
        	HomeFragment homefrag = new HomeFragment();
            return homefrag;
            
        case 1:
        	
        	
            TasksFragment taskfrag = new TasksFragment();
        	return taskfrag;
        case 2:
        	
            GroupsFragment groupfrag = new GroupsFragment();  
            return groupfrag;
        case 3:
        SettingsFragment settingsfrag =  new SettingsFragment();
        return settingsfrag;
    }

        return null;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    
    @Override
    public CharSequence getPageTitle (int position) {
        return tabs[position];
    }
    
    
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }
	
}
