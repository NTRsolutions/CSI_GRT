package com.growatt.shinephone.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.UserQuestionListAdapter;
import com.growatt.shinephone.bean.CustomQuestionListBean;
import com.growatt.shinephone.ossactivity.OssGDReplyQuesActivity;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.PostUtil.postListener;
import com.growatt.shinephone.util.T;
import com.mylhyl.circledialog.CircleDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyQuestionfragment extends BaseFragment{
	private View view;
	private RecyclerView mRecyclerView;
	private View emptyView;
		MyReceiver receiver;
		private IntentFilter filter;
	private SwipeRefreshLayout swipeRefreshLayout;
	private ArrayList<CustomQuestionListBean> list;
	private UserQuestionListAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.myquestionfragment, container, false);
		registerBroadCast();
		list=new ArrayList<>();
		SetViews();
		adapter=new UserQuestionListAdapter(R.layout.userdata_item,list);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		mRecyclerView.setAdapter(adapter);
		adapter.setEmptyView(R.layout.empty_my_question, (ViewGroup) mRecyclerView.getParent());
		emptyView = adapter.getEmptyView();
		ImageView imageView = (ImageView) emptyView.findViewById(R.id.emptyView);
		if(getLanguage()==0){
			Glide.with(getActivity()).load(R.drawable.myqustion_emptyview).into(imageView);
		}else{
			Glide.with(getActivity()).load(R.drawable.myqustion_emptyview_en).into(imageView);
		}
		SetListeners();
//		listview.setEmptyView(emptyView);
		return view;
	}
	private void registerBroadCast() {
		receiver=new MyReceiver();
		 filter = new IntentFilter();  
	     filter.addAction(Constant.MyQuestionfragment_Receiver);  
	     filter.setPriority(1000);
	        //ע��㲥        
	     getActivity().registerReceiver(receiver, filter);
		
	}
	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(receiver);
		super.onDestroy();
	}
	private void SetViews() {
		mRecyclerView=(RecyclerView) view.findViewById(R.id.listView_myquestion);
	}

	private void SetListeners() {
		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
		swipeRefreshLayout.setColorSchemeResources(R.color.headerView);
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				getuserdata();
			}
		});
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //				Intent mIntent = new Intent(getActivity(),DatadetailedActivity.class);
				Intent mIntent = new Intent(getActivity(),OssGDReplyQuesActivity.class);
//                CustomQuestionListBean bean = list.get(position);
				CustomQuestionListBean bean = (CustomQuestionListBean) adapter.getItem(position);
				Bundle mBundle = new Bundle();
				mBundle.putString("id", bean.getId() + "");
				mBundle.putString("userId", bean.getUserId() + "");
				mBundle.putString("content", bean.getContent());
				mBundle.putString("title", bean.getTitle());
				mBundle.putString("status", bean.getStatus() + "");
				mBundle.putString("lastTime", bean.getLastTime());
				mBundle.putInt("type",100);
				mIntent.putExtras(mBundle);
				startActivity(mIntent);
            }
        });
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
				final CustomQuestionListBean bean = (CustomQuestionListBean) adapter.getItem(position);
				new CircleDialog.Builder(getActivity())
						.setWidth(0.7f)
						.setTitle(getString(R.string.all_prompt))
						.setText(getString(R.string.myquestion_isdecete))
						.setPositive(getString(R.string.all_ok), new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								deleteQuestion(bean.getUserId(),bean.getId(),position);
							}
						})
						.setNegative(getString(R.string.all_no),null)
						.show();
                return false;
            }
        });
//		listview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View view, int position,
//					long arg3) {
//				System.out.println(position);
////				position=position-1;
////				Intent mIntent = new Intent(getActivity(),DatadetailedActivity.class);
//				Intent mIntent = new Intent(getActivity(),OssGDReplyQuesActivity.class);
//				Bundle mBundle = new Bundle();
//				mBundle.putString("id", list.get(position).get("id").toString());
//				mBundle.putString("userId", list.get(position).get("userID").toString());
//				mBundle.putString("content", list.get(position).get("content").toString());
//				mBundle.putString("title", list.get(position).get("title").toString());
//				mBundle.putString("status", list.get(position).get("status").toString());
//				mBundle.putString("lastTime", list.get(position).get("lastTime").toString());
//				mBundle.putInt("type",100);
//				mIntent.putExtras(mBundle);
//				startActivity(mIntent);
//			}
//		});
//		listview.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//					final int position, long arg3) {
//				AlertDialog builder = new AlertDialog.Builder(getActivity()).setTitle(R.string.all_prompt).setMessage(R.string.myquestion_isdecete).
//						setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog, int arg1) {
//								Mydialog.Show(getActivity(), "");
//								PostUtil.post(new Urlsutil().deleteQuestion, new postListener() {
//
//									@Override
//									public void success(String json) {
//										Mydialog.Dismiss();
//										try {
//											JSONObject jsonObject=new JSONObject(json);
//											String msg=jsonObject.getString("msg");
//											if(msg.equals("200")){
//												T.make(R.string.all_success,getActivity());
//												list.remove(position);
//												adapter=new UserdataAdapter(getActivity(), list);
//												listview.setAdapter(adapter);
//												if(position>0){
//													listview.setSelection(position);
//												}
//											}else if ("701".equals(msg)){
//												T.make(R.string.m7,getActivity());
//											}else{
//												T.make(R.string.all_failed,getActivity());
//											}
//										} catch (JSONException e) {
//											e.printStackTrace();
//										}
//									}
//
//									@Override
//									public void Params(Map<String, String> params) {
//										params.put("questionId", list.get(position).get("id").toString());
//									}
//
//									@Override
//									public void LoginError(String str) {
//										// TODO Auto-generated method stub
//
//									}
//								});
//							}
//						}).setNegativeButton(R.string.all_no, new  DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog, int arg1) {
//
//							}
//						}).create();
//				builder.show();
//				return true;
//			}
//		});
		Mydialog.Show(getActivity(),"");
		getuserdata();
	}

	/**
	 * 删除问题
	 * @param userId
	 * @param id
	 */
	private void deleteQuestion(final int userId, final int id, final int position) {
		Mydialog.Show(getActivity());
		PostUtil.post(OssUrls.questionDeleteCus(), new postListener() {
			@Override
			public void Params(Map<String, String> params) {
				params.put("userId",userId + "");
				params.put("questionId",id + "");
			}

			@Override
			public void success(String json) {
				try {
					JSONObject jsonObject = new JSONObject(json);
					int result = jsonObject.getInt("result");
					if (result == 1){
						T.make(R.string.all_success,getActivity());
//						list.remove(position);
						adapter.remove(position);

//						adapter=new UserdataAdapter(getActivity(), list);
//						listview.setAdapter(adapter);
//						if(position>0){
//							listview.setSelection(position);
//						}
					}else {
						T.make(R.string.all_failed,getActivity());
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void LoginError(String str) {

			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==101){
			getuserdata();
		}
	}


	public void getuserdata(){
		PostUtil.post(OssUrls.questionListCus(), new postListener() {
			@Override
			public void Params(Map<String, String> params) {
				params.put("userId",Cons.userId);
				params.put("userName",Cons.userBean != null ? Cons.userBean.getAccountName():"");
			}

			@Override
			public void success(String json) {
                Mydialog.Dismiss();
				if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){ swipeRefreshLayout.setRefreshing(false);}
				try {
					JSONObject jsonObject = new JSONObject(json);
					int result = jsonObject.getInt("result");
					if (result == 1) {
						JSONArray array = jsonObject.getJSONObject("obj").getJSONArray("datas");
						List<CustomQuestionListBean> newList = new ArrayList<>();
						for (int i = 0, length = array.length(); i < length; i++) {
							Gson gson = new Gson();
							CustomQuestionListBean bean = gson.fromJson(array.getJSONObject(i).toString(), CustomQuestionListBean.class);
							newList.add(bean);
						}
						adapter.setNewData(newList);
					}
				}catch (Exception e){
					e.printStackTrace();
                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){ swipeRefreshLayout.setRefreshing(false);}
				}
			}
			@Override
			public void LoginError(String str) {
                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){ swipeRefreshLayout.setRefreshing(false);}
			}
		});
//		GetUtil.get(new Urlsutil().getQuestionInfoNew+"&userId="+Cons.userId, new GetListener() {
//
//			@Override
//			public void success(String json) {
//				Mydialog.Dismiss();
//				if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){ swipeRefreshLayout.setRefreshing(false);}
//				list.clear();
//				try {
//					JSONArray jsonArray=new JSONArray(json);
//					if(json.length()<20){
//						T.make(R.string.all_data_inexistence,getActivity());
//						return ;
//					}
//					for (int i = 0; i < jsonArray.length(); i++) {
//						JSONObject jsonObject=jsonArray.getJSONObject(i);
//						Map<String, Object>map=new HashMap<String, Object>();
//						map.put("id", jsonObject.get("id").toString());
//						map.put("userID", jsonObject.get("userID").toString());
//						map.put("questionType", jsonObject.get("questionType").toString());
//						map.put("questionDevice", jsonObject.get("questionDevice").toString());
//						map.put("attachment", jsonObject.get("attachment").toString());
//						map.put("remark", jsonObject.get("remark").toString());
//						map.put("title", jsonObject.get("title").toString());
//						map.put("content", jsonObject.get("content").toString());
//						map.put("status", jsonObject.get("status").toString());
//						map.put("lastTime", jsonObject.get("lastTime").toString());
//						map.put("createrTime", jsonObject.get("createrTime").toString());
//						map.put("workerId", jsonObject.get("workerId").toString());
//						map.put("groupId", jsonObject.get("groupId").toString());
//						map.put("name", jsonObject.get("name").toString());
//						map.put("country", jsonObject.get("country").toString());
//						map.put("attachmentList", jsonObject.get("attachmentList").toString());
//						map.put("serviceQuestionReplyBean", jsonObject.get("serviceQuestionReplyBean").toString());
//						map.put("accountName", jsonObject.get("accountName").toString());
//						JSONArray jsonArray2=jsonObject.getJSONArray("serviceQuestionReplyBean");
//						if(jsonArray2.toString().length()>10){
//							JSONObject jsonObject1=jsonArray2.getJSONObject(0);
//							Map<String, Object>maps=new HashMap<String, Object>();
//							maps.put("id", jsonObject1.get("id").toString());
//							maps.put("questionId", jsonObject1.get("questionId").toString());
//							maps.put("userId", jsonObject1.get("userId").toString());
//							maps.put("message", jsonObject1.get("message").toString());
//							maps.put("time", jsonObject1.get("time").toString());
//							maps.put("userName", jsonObject1.get("userName").toString());
//							maps.put("attachment", jsonObject1.get("attachment").toString());
//							maps.put("isAdmin", jsonObject1.get("isAdmin").toString());
//							maps.put("jobId", jsonObject1.get("jobId").toString());
//							maps.put("workerId", jsonObject1.get("workerId").toString());
//							map.put("serviceQuestionReplyBean", maps);
//						}else{
//							map.put("serviceQuestionReplyBean", "");
//						}
//
//						list.add(map);
//					}
//					Collections.reverse(list); // ��������
//
//					adapter.notifyDataSetChanged();
//				} catch (JSONException e) {
//					e.printStackTrace();
//					if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){ swipeRefreshLayout.setRefreshing(false);}
//				}
//			}
//
//			@Override
//			public void error(String json) {
//				if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){ swipeRefreshLayout.setRefreshing(false);}
//			}
//		});
	}
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if(this.getView()!=null){
			this.getView().setVisibility(menuVisible?View.VISIBLE:View.GONE);
		}
	}

	public void onRefresh() {
		Mydialog.Show(getActivity(),"");
		getuserdata();
	}

	 public class MyReceiver extends BroadcastReceiver {  
   	  
	       
	        @Override  
	        public void onReceive(Context context, Intent intent) {
	        	if(Constant.MyQuestionfragment_Receiver.equals(intent.getAction())){
	        		onRefresh();
	        	}
	        }  
	      
	    }  

}
