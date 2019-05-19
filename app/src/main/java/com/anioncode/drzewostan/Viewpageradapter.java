package com.anioncode.drzewostan;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Viewpageradapter extends FragmentPagerAdapter {

    ///Tworze listy
    private final List<Fragment>fragmentsList=new ArrayList<>();
    private final List<String>FragmentListTitles=new ArrayList<>();

    public Viewpageradapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentsList.get(i);
    }

    @Override
    public int getCount() {
        return FragmentListTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentListTitles.get(position);
    }
    public void Addfragment(Fragment fragment,String title){
        fragmentsList.add(fragment);
        FragmentListTitles.add(title);

    }
}
