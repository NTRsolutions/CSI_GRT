package com.growatt.shinephone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter{
	
	FragmentManager fm;
//	boolean isFirst=true;
	List<Fragment> list;
	public FragmentAdapter(FragmentManager fm,List<Fragment> list) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fm=fm;
		this.list=list;
	}

	@Override
	public Fragment getItem(int position) {
//		Fragment fragment;
//		switch (position) {
//		case 0:
//
// ��ҳ���Ź�
//			fragment = new fragment1();
//			break;
//		case 1:
//			// ����
//			fragment = new fragment2();
//			break;
//		case 2:
//			// �ҵ�
//			fragment = new fragment3();
//			break;
//		case 3:
//			// ����
//			fragment = new fragment4();
//			break;
//		default:
//			fragment = new fragment1();
//			break;
//		}
		return list.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}

}
