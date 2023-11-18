package com.play.edit_dp_and_post.editpost_dp;

import android.content.Context;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdp extends FragmentPagerAdapter {
    ArrayList<Fragment> fragmentslist=new ArrayList<>();
    ArrayList<String> fragmentstitle=new ArrayList<>();

    private Context mcontext;
    public PageAdp(Context context,@NonNull FragmentManager fm) {
        super(fm);
        mcontext=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentslist.get(position);
    }


    @Override
    public int getCount() {
        return  fragmentslist.size();
    }
    public CharSequence getPageTitle(int position)
    {
        // return super.getPageTitle(position);
        return fragmentstitle.get(position);
    }

    public  void  addFragment(Fragment fragment,String title)
    {
        fragmentslist.add(fragment);
        fragmentstitle.add(title);
    }
}
