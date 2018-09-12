package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.v2.ProductV2Adapter;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsActivity extends DoActivity {
	/**
	 * �Ƽ���Ʒ
	 * 
	 */
	private RecyclerView recyclerView;
	private BaseQuickAdapter adapter;
	private List<Map<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products);
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
		setHeaderTitle(headerView,getString(R.string.fragment3_product));
	}
	private void SetViews() {
		recyclerView=(RecyclerView)findViewById(R.id.gridView1);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		list=new ArrayList<>();
		adapter=new ProductV2Adapter(R.layout.products_item, list);
		recyclerView.setAdapter(adapter);
		adapter.setEmptyView(R.layout.empty_view,(ViewGroup) recyclerView.getParent());
	}

	private void SetListeners() {
		adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				Map<String, Object> item = ((ProductV2Adapter) adapter).getItem(position);
				Intent intent=new Intent(ProductsActivity.this,ProductsdataActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("feature", item.get("feature").toString());
				bundle.putString("identifying", item.get("identifying").toString());
				bundle.putString("outline", item.get("outline").toString());
				bundle.putString("productImage", item.get("productImage").toString());
				bundle.putString("productName", item.get("productName").toString());
				bundle.putString("id", item.get("id").toString());
				bundle.putString("technologyParams", item.get("technologyParams").toString());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		Mydialog.Show(ProductsActivity.this, "");
		
		String url=new Urlsutil().getMoreProductList+"&language="+AppUtils.getLocale();

		
		GetUtil.get(Urlsutil.getInstance().getMoreProductList+"&language="+AppUtils.getLocale(), new GetListener() {
			
			@Override
			public void success(String json) {
				try {
					Mydialog.Dismiss();
					JSONArray jsonArray=new JSONArray(json);
					List<Map<String,Object>> newList = new ArrayList<>();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject=jsonArray.getJSONObject(i);
						Map<String, Object>map=new HashMap<String, Object>();
						map.put("id", jsonObject.get("id").toString());
						map.put("productImage", jsonObject.get("productImage").toString());
						map.put("identifying", jsonObject.get("identifying").toString());
						map.put("feature", jsonObject.get("feature").toString());
						map.put("icon", jsonObject.get("icon").toString());
						map.put("outline", jsonObject.get("outline").toString());
						map.put("productName", jsonObject.get("productName").toString());
						map.put("technologyParams", jsonObject.get("technologyParams").toString());
						newList.add(map);
					}
					adapter.setNewData(newList);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void error(String json) {
				Mydialog.Dismiss();
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
