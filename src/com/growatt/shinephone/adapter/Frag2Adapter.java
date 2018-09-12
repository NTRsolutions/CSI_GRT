package com.growatt.shinephone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.growatt.shinephone.fragment.fragment1;
import com.growatt.shinephone.fragment.fragment2;
import com.growatt.shinephone.fragment.fragment3;
import com.growatt.shinephone.fragment.fragment4;

public class Frag2Adapter extends FragmentPagerAdapter{
	public Frag2Adapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment;
		switch (position) {
		case 0:
			fragment = new fragment1();
			break;
		case 1:
			fragment = new fragment2();
			break;
		case 2:
			fragment = new fragment3();
			break;
		case 3:
			fragment = new fragment4();
			break;
		default:
			fragment = new fragment1();
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}
}
