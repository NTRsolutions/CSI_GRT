package com.growatt.shinephone.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;

import java.io.Serializable;
import java.util.Locale;

public class BaseFragment extends Fragment implements Serializable{
	/**
     * ��ȡϵͳ����
     * @return
     */
    	public int getLanguage(){
    		int lan=1;
    		Locale locale=getResources().getConfiguration().locale;
    		String language=locale.getLanguage();
    		if(language.toLowerCase().contains("zh")){
				language="zh_cn";
				lan=0;
			}
			if(language.toLowerCase().contains("en")){
				language="en";
				lan=1;
			}
			if(language.toLowerCase().contains("fr")){
				language="fr";
				lan=2;
			}
			if(language.toLowerCase().contains("ja")){
				language="ja";
				lan=3;
			}
			if(language.toLowerCase().contains("it")){
				language="it";
				lan=4;
			}
			if(language.toLowerCase().contains("ho")){
				language="ho";
				lan=5;
			}
			if(language.toLowerCase().contains("tk")){
				language="tk";
				lan=6;
			}
			if(language.toLowerCase().contains("pl")){
				language="pl";
				lan=7;
			}
			if(language.toLowerCase().contains("gk")){
				language="gk";
				lan=8;
			}
			if(language.toLowerCase().contains("gm")){
				language="gm";
				lan=9;
			}
    		return lan;
    	}
	public void jumpTo(Class<?> clazz,boolean isFinish){
		Intent intent = new Intent(getActivity(),clazz);
		startActivity(intent);
		if(isFinish){
			getActivity().finish();
		}
	}

	public void jumpTo(Intent intent,boolean isFinish){
		startActivity(intent);
		if(isFinish){
			getActivity().finish();
		}
	}
}
