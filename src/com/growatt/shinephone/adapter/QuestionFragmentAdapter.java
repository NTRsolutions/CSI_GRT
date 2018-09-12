package com.growatt.shinephone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.growatt.shinephone.fragment.CommonQuestionfragment;
import com.growatt.shinephone.fragment.MyQuestionfragment;
import com.growatt.shinephone.fragment.fragment1;
import com.growatt.shinephone.fragment.fragment2;
import com.growatt.shinephone.fragment.fragment3;
import com.growatt.shinephone.fragment.fragment4;

public class QuestionFragmentAdapter extends FragmentPagerAdapter{
	public QuestionFragmentAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment;
		switch (position) {
		case 0:
			fragment = new MyQuestionfragment();
			break;
		case 1:
			fragment = new CommonQuestionfragment();
			break;
		
		default:
			fragment = new MyQuestionfragment();
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
