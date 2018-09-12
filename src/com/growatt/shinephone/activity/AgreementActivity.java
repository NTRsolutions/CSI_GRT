package com.growatt.shinephone.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.util.Position;

import java.io.InputStream;

public class AgreementActivity extends DoActivity {

	private TextView tv1;
	private String agreement;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agreement);
		initHeaderView();
		SetViews();
		SetListeners();
	}
	private View headerView;
	private void initHeaderView() {
		headerView = findViewById(R.id.headerView);
		setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		setHeaderTitle(headerView,getString(R.string.about_agreement));
	}
	private void SetViews() {
		tv1=(TextView)findViewById(R.id.textView1);
		if (getLanguage() == 0){
			agreement = "agreement_cn.txt";
		}else {
			agreement = "agreement.txt";
		}
		try {
			InputStream inputStream = getAssets().open(agreement);
			byte[] buffer=new byte[inputStream.available()];
            inputStream.read(buffer); 
            String resultString = new String(buffer,"utf-8");
			resultString = resultString.replace("\\n","\n");
			tv1.setText(resultString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void SetListeners() {
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
