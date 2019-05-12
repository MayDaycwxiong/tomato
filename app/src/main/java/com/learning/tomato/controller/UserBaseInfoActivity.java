package com.learning.tomato.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.learning.tomato.R;
import com.learning.tomato.dto.UserDTO;
import com.learning.tomato.dto.UserPO;
import com.learning.tomato.until.BaseActivity;
import com.learning.tomato.until.paramUtil.DateTranslate;
import com.learning.tomato.until.paramUtil.ObjectUtil;
import com.learning.tomato.until.fastdfs.FastDFSClient;
import com.learning.tomato.until.netUtil.OkManager;
import com.learning.tomato.until.picture.GetPath;
import com.learning.tomato.until.MyStaticResource;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;
import com.learning.tomato.until.paramUtil.StringUtil;

public class UserBaseInfoActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "UserBaseInfoActivity";
    private OkManager okManager=null;
    private String url=MyStaticResource.UPDATEBASEINFOURL;
    private String getAllInfoUrl=MyStaticResource.SELECTALLUSERINFOURL;
    private Button editHeadIcon;
    private AlertDialog dialog;
    private TextView photograph;
    private TextView album;
    private ImageView myHeadIcon;
    private TextView uesrId,userName,userSex,userBirthday,userAge,userRole,userAdd,userMotto;
    private ImageView image1,image2,image3,image4,image5,image6,image7;
    private File cameraFile;
    private Map<String,String> map=new HashMap<>();
    private Map<Integer,String> bindViewWithId=new HashMap<>();
    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE = 3;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_base_info);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        createDialog();
        editHeadIcon = findViewById(R.id.btn_userInfo);
        editHeadIcon.setOnClickListener(this);
        myHeadIcon = findViewById(R.id.myHeadIcon);
        bindViewWithId.put(R.id.myHeadIcon,"usericon"+"&");
        uesrId=findViewById(R.id.userId);

        userName=findViewById(R.id.userName);
        userName.setOnClickListener(this);
        bindViewWithId.put(R.id.userName,"username"+"&昵称");
        userSex=findViewById(R.id.userSex);
        userSex.setOnClickListener(this);
        bindViewWithId.put(R.id.userSex,"usersex"+"&性别");
        userBirthday=findViewById(R.id.userBirthday);
        userBirthday.setOnClickListener(this);
        bindViewWithId.put(R.id.userBirthday,"birthday"+"&出生日期");

        userAge=findViewById(R.id.userAge);
        userAge.setOnClickListener(this);
        bindViewWithId.put(R.id.userAge,"userage"+"&年龄");

        userRole=findViewById(R.id.userRole);
        userRole.setOnClickListener(this);
        bindViewWithId.put(R.id.userRole,"userrole"+"&职业");

        userAdd=findViewById(R.id.userAdd);
        userAdd.setOnClickListener(this);
        bindViewWithId.put(R.id.userAdd,"useraddr"+"&地址");

        userMotto=findViewById(R.id.userMotto);
        userMotto.setOnClickListener(this);
        bindViewWithId.put(R.id.userMotto,"usermotto"+"&座右铭");

        image1=findViewById(R.id.image1);
        image1.setOnClickListener(this);
        image2=findViewById(R.id.image2);
        image2.setOnClickListener(this);
        image3=findViewById(R.id.image3);
        image3.setOnClickListener(this);
        image4=findViewById(R.id.image4);
        image4.setOnClickListener(this);
        image5=findViewById(R.id.image5);
        image5.setOnClickListener(this);
        image6=findViewById(R.id.image6);
        image6.setOnClickListener(this);
        image7=findViewById(R.id.image7);
        image7.setOnClickListener(this);
        okManager=OkManager.getInstance();
        Intent intent=getIntent();
        // 初始化组件
        userid=intent.getStringExtra("userid");
        initView(userid);
    }

    /**
     * 请求服务器返回用户基本信息，并加载到对应控件中去
     * @param userid
     */
    private void initView(String userid) {
        Log.e(TAG,"从Intent中获得的用户账号为："+userid);
        map.put("userid",userid);
        okManager.asynJsonObjectByRequest(getAllInfoUrl,map, new OkManager.Func1() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG,result);
                UserDTO userDTO= JSON.parseObject(result,UserDTO.class);
                Log.e(TAG,"响应消息:"+userDTO);
                Log.e(TAG,"响应标识:"+userDTO.getFlag());
                UserPO userPO=userDTO.getUserPO();
                resultMapping(userPO);
            }
        });
    }

    /**
     * 组件初始化工作
     * @param userPO
     */
    private void resultMapping(UserPO userPO) {
        if(ObjectUtil.isNull(userPO)){
            Log.e(TAG,"获得的用户对象为空");
            return;
        }
        if(StringUtil.isNotEmpty(userPO.getUsericon())){
            Glide.with(this).load(userPO.getUsericon()).into(myHeadIcon);
        }
        uesrId.setText(StringUtil.isNotEmpty(userPO.getUserid())?userPO.getUserid():MyStaticResource.EMPTY);
        userName.setText(StringUtil.isNotEmpty(userPO.getUsername())?userPO.getUsername():MyStaticResource.EMPTY);
        userSex.setText(StringUtil.isNotEmpty(userPO.getUsersex())?userPO.getUsersex():MyStaticResource.EMPTY);
        if(ObjectUtil.isNotNull(userPO.getUserbirthday())){
            userBirthday.setText(DateTranslate.translateToString(userPO.getUserbirthday()));
        }
        if(ObjectUtil.isNotNull(userPO.getUserage())){
            userAge.setText(userPO.getUserage()+"");
        }
        userRole.setText(StringUtil.isNotEmpty(userPO.getUserrole())?userPO.getUserrole():MyStaticResource.EMPTY);
        userAdd.setText(StringUtil.isNotEmpty(userPO.getUseraddr())?userPO.getUseraddr():MyStaticResource.EMPTY);
        userMotto.setText(StringUtil.isNotEmpty(userPO.getUsermotto())?userPO.getUsermotto():MyStaticResource.EMPTY);
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserBaseInfoActivity.this);
        dialog = builder.create();
        View dialogView = View.inflate(UserBaseInfoActivity.this, R.layout.selectimage, null);
        dialog.setView(dialogView);
        photograph = dialogView.findViewById(R.id.photograph);
        album = dialogView.findViewById(R.id.album);
        photograph.setOnClickListener(this);
        album.setOnClickListener(this);
    }

    public static void actionStart(Context context,String userid) {
        Intent intent = new Intent(context, UserBaseInfoActivity.class);
        intent.putExtra("userid",userid);
        context.startActivity(intent);
        Log.e(TAG, "into " + TAG);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_userInfo:
                dialog.show();
                break;
            case R.id.photograph:
                /**
                 * 解决缩略图
                 */
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String filename =userid + ".jpg";
                cameraFile = new File(Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM + File.separator + "Camera" + File.separator + filename);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
                dialog.dismiss();
                break;
            case R.id.album:
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("image/*");
                startActivityForResult(intent1, GALLY_REQUEST_CODE);
                dialog.dismiss();
                break;
                case R.id.userName:
                case R.id.image1:
                    editDialog(R.id.userName);
                    break;
            case R.id.userSex:
            case R.id.image2:
                editDialog(R.id.userSex);
                break;
            case R.id.userBirthday:
            case R.id.image3:
                editDialog(R.id.userBirthday);
                break;
            case R.id.userAge:
            case R.id.image4:
                editDialog(R.id.userAge);
                break;
            case R.id.userRole:
            case R.id.image5:
                editDialog(R.id.userRole);
                break;
            case R.id.userAdd:
            case R.id.image6:
                editDialog(R.id.userAdd);
                break;
            case R.id.userMotto:
            case R.id.image7:
                editDialog(R.id.userMotto);
                break;
        }
    }
    private void editDialog(final int id){
        LayoutInflater inflater=getLayoutInflater();
        View layout=inflater.inflate(R.layout.baseinfo_dialog, (ViewGroup) findViewById(R.id.edit_dialog));
        final EditText lable_value=layout.findViewById(R.id.lable_value);
        TextView lable_key=layout.findViewById(R.id.lable_key);
        final String value=bindViewWithId.get(id);
        lable_key.setText(value.substring(value.lastIndexOf('&')+1));
        new AlertDialog.Builder(UserBaseInfoActivity.this).setTitle("基本信息修改").setView(layout)
        .setNegativeButton("取消", null)
        .setPositiveButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                map.put(value.substring(0,value.indexOf('&')),lable_value.getText().toString());
                updateData(map,value.substring(0,value.indexOf('&')),null,(TextView) findViewById(id));
            }
        }).show();
    }


    /**
     * @param requestCode
     * @param resultCode
     * @param data        用户选择的图片
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (cameraFile.exists()) {
                Log.i(TAG, "摄像头剪裁");
                Bitmap bitmap = null;
                try {
                    FileInputStream fis = new FileInputStream(cameraFile);
                    bitmap = BitmapFactory.decodeStream(fis);
                    fis.close();
                    Uri uri = saveBitmap(bitmap);
                    startImageZoom(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == GALLY_REQUEST_CODE) {
            if (data == null) {
                return;
            }
            /**
             * 获取图片的同意资源标识符
             */
            Uri uri = data.getData();
            Uri fileUri = convertUri(uri);
            startImageZoom(fileUri);
        } else if (requestCode == CROP_REQUEST_CODE) {
            if (data == null) {
                return;
            }
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            Uri uri;
            Log.e(TAG, "开始裁剪");
            if (data.getData() != null) {
                Log.e(TAG, "开始裁剪data.getData() ViVo 可用");
                uri = data.getData();
            } else {
                Log.e(TAG, "开始裁剪data.getData() not ViVo 可用");
                uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
            }
            //resetIcon(uri.toString());
            String path = GetPath.getInstance().UriToPath(uri, UserBaseInfoActivity.this);
            if (path == null) {
                path = GetPath.getInstance().getPath(UserBaseInfoActivity.this, uri);
            }
            if (path != null) {

                final String finalPath = path;
                Log.e(TAG, "图片的路径为: " + path);
                MyStaticResource.MTHREADPOOL.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            File file = new Compressor(UserBaseInfoActivity.this)
                                    .setMaxWidth(640)
                                    .setMaxHeight(480)
                                    .setQuality(75)
                                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                    .compressToFile(new File(finalPath));
                            final String otherHalfUrl=uploadFile(file, finalPath.substring(finalPath.lastIndexOf('.') + 1));
                            if(otherHalfUrl==null){
                                Log.e(TAG,"图片上传失败");
                            }else{
                                Log.e(TAG,"服务器待更新的图片地址为："+MyStaticResource.VISITPREFIX+otherHalfUrl);
                                String value=bindViewWithId.get(R.id.myHeadIcon);
                                map.put(value.substring(0,value.indexOf('&')),MyStaticResource.VISITPREFIX+otherHalfUrl);
                                updateData(map,value.substring(0,value.indexOf('&')),(ImageView) findViewById(R.id.myHeadIcon), null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    private void updateData(final Map<String, String> map, final String key, final ImageView imageView, final TextView textView) {
        okManager.asynJsonObjectByRequest(url, map, new OkManager.Func1() {
            @Override
            public void onResponse(String result) {
                Log.e(TAG,result);
                UserDTO userDTO= JSON.parseObject(result,UserDTO.class);
                Log.e(TAG,"响应消息"+userDTO);
                Log.e(TAG,"响应标识为："+userDTO.getFlag());
                if(imageView!=null){
                    resultMapping(userDTO.getFlag(),map.get(key),imageView,null);
                }else if(textView!=null){
                    resultMapping(userDTO.getFlag(),map.get(key),null,textView);
                }
                map.remove(key);
                Log.e(TAG,"移除当前请求参数"+key+"，map当前大小为："+map.size());
            }
        });
    }

    /**
     * 返回结果映射
     * @param flag
     * @param info
     * @param imageView
     * @param textView
     */
    private void resultMapping(String flag, String info, ImageView imageView, TextView textView) {
        switch (flag){
            case "0":
                if(imageView!=null){
                    Log.e(TAG,"图片地址为："+info);
                    Glide.with(this).load(info).into(imageView);
                }else if(textView!=null){
                    Log.e(TAG,"更新内容为："+info);
                    textView.setText(info);
                }
                Toast.makeText(UserBaseInfoActivity.this,"修改信息成功",Toast.LENGTH_SHORT).show();
                break;
            case "1":
                Toast.makeText(UserBaseInfoActivity.this,"修改信息失败",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 图片上传接口掉用
     *
     * @param file
     * @param substring 图片类型“.jgp”,“.png”
     */
    private String uploadFile(File file, String substring) {
        Log.e(TAG, "图片上传接口掉用");
        String returnUrl = null;
        try {
            Log.e(TAG, "图片后缀是：" + substring);
            returnUrl = FastDFSClient.upload(file, substring);
            Log.e(TAG, "图片上传返回的URL是：" + returnUrl);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "图片上传出现异常");
        }
        return returnUrl;
    }

    /**
     * 把bitmap存储到SD卡并转换成File型Uri
     *
     * @param bm
     * @return
     */
    private Uri saveBitmap(Bitmap bm) {
        /**
         * 用时间戳方式命名拍照文件
         */
        String fileName;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        fileName = format.format(date) + ".jpg";

        String photoPath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM + File.separator + "Camera" + File.separator;
        File img = new File(photoPath, fileName);
        Log.e(TAG, img.toString());
        String url = MediaStore.Images.Media.insertImage(getContentResolver(), bm, null, null);
        Log.e(TAG, "待插入的图片的uri为" + url);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        UserBaseInfoActivity.this.sendBroadcast(intent);
        Log.e(TAG, "插入到相册");
        return Uri.parse(url);
    }

    /**
     * 调用系统自带的剪裁功能
     *
     * @param uri
     */
    private void startImageZoom(Uri uri) {
        Log.e(TAG, "调用系统自带的剪裁接口");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    /**
     * 把Content Uri 转换为File型的Uri
     *
     * @param uri
     * @return
     */
    private Uri convertUri(Uri uri) {
        InputStream is = null;
        try {
            /**
             * 从Uri中获取InputStream
             */
            is = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.clear();
        map=null;
        bindViewWithId.clear();
        bindViewWithId=null;
    }
}
