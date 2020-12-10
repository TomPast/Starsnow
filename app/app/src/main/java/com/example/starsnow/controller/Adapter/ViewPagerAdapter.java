package com.example.starsnow.controller.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.starsnow.R;
import com.example.starsnow.controller.fragments.FragmentOACICode;
import com.example.starsnow.controller.fragments.FragmentOACIDecode;

public class ViewPagerAdapter extends FragmentPagerAdapter{
    private Context context;
    public ViewPagerAdapter(FragmentManager fm, Context c) {
        super(fm);
        context = c;
    }
    /**
     * Return fragment with respect to Position .
     */
    @Override
    public Fragment getItem(int position)
    {
        switch (position){
            case 0 : return new FragmentOACICode();
            case 1 : return new FragmentOACIDecode();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 2;
    }
    /**
     * This method returns the title of the tab according to the position.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 :
                return context.getString(R.string.code);
            case 1 :
                return context.getString(R.string.decode);
        }
        return null;
    }
}
