package com.growatt.shinephone.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class ScrollViewPagerAdapter extends FragmentPagerAdapter{
	List<Fragment> frgs=new ArrayList<Fragment>();
	public ScrollViewPagerAdapter(FragmentManager fm,List<Fragment> frgs) {
		super(fm);
		this.frgs=frgs;
	}

	@Override
	public Fragment getItem(int arg0) {
		return frgs.get(arg0);
	}

	@Override
	public int getCount() {
		return frgs.size();
	}
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
    	// TODO Auto-generated method stub
    	return super.instantiateItem(container, position);
    }
    
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
	}
	
}
