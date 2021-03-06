package com.androidlab.shiji.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.androidlab.shiji.fragment_search_result.Fragment_1;
import com.androidlab.shiji.fragment_search_result.FragmentMap;
import com.androidlab.shiji.fragment_search_result.Fragment_BookSpread;
import com.androidlab.shiji.fragment_search_result.Fragment_wordVec;

public class SearchItemResultAdapter extends FragmentPagerAdapter {
    private int size;
    private String keyword;

    public SearchItemResultAdapter(FragmentManager fm, int size, String keyword) {
        super(fm);
        this.size = size;
        this.keyword = keyword;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Fragment_wordVec.newInstance(keyword);
            case 1:
                return FragmentMap.newInstance(keyword);
            case 2:
                return Fragment_BookSpread.newInstance(keyword);
//            case 3:
//                return Fragment4.newInstance();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return size;
    }
}
