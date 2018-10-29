package edu.nyu.foodvoid.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.nyu.foodvoid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private static final String[] tabNames = {"Recipes","Restaurants"};
    private static final int tabCount = 2;


    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_tab,container,false);

        tabLayout = v.findViewById(R.id.tabLayout1);
        viewPager = v.findViewById(R.id.viewPager1);

        viewPager.setAdapter(new TabFragmentAdapter(getChildFragmentManager()));

        viewPager.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });


        return v;
    }


    public class TabFragmentAdapter extends FragmentPagerAdapter{

        public TabFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:

                    Recipes recipes = new Recipes();
                    return recipes;


                case 1:
                    Restaurants restaurants = new Restaurants();
                    return restaurants;
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabCount;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames[position];
        }
    }

}
