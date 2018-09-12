package com.growatt.shinephone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.growatt.shinephone.fragment.Analysefragment;
import com.growatt.shinephone.fragment.CommonQuestionfragment;
import com.growatt.shinephone.fragment.MyQuestionfragment;
import com.growatt.shinephone.fragment.Priorityfragment;
import com.growatt.shinephone.fragment.fragment1;
import com.growatt.shinephone.fragment.fragment2;
import com.growatt.shinephone.fragment.fragment3;
import com.growatt.shinephone.fragment.fragment4;

public class NengyuanFragmentAdapter extends FragmentPagerAdapter{
	public NengyuanFragmentAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment;
		switch (position) {
		case 0:
			fragment = new Analysefragment();
			break;
		case 1:
			fragment = new Priorityfragment();
			break;
		
		default:
			fragment = new Analysefragment();
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
}
