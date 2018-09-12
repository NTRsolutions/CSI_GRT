package com.growatt.shinephone.ossactivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.activity.QuesImageListActivity;
import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.adapter.CusQuestionDeticalAdapter;
import com.growatt.shinephone.adapter.OssReplyQuesAdapter;
import com.growatt.shinephone.bean.CustomQuestionDeticalBean;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.AddCQ;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.ImagePathUtil;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.params.DialogParams;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 只针对server问题详情
 */
@ContentView(R.layout.activity_oss_gdreply_ques)
public class OssGDReplyQuesActivity extends DemoBase {
    @ViewInject(R.id.listView)
    ListView listView;
    @ViewInject(R.id.ivSend)
    ImageView ivSend;//发送按钮
    @ViewInject(R.id.ivNoSend)
    ImageView ivNoSend;//不发送按钮
    @ViewInject(R.id.etContent)
    EditText etContent;//编辑内容
    @ViewInject(R.id.flReply)
    FrameLayout flReply;//回复问题
    //图片列表数据
    @ViewInject(R.id.recycleViewPhoto)
    RecyclerView recyclerView;
    private List<String> fileList;
    private OssReplyQuesAdapter fileAdapter;
    private LinearLayoutManager fileManager;
    @ViewInject(R.id.flPhoto)
    FrameLayout flPhoto;//打开相册或相机
    private String[] types;
    private File file;
    private byte[] imageData;
    private File iconFile;
    private Uri imageUri;
    private File imageFile;
    //头部数据
    private TextView tvTitle;
    private TextView tvQuestionType;
    private TextView tvQuestionDevice;
    private TextView tvStatus;
    private TextView tvTime;
    private TextView tvContent;
    private View tvImage;
    private ImageView ivPhoto;
    private TextView tvPhone;
    private TextView tvEmail;
    private LinearLayout llPhoneOrEmail;
    private ArrayList<String> headImgList = new ArrayList<>();
    private int type;//100:代表server;101代表oss
    //数据来至server
    private View header_replyques;
    private CusQuestionDeticalAdapter mAdapter;
    private List<CustomQuestionDeticalBean.ReplyList> mList;
    private String questionId;
    private String userId;
    private String status;
    private String lastTime;
    private String title;
    private String content;
    private CustomQuestionDeticalBean bean = new CustomQuestionDeticalBean();
    //数据来自oss
    private String serverUrl;//问题服务器地址
    //裁剪图片
    private static final String SAMPLE_CROPPED_IMAGE_NAME = "Crop";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initView();
        initListView();
        initRecycleView();
        switch (type){
            case 100:
                refreshServer(questionId,userId);
                break;
            case 101:
                refreshOss(questionId,serverUrl);
                break;
        }
    }

    /**
     * 刷新oss问题详情
     * @param questionId
     * @param serverUrl
     */
    private void refreshOss(final String questionId, final String serverUrl) {
        Mydialog.Show(this);
        PostUtil.post(OssUrls.postOssGD_questionDetial(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("questionId",questionId);
                params.put("serverUrl",serverUrl);
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        String newJson = jsonObject.getJSONObject("obj").toString();
                        parseQuesDetialJson(newJson);
                    }else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void LoginError(String str) { }
        });
    }

    private void initRecycleView() {
        fileList = new ArrayList<>();
        fileManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(fileManager);
        fileAdapter = new OssReplyQuesAdapter(mContext,R.layout.item_putin2,fileList,recyclerView);
        recyclerView.setAdapter(fileAdapter);
    }

    private void initIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        type = bundle.getInt("type");
        userId = bundle.getString("userId");
        switch (type){
            case 100:
                if(TextUtils.isEmpty(userId)){
                    List<Map<String, Object>> sss = SqliteUtil.inquiryids(SqliteUtil.inquiryplant());
                    userId = sss.get(0).get("userId").toString();
                }
                break;
            case 101:
                serverUrl = bundle.getString("serverUrl");
                break;
        }
        questionId = bundle.getString("id");
        status = bundle.getString("status");
        lastTime = bundle.getString("lastTime");
        title = bundle.getString("title");
        content = bundle.getString("content");
    }

    private void initView() {
        types = new String[]{getString(R.string.all_photo_album),getString(R.string.all_photograph)};
        //头部空间初始化
        header_replyques = LayoutInflater.from(mContext).inflate(R.layout.header_ossgd_detical,listView,false);
        tvTitle = (TextView) header_replyques.findViewById(R.id.tvTitle);
        tvQuestionType = (TextView) header_replyques.findViewById(R.id.tvQuestionType);
        tvQuestionDevice = (TextView) header_replyques.findViewById(R.id.tvQuestionDevice);
        tvStatus = (TextView) header_replyques.findViewById(R.id.tvStatus);
        tvTime = (TextView) header_replyques.findViewById(R.id.tvTime);
        tvContent = (TextView) header_replyques.findViewById(R.id.tvContent);
        tvImage = header_replyques.findViewById(R.id.tvImage);
        ivPhoto = (ImageView) header_replyques.findViewById(R.id.ivPhoto);
        //oss头部多两个参数
        tvPhone = (TextView) header_replyques.findViewById(R.id.tvPhone);
        tvEmail = (TextView) header_replyques.findViewById(R.id.tvEmail);
        llPhoneOrEmail = (LinearLayout) header_replyques.findViewById(R.id.llPhoneOrEmail);


        tvTitle.setText(title);
        tvTime.setText(lastTime);
        tvContent.setText(content);
        dealStatus(Integer.parseInt(status),tvStatus);
        Glide.with(mContext).load(Environment.getExternalStorageDirectory()+"/"+ ShineApplication.IMAGE_FILE_LOCATION)
                .bitmapTransform(new CropCircleTransformation(mContext))
                .placeholder(R.drawable.ossgd_user)
                .error(R.drawable.ossgd_user)
                .into(ivPhoto);
    }

    private void initListView() {
        listView.addHeaderView(header_replyques);
        mList = new ArrayList<>();
        mAdapter = new CusQuestionDeticalAdapter(mContext,R.layout.item_ossgd_replyques,mList);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listView 头部
                if (position == 0){
                    if (tvImage.getVisibility() == View.VISIBLE){
                        //跳转界面：传递headImgList
                        Intent intent = new Intent(mContext, QuesImageListActivity.class);
                        intent.putStringArrayListExtra("img",headImgList);
                        intent.putExtra("type",0);
                        startActivity(intent);
                    }
                }else {
                    CustomQuestionDeticalBean.ReplyList quesBean = mAdapter.getItem(position - 1);
                    View imgView = view.findViewById(R.id.tvImage);
                    ArrayList<String> imgs = dealImage(quesBean.getAttachment(),imgView);
                    if (imgs != null){
                        Intent intent = new Intent(mContext, QuesImageListActivity.class);
                        intent.putStringArrayListExtra("img",imgs);
                        intent.putExtra("type",0);
                        startActivity(intent);
                    }
                }
            }
        });
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0){
                    showIvSend(true);
                }else {
                    showIvSend(false);
                }
            }
        });
        flReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivSend.getVisibility() != View.VISIBLE) return;
                //做回复问题处理
                switch (type){
                    case 100:
                        replyServerQuestion();
                        break;
                    case 101:
                        replyOssQuestion();
                        break;
                }
            }
        });
    }

    /**
     * server问题回复
     */
    private void replyServerQuestion() {
        Mydialog.Show(this);
        Map<String,Object> map = new HashMap<>();
        map.put("questionId", questionId);
        int size = fileList.size();
        for (int i = 1;i<4;i++){
            String value = size >= i ? fileList.get(i-1):"";
            map.put("image"+i,value);
        }
        map.put("userId", Cons.userId);
        map.put("message", etContent.getText().toString().trim());
        String userName = "";
        if (Cons.userBean != null){
            userName = Cons.userBean.getAccountName();
        }else {
            userName = bean.getQuestion().getAccountName();
        }
        map.put("userName",userName);
        LogUtil.i("imageMap:"+map.toString());
        AddCQ.postUp(OssUrls.questionReplyCus(), map, new GetUtil.GetListener() {
            @Override
            public void error(String json) {
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    switch (result){
                        case 1:
                         toast(R.string.all_success);
                        clearReplyData();//清理回复数据
                        refreshServer(questionId,userId);//刷新界面数据
                            break;
                        default:
                            toast(R.string.all_failed);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }
        });
//        AddCQ.postUp(new Urlsutil().replyMessage, map, new GetUtil.GetListener() {
//
//            @Override
//            public void success(String json) {
//                if(TextUtils.isEmpty(json)) return;
//                try {
//                    JSONObject jsonObject = new JSONObject(json);
//                    boolean success = jsonObject.getBoolean("success");
//                    if (success){
//                        toast(R.string.all_success);
//                        clearReplyData();//清理回复数据
//                        refreshServer(questionId,userId);//刷新界面数据
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Mydialog.Dismiss();
//                }
//            }
//            @Override
//            public void error(String json) {}
//        });

    }

    /**
     * 清理回复数据
     */
    private void clearReplyData() {
        etContent.setText("");
        fileAdapter.addAll(new ArrayList<String>(),true);
        MyUtils.hideAllView(View.GONE,recyclerView);//隐藏控件
    }

    /**
     * oss工单问题回复
     */
    private void replyOssQuestion() {
        Mydialog.Show(this);
        Map<String,Object> map = new HashMap<>();
        map.put("questionId", questionId);
        int size = fileList.size();
        for (int i = 1;i<4;i++){
            String value = size >= i ? fileList.get(i-1):"";
            map.put("image"+i,value);
        }
        map.put("serverUrl", serverUrl);
        map.put("message", etContent.getText().toString().trim());
        LogUtil.i("imageMap:"+map.toString());
        AddCQ.postUp(OssUrls.postOssGD_questionReply(), map, new GetUtil.GetListener() {
            @Override
            public void success(String json) {
                if(TextUtils.isEmpty(json)) return;
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        toast(R.string.all_success);
                        clearReplyData();//清理回复数据
                        refreshOss(questionId,serverUrl);//刷新界面数据
                    }else {
                        handlerReplyOss.sendEmptyMessage(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }
            @Override
            public void error(String json) {}
        });
    }

    /**
     * 设置发送图标
     * @param isSend
     */
    public void showIvSend(boolean isSend){
        if (isSend){
            MyUtils.showAllView(ivSend);
            MyUtils.hideAllView(View.INVISIBLE,ivNoSend);
        }else {
            MyUtils.showAllView(ivNoSend);
            MyUtils.hideAllView(View.INVISIBLE,ivSend);
        }
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
        setHeaderTitle(headerView,getString(R.string.问题内容));
        setHeaderTvTitle(headerView, getString(R.string.m100关闭问题), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder(OssGDReplyQuesActivity.this)
                        .setWidth(0.7f)
                        .setTitle(getString(R.string.reminder))
                        .setText(getString(R.string.m102是否关闭问题))
                        .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setQuestionOK();
                            }
                        })
                        .setNegative(getString(R.string.all_no),null)
                        .show();
            }
        });
    }

    /**
     * 设置问题为已解决
     */
    private void setQuestionOK() {
        Mydialog.Show(mContext);
        PostUtil.post(OssUrls.questionSolveCus(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("questionId",questionId);
                String userId = Cons.userId;
                if (TextUtils.isEmpty(userId) && Cons.userBean != null){
                    userId = Cons.userBean.getId();
                }
                params.put("userId",userId);
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        MyControl.circlerDialog(OssGDReplyQuesActivity.this,getString(R.string.m103操作成功),-1,true);
                    }else {
                        MyControl.circlerDialog(OssGDReplyQuesActivity.this,getString(R.string.all_failed),result,false);
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

    public void refreshServer(final String questionId, final String userId){
        PostUtil.post(OssUrls.questionDetailCus(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("questionId",questionId );
                params.put("userId",userId );
            }

            @Override
            public void success(String json) {
                parseQuesDetialJson(json);
            }

            @Override
            public void LoginError(String str) {

            }
        });
//        GetUtil.getParams(new Urlsutil().getQuestionInfoDetail, new PostUtil.postListener() {
//            @Override
//            public void Params(Map<String, String> params) {
//                params.put("questionId",questionId );
//                params.put("userId",userId );
//            }
//            @Override
//            public void success(String json) {
//                parseQuesDetialJson(json);
//            }
//            @Override
//            public void LoginError(String str) {
//            }
//        });
    }

    /**
     * 解析oss和server的json数据
     * @param json
     */
    private void parseQuesDetialJson(String json) {
        try{
            JSONObject jsonObject = new JSONObject(json);
            int result = jsonObject.getInt("result");
            if (result != 1) return;

        Gson gson = new Gson();
        bean = gson.fromJson(jsonObject.getJSONObject("obj").getJSONObject("datas").toString(),CustomQuestionDeticalBean.class);
        CustomQuestionDeticalBean.Question question = bean.getQuestion();
        if (question == null) return;
        tvTitle.setText(question.getTitle());
        tvTime.setText(question.getLastTime());
        tvContent.setText(question.getContent());
        tvQuestionDevice.setText(question.getQuestionDevice());
        //头部图片处理
        headImgList = dealImage(question.getAttachment(),tvImage);
        //问题类型
        tvQuestionType.setText(Cons.questionType[Integer.parseInt(question.getQuestionType())]);
        //问题状态
        int status = question.getStatus();
        dealStatus(status,tvStatus);
        mAdapter.addAll(bean.getReplyList(),true);
        listView.setSelection(mAdapter.getCount() - 1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //头部图片处理
    public ArrayList<String> dealImage(String attachment,View view){
        ArrayList<String> imgs = null;
        if (TextUtils.isEmpty(attachment) || "null".equals(attachment)){
            MyUtils.hideAllView(View.INVISIBLE,view);
        }else {
            MyUtils.showAllView(view);
            imgs = new ArrayList<>();
            if (attachment.contains("_")){
                String[] images = attachment.split("_");
                for(String imageStr:images){
                    if (imageStr.length()>0){imgs.add(imageStr);}
                }
            }else {
                imgs.add(attachment);
            }
        }
        return imgs;
    }
    //处理状态
    public void dealStatus(int status,TextView tvStatus){
        switch (status){
            case 0:
                tvStatus.setTextColor(ContextCompat.getColor(mContext,R.color.ossgd_ques_progress_no));
                tvStatus.setText(R.string.m73待处理);
                break;
            case 1:
                tvStatus.setTextColor(ContextCompat.getColor(mContext,R.color.ossgd_ques_progress_processing));
                tvStatus.setText(R.string.m74处理中);
                break;
            case 2:
                tvStatus.setTextColor(ContextCompat.getColor(mContext,R.color.ossgd_ques_progress_yes));
                tvStatus.setText(R.string.m75已处理);
                break;
            case 3:
                tvStatus.setTextColor(ContextCompat.getColor(mContext,R.color.ossgd_ques_progress_wait));
                tvStatus.setText(R.string.m76待跟进);
                break;
        }
    }
    /*
    打开相机相册
     */
    @Event(value = R.id.flPhoto)
    private void flPhoto(View v){
        if (fileList.size() >= 3){
            return;
        }
        new CircleDialog.Builder(this)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        //增加弹出动画
                        params.animStyle = R.style.MyDialogStyle;
                    }
                })
                .setItems(types, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        imageFile  = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + ".jpg");
//                        if (Build.VERSION.SDK_INT >= 24) {
//                            imageUri = FileProvider.getUriForFile(OssGDReplyQuesActivity.this, getPackageName(), imageFile);
//                        } else {
                            imageUri = Uri.fromFile(imageFile);
//                        }
                        switch (position){
                            case 0:
                                selectPicture();
                                break;
                            case 1:
                                takePicture();
                                break;
                        }
                    }
                })
                .setNegative(getString(R.string.all_no),null)
                .show();
    }

    /**
     * 拍照
     */
    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //将拍照的图片保存到本地
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + ".jpg");
        Uri iconUri = null;
//        if (Build.VERSION.SDK_INT >= 24) {
//            iconUri = FileProvider.getUriForFile(this, getPackageName(), file);
//        } else {
            iconUri = Uri.fromFile(file);
//        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, iconUri);
        startActivityForResult(intent, 102);
    }

    /**
     * 从相册选择
     */
    public void selectPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 101);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 101) {//从相册获取
            Uri uri = data.getData();
            //根据uri查找图片地址
            ContentResolver resolver = getContentResolver();
            String[] pojo = {MediaStore.Images.Media.DATA};
            Cursor cursor = resolver.query(uri, pojo, null, null, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
                cursor.moveToFirst();
                String path = cursor.getString(columnIndex);
                file = new File(path);
                cursor.close();
            }
//            crop(uri);//裁剪头像
            startCropActivity(uri);
        } else if (resultCode == RESULT_OK && requestCode == 102) {//拍照
            Uri uri1 = null;
//            if (Build.VERSION.SDK_INT >= 24) {
//                uri1 = getImageContentUri(this, file);
//            } else {
                uri1 = Uri.fromFile(file);
//            }
//            crop(uri1);//裁剪头像
            startCropActivity(uri1);
        }
        if (resultCode == RESULT_OK && requestCode == 103) {//收到截取后的图像
            if (imageUri != null){
                Bitmap bitmap = decodeUriAsBitmap(imageUri);
                if (bitmap != null) {
                    //将头像转为二进制数组写入临时文件传给服务器
                    ByteArrayOutputStream baos = new ByteArrayOutputStream(bitmap.getWidth() * bitmap.getHeight() * 4);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    imageData = baos.toByteArray();
                    try {
                        iconFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath(), System.currentTimeMillis() + "quesIcon.png");
                        if (!iconFile.exists()) {
                            iconFile.createNewFile();
                        }
                        FileOutputStream out = new FileOutputStream(iconFile);
                        out.write(imageData);
                        out.flush();
                        out.close();
                        addFileToRecyclerView(iconFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
             }
            }
        //裁剪
        if (resultCode == RESULT_OK) {
            if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void startCropActivity(@NonNull Uri uri) {
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
        destinationFileName += System.currentTimeMillis()+".jpg";

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));

//        uCrop = basisConfig(uCrop);
        uCrop = advancedConfig(uCrop);
//
        uCrop.start(this);
    }
    /**
     * Sometimes you want to adjust more options, it's done via {@link com.yalantis.ucrop.UCrop.Options} class.
     *
     * @param uCrop - ucrop builder instance
     * @return - ucrop builder instance
     */
    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();

        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        return uCrop.withOptions(options);
    }
    /**
     * In most cases you need only to set crop aspect ration and max size for resulting image.
     *
     * @param uCrop - ucrop builder instance
     * @return - ucrop builder instance
     */
    private UCrop basisConfig(@NonNull UCrop uCrop) {
        return uCrop;
    }
    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            String path = ImagePathUtil.getRealPathFromUri(this,resultUri);
            addFileToRecyclerView(path);
            LogUtil.i("path=="+ path);
        } else {
            toast(R.string.all_failed);
        }
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e("TAG", "handleCropError: ", cropError);
            Toast.makeText(this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            toast(R.string.all_failed);
        }
    }
    public void addFileToRecyclerView(String filePath){
        List<String> newList = new ArrayList<>();
        newList.add(filePath);
        if (fileAdapter.getItemCount() == 0){
            MyUtils.showAllView(recyclerView);
        }
        fileAdapter.addAll(newList,false);
    }
    private Bitmap decodeUriAsBitmap(Uri uri){
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 添加图片到控件，初次添加时显示控件，隐藏控件逻辑在adapter中
     * @param file
     */
    public void addFileToRecyclerView(File file){
        List<String> newList = new ArrayList<>();
        newList.add(file.getAbsolutePath());
        if (fileAdapter.getItemCount() == 0){
            MyUtils.showAllView(recyclerView);
        }
        fileAdapter.addAll(newList,false);
    }
    /**
     * 7.0 获取图片文件uri
     *
     * @param context
     * @param file
     * @return
     */
    private Uri getImageContentUri(Context context, File file) {
        String filePath = file.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=?", new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (file.exists()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            }
        }
        return null;
    }
    /***
     * 调用android 自带的裁剪功能，对图片进行裁剪
     *
     * @param uri 被裁剪图片的路径
     */
    private void crop(Uri uri) {
        //隐式意图调用android自身的图片截取界面
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 9);
        intent.putExtra("aspectY", 16);
        intent.putExtra("outputX", 450);
        intent.putExtra("outputY", 800);
        //设置了true的话直接返回bitmap，可能会很占内存
        intent.putExtra("return-data", false);
        //设置输出的格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //设置输出的地址
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, 103);//强截取的头像返回（bundle）
    }
    Handler handlerReplyOss = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = "";
            switch (msg.what){
                case 0 :
                    text = getString(R.string.datalogcheck_check_no_server);
                    break;
                case 2:
                    text = getString(R.string.all_failed);
                    break;
                case 3:
                    text = "登录超时";
                    break;
                case 4:
                    text = "搜索服务器地址为空";
                    break;
                case 5:
                    text = "问题不存在";
                    break;
                case 6:
                    text = "客服不存在";
                    break;
                case 7:
                    text = "图片上传失败";
                    break;
                case 21:
                    text = "搜索不到信息";
                    break;
                default:text = getString(R.string.datalogcheck_check_no_server);
                    break;
            }
            MyControl.circlerDialog(OssGDReplyQuesActivity.this,text,msg.what,false);
        }
    };
}
