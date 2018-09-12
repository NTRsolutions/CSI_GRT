package com.growatt.shinephone.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.Putin2Adapter;
import com.growatt.shinephone.adapter.SNAdapter;
import com.growatt.shinephone.bean.UserBean;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.AddCQ;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.ImagePathUtil;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.Urlsutil;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.params.DialogParams;
import com.yalantis.ucrop.UCrop;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.json.JSONArray;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_putin_v2)
public class PutinV2Activity extends DemoBase {
    @ViewInject(R.id.recycleView)
    RecyclerView recyclerView;
    private Putin2Adapter mAdapter;
    private List<String> mList;
    private LinearLayoutManager mLayoutManager;
    @ViewInject(R.id.flPhoto)
    FrameLayout flPhoto;//打开相册或相机
    @ViewInject(R.id.flSelectPhone)
    FrameLayout flSelectPhone;
    @ViewInject(R.id.tvPhoneOrEmail) TextView tvPhoneOrEmail;
    @ViewInject(R.id.tvNote) TextView tvNote;
    @ViewInject(R.id.etPhoneOrEmail) EditText etPhoneOrEmail;
    @ViewInject(R.id.llVer) LinearLayout llVer;
    @ViewInject(R.id.btnVer) Button btnVer;//验证手机或者邮箱
    @ViewInject(R.id.tvOk) TextView tvOk;//验证手机或者邮箱
    @ViewInject(R.id.tvClickSn) TextView tvClickSn;//获取电站序列号
    @ViewInject(R.id.tvQuestion) TextView tvQuestion;//问题类型
    @ViewInject(R.id.etSN) EditText etSN;//序列号
    @ViewInject(R.id.etTitle) EditText etTitle;//标题
    @ViewInject(R.id.etContent) EditText etContent;//内容
    private String[] verStr ;
    private String[] verNote;
    private boolean isPhone = true;
    private String[] types;
    //调用相机
    private File file;
    private byte[] imageData;
    private File iconFile;
    private Uri imageUri;
    private File imageFile;
    //sn弹框数据
    private List<String> snList;
    private SNAdapter snAdapter;
    private PopupWindow popup;
    //问题类型
    private List<String> questionList ;
    private int questionType = 6;
    //裁剪图片
    private static final String SAMPLE_CROPPED_IMAGE_NAME = "Crop";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initRecycleView();
        initListener();
    }
    private void initListener() {
        llVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnVer(null);
            }
        });
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return true;
            }
        });
        tvClickSn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View arg0) {
                Mydialog.Show(PutinV2Activity.this);
                PostUtil.post(new Urlsutil().getQualityInformation, new PostUtil.postListener() {

                    @Override
                    public void success(String json) {
                        try {
                            JSONArray jsonArray=new JSONArray(json);
                            List<String> sns=new ArrayList<String>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                sns.add(jsonObject.get("deviceSN").toString());
                            }
                            Collections.sort(sns);
                            getSN(arg0,sns);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Mydialog.Dismiss();
                        }

                    }

                    @Override
                    public void Params(Map<String, String> params) {
//						params.put("plantId", Cons.plants.get(0).get("plantId").toString());
                        params.put("plantId", Cons.plant);
                        params.put("pageNum", "1");
                        params.put("pageSize", "100");
                    }
                    @Override
                    public void LoginError(String str) {}
                });
            }
        });
    }
    public void getSN(View v, final List<String> sns){
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popop_plantnames, null);
        ListView lv=(ListView)contentView.findViewById(R.id.listView1);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                String sn=sns.get(position);
                etSN.setText(sn);
                popup.dismiss();
            }
        });
        snAdapter=new SNAdapter(this, sns);
        lv.setAdapter(snAdapter);
        popup = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popup.setTouchable(true);
        popup.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popup.setBackgroundDrawable(new ColorDrawable(0));
        popup.showAsDropDown(v);
    }
    @Event(value = R.id.llQuestion)
    private void llQuestion(View v){
        new CircleDialog.Builder(this)
                .setItems(questionList, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        questionType = position + 1;
                        tvQuestion.setText(questionList.get(position));
                    }
                })
                .setNegative(getString(R.string.all_no),null)
                .show();
    }
    @Event(value = R.id.tvCancle)
    private void tvCancle(View v){
        finish();
    }
    /**
     * 提交问题
     * @param v
     */
    @Event(value = R.id.tvOk)
    private void tvOk(View v){
        if(Cons.isflag==true&&!"guest".equals(Cons.userBean.getAccountName())){
            MyControl.circlerDialog(this,getString(R.string.all_experience),-1,false);
            return;
        }
        if (isEmpty(etTitle,etSN,etContent,etPhoneOrEmail)){
            MyControl.circlerDialog(this,getString(R.string.all_blank),-1,false);
            return;
        }
        Mydialog.Show(this);
        UserBean bean = Cons.userBean;
        if (bean == null) return;
        Map<String,Object> map = new HashMap<>();
        int size = mList.size();
        for (int i = 1;i<4;i++){
            String value = size >= i ? mList.get(i-1):"";
            map.put("image"+i,value);
        }
        map.put("title", etTitle.getText().toString());
        map.put("content", etContent.getText().toString());
        map.put("userId",Cons.userId);
        map.put("questionType",questionType);
        map.put("questionDevice",etSN.getText().toString());
        //新加参数
        map.put("userId",bean.getId());
        map.put("userName",bean.getAccountName());
        map.put("country",bean.getCounrty());
        map.put("serverUrl", SqliteUtil.inquiryurl());
        if (isPhone){
            map.put("phoneNum",etPhoneOrEmail.getText().toString().trim());
            map.put("email","");
        }else {
            map.put("email",etPhoneOrEmail.getText().toString().trim());
            map.put("phoneNum","");

        }
        LogUtil.i("imageMap:"+map.toString());
        AddCQ.postUp(OssUrls.addQuestionCus(), map, new GetUtil.GetListener() {

            @Override
            public void success(String res) {
//                if(!TextUtils.isEmpty(res)){
//                    if(res.contains("true")){
//                        toast(R.string.all_success);
//                        sendBroadcast(new Intent(Constant.MyQuestionfragment_Receiver));
//                        finish();
//                    }else{
//                        toast(R.string.all_failed);
//                    }
//                }
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    int result = jsonObject.getInt("result");
                    switch (result){
                        case 1:
                            toast(R.string.all_success);
                            sendBroadcast(new Intent(Constant.MyQuestionfragment_Receiver));
                            finish();
                            break;
                        default:
                            toast(R.string.all_failed);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    toast(R.string.all_failed);
                }
            }
            @Override
            public void error(String json) {
            }
        });
    }
    /**
     * 验证手机或者邮箱
     * @param v
     */
    @Event(value = R.id.btnVer)
    private void btnVer(View v){
        if (isPhone){
            verPhone();
        }else {
            verEmail();
        }
    }

    /**
     * 验证邮箱
     */
    private void verEmail() {
        String email = etPhoneOrEmail.getText().toString().trim();
        Intent intent = new Intent(this,NewEmailVerActivity.class);
        intent.putExtra("email",email);
        intent.putExtra("type",100);
        startActivityForResult(intent,1002);
    }

    /**
     * 验证手机
     */
    private void verPhone() {
        String phone = etPhoneOrEmail.getText().toString().trim();
        Intent intent = new Intent(this,NewPhoneVerActivity.class);
        intent.putExtra("phone",phone);
        intent.putExtra("type",100);
        startActivityForResult(intent,1001);
    }

    /**
     * 切换手机或邮箱
     */
    @Event(value = R.id.flSelectPhone)
    private void flSelectPhone(View v){
        new CircleDialog.Builder(this)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        //增加弹出动画
                        params.animStyle = R.style.MyDialogStyle;
                    }
                })
                .setItems(verStr, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case 0:
                                selectPhone();
                                break;
                            case 1:
                                selectEmail();
                                break;
                        }
                    }
                })
                .setNegative(getString(R.string.all_no),null)
                .show();
    }
    /**
     * 选择邮箱后切换状态
     */
    private void selectEmail() {
        etPhoneOrEmail.setText(Cons.userBean.getEmail());
        isPhone = false;
        tvPhoneOrEmail.setText(verStr[1]);
        etPhoneOrEmail.setHint(R.string.register_email_no_blank);
        if (Cons.isValiEmail){
            MyUtils.hideAllView(View.GONE,llVer);
        }else {
            MyUtils.showAllView(llVer);
            tvNote.setText(verNote[1]);
        }
    }
    /**
     * 选择手机后切换状态
     */
    private void selectPhone() {
        etPhoneOrEmail.setText(Cons.userBean.getPhoneNum());
        isPhone = true;
        tvPhoneOrEmail.setText(verStr[0]);
        etPhoneOrEmail.setHint(R.string.register_phone_no_blank);
        if (Cons.isValiPhone){
            MyUtils.hideAllView(View.GONE,llVer);
        }else {
            MyUtils.showAllView(llVer);
            tvNote.setText(verNote[0]);
        }
    }
    private void initRecycleView() {
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        mList = new ArrayList<>();
        mAdapter = new Putin2Adapter(this,R.layout.item_putin2,mList);
        recyclerView.setAdapter(mAdapter);
    }

    private void initView() {
        verNote = new String[]{getString(R.string.m94您的手机号还未验证),getString(R.string.m95您的邮箱还未验证)} ;
        questionList = Arrays.asList(getString(R.string.putin_problem_item2),getString(R.string.putin_problem_item3)
                ,getString(R.string.putin_problem_item4),getString(R.string.putin_problem_item5),getString(R.string.putin_problem_item6)
                ,getString(R.string.putin_problem_item7));
        snList=new ArrayList<>();
        types = new String[]{getString(R.string.all_photo_album),getString(R.string.all_photograph)};
        verStr = new String[]{getString(R.string.m82手机号),getString(R.string.m83邮箱)};
        if (Cons.isValiPhone){
            selectPhone();
        }else if (Cons.isValiEmail){
            selectEmail();
        }else {
            selectPhone();
        }
    }

    @Event(value = R.id.flPhoto)
    private void flPhoto(View v){
        if (mList.size() >= 3){
            MyControl.circlerDialog(PutinV2Activity.this,getString(R.string.m92最多上传3张图片),-1,false);
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
//                            imageUri = FileProvider.getUriForFile(PutinV2Activity.this, getPackageName(), imageFile);
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
            startCropActivity(uri1);//裁剪头像
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
//                        addFileToRecyclerView(iconFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (resultCode == RESULT_OK && requestCode == 1001) {//验证手机通过
            MyUtils.hideAllView(View.GONE,llVer);
        }
        if (resultCode == RESULT_OK && requestCode == 1002) {//验证邮箱通过
            MyUtils.hideAllView(View.GONE,llVer);
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
        if (mAdapter.getItemCount() == 0){
            MyUtils.showAllView(recyclerView);
        }
        mAdapter.addAll(newList,false);
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
}
