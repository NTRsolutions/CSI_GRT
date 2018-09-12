package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.adapter.OssUserInfoAdapter;
import com.growatt.shinephone.bean.OssSearchAllUserListBean;
import com.growatt.shinephone.bean.OssUserInfoBean;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.listener.OnHandlerListener;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.mylhyl.circledialog.CircleDialog;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_oss_user_info)
public class OssUserInfoActivity extends DemoBase {
    @ViewInject(R.id.recycleView)
    RecyclerView recyclerView;
    private List<OssUserInfoBean> mList;
    private String userName;
    private OssUserInfoAdapter mAdapter;
    private List<String> names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initView();
        initRecycleView();
        initListener();
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
               if (position == 5){//修改密码
                    editPassword(position);
               }else {
                   jumpOssUserInfoEdit(position);
               }
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return true;
            }
        });
    }

    private void editPassword(int position) {
        StringBuilder sb = new StringBuilder().append(mList.get(position).getName()).append(":1234567");
        new CircleDialog.Builder(this)
                .setWidth(0.7f)
                .setTitle(getString(R.string.警告))
                .setText(sb.toString())
                .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyControl.editOssUserInfo(mContext, userName, "", "", "", "", "", "", "1", new OnHandlerListener() {
                            @Override
                            public void handlerDeal(int result,String msg) {
                                OssUtils.showOssDialog(OssUserInfoActivity.this,msg,result,true,null);
//                                handlerUserInfo.sendEmptyMessage(result);
                            }
                        });
                    }
                })
                .setNegative(getString(R.string.all_no),null)
                .show();
    }

    private void jumpOssUserInfoEdit(int position) {
       OssUserInfoBean user =  mList.get(position);
        Intent intent = new Intent(this,OssUserInfoEditActivity.class);
        intent.putExtra("userName",userName);
        intent.putExtra("title",user.getName());
        intent.putExtra("value",user.getValue());
        intent.putExtra("position",position);
        jumpTo(intent,false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
    private void initView() {
        names = Arrays.asList("修改真实姓名",getString(R.string.修改邮箱)
                ,getString(R.string.修改手机号),getString(R.string.geographydata_changetimezone)
                ,"修改公司名称","重置密码");
    }

    private void initRecycleView() {
        mList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new OssUserInfoAdapter(mContext,R.layout.item_oss_userinfo,mList);
        recyclerView.setAdapter(mAdapter);
        addData(new OssSearchAllUserListBean());
    }
    public void addData(OssSearchAllUserListBean bean){
       if (bean == null) return;
        List<String> values = Arrays.asList(bean.getActiveName(),bean.getEmail(),bean.getPhoneNum(),bean.getTimeZone()+"",bean.getCompany(),"");
        List<OssUserInfoBean> newList = new ArrayList<>();
        for (int i = 0,size = names.size();i<size;i++){
            OssUserInfoBean userBean = new OssUserInfoBean();
            userBean.setName(names.get(i));
            userBean.setValue(values.get(i));
            newList.add(userBean);
        }
        mAdapter.addAll(newList,true);
    }
    public void refresh(){
        Mydialog.Show(this);
        PostUtil.post(OssUrls.postOssSearchAll(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("searchType","0");//用户名
                params.put("param",userName);
                params.put("page","1");
                params.put("serverAddr",OssUrls.ossCRUDUrl);
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        JSONObject obj = jsonObject.getJSONObject("obj");
                        JSONArray arrUser = obj.getJSONArray("userList");
                        if (arrUser != null && arrUser.length()>0){
                            OssSearchAllUserListBean newBean = new Gson().fromJson(arrUser.getJSONObject(0).toString(),OssSearchAllUserListBean.class);
                            if (newBean != null){
                                addData(newBean);
                            }
                        }
                    }else {
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
//                        MyControl.circlerDialog(OssUserInfoActivity.this,getString(R.string.datalogcheck_check_no_server),result,false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }
            @Override
            public void LoginError(String str) {}
        });
    }
    /**
     * 处理头部
     */
    private View headerView;
    private void initHeaderView() {
        headerView = findViewById(R.id.headerView);
        setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setHeaderTitle(headerView,"用户信息");
    }
    private void initIntent() {
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
    }
    //oss用户信息handler
    private Handler handlerUserInfo = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = "";
            switch (msg.what){
                case 0:text = getString(R.string.datalogcheck_check_no_server);break;
                case 1:text = getString(R.string.修改成功);break;
                case 2:text =  "修改用户信息失败";break;
                case 3:text = "服务器地址为空";break;
                case 4:text = "邮箱为空";break;
                case 5:text = "用户不存在";break;
            }
            MyControl.circlerDialog(OssUserInfoActivity.this,text,msg.what);
        }
    };
}
