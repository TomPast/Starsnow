package Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import fragments.FragmentOACICode;
import fragments.FragmentOACIDecode;

public class ViewPagerAdapter extends FragmentPagerAdapter{
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
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
                return "Codé";
            case 1 :
                return "Décodé";
        }
        return null;
    }
}
