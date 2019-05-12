package com.learning.tomato.until.picture;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: cwxiong
 * @e-mail: 1451780593@qq.com
 * @Company: CSUFT
 * @Description: TODO
 * @date 2018/6/2 21:11
 */

public class GetPath {
    private static final String TAG = "GetPath";
    public static GetPath getPath=null;
    private GetPath(){}
    public static GetPath getInstance(){
        if(getPath==null){
            synchronized (GetPath.class){
                if(getPath==null){
                    getPath=new GetPath();
                }
            }
        }
        return getPath;
    }

    @Nullable
    public String UriToPath(Uri uri,Context context) {
        Log.e(TAG, "getString: URI="+uri );
        String path=null;
        Bitmap bm=null;
        try {
            ContentResolver resolver=context.getContentResolver();
            Log.e(TAG, "getString: "+0 );
            bm= MediaStore.Images.Media.getBitmap(resolver,uri);
            Log.e(TAG, "getString: "+1 );
            //  imageView.setImageBitmap(ThumbnailUtils.extractThumbnail(bm,100,100));
            Log.e(TAG, "getString: "+2 );
            String[] proj={MediaStore.Images.Media.DATA};
            Log.e(TAG, "getString: "+3);
            Cursor cursor=null;
            if(Build.VERSION.SDK_INT<11){
                /**
                 * cursor 的managedQuery方法过时替换
                 */
//                cursor=managedQuery(uri,proj,null,null,null);
                Log.e(TAG, "getString: "+4.1 );
            }else{
                CursorLoader cursorLoader=new CursorLoader(context,uri,null,null,null,null);
                cursor=cursorLoader.loadInBackground();
                Log.e(TAG, "getString: "+4.2 );
            }
            int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            Log.e(TAG, "getString: "+5 );
            cursor.moveToFirst();
            path=cursor.getString(column_index);
            Log.e(TAG, "getString: "+6+path );
        } catch (Exception e) {
            e.printStackTrace();
            return path;
        }
//        datas.add(bm);
//        myGridViewAdapter2.notifyDataSetChanged();
        return path;
    }

    /**
     * 此方法用来更新到系统相册里面去
     * @param context
     * @param bm
     */
    public void updateToCamera(Context context, Bitmap bm) {
        File appDir=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"DCIM/Camera");
        if(!appDir.exists()){
            appDir.mkdirs();
        }
        String fileName=System.currentTimeMillis()+".jpg";
        File file=new File(appDir,fileName);
        try{
            try {
                FileOutputStream fos=new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.JPEG,100,fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }finally {
            ScannerByReceiver(context,file.getAbsolutePath());
            if(!bm.isRecycled()){
                System.gc();
            }
        }
    }

    /**
     * 广播通知系统扫描
     * @param context
     * @param absoluteFile
     */
    private void ScannerByReceiver(Context context, String absoluteFile) {
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.parse("file://"+absoluteFile)));
    }

    /**
     * 解决部分手机获取不到图片的问题
     * @param context
     * @param uri
     * @return
     */
    public String getPath(final Context context, final Uri uri){
        final boolean isKitKat=Build.VERSION.SDK_INT>=19;

        ContentResolver resolver=context.getContentResolver();
        Bitmap bm= null;
        try {
            bm = MediaStore.Images.Media.getBitmap(resolver,uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // imageView.setImageBitmap(ThumbnailUtils.extractThumbnail(bm,100,100));
//        datas.add(bm);
//
//        myGridViewAdapter2.notifyDataSetChanged();

        if(isKitKat && DocumentsContract.isDocumentUri(context,uri)){
            if(isExternalStorageDocument(uri)){
                final String docId=DocumentsContract.getDocumentId(uri);
                final String[] split=docId.split(":");
                final String type=split[0];

                if("primary".equalsIgnoreCase(type)){
                    return Environment.getExternalStorageDirectory()+"/"+split[1];
                }
            }else if(isDownloadsDocument(uri)){
                final String id=DocumentsContract.getDocumentId(uri);
                final Uri contentUri= ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),Long.valueOf(id));
                return getDataColumn(context,contentUri,null,null);
            }
            else if(isMediaDocument(uri)){
                final String docId=DocumentsContract.getDocumentId(uri);
                final String[] split=docId.split(":");
                final String type=split[0];

                Uri contentUri=null;
                if("image".equals(type)){
                    contentUri=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }else if("video".equals(type)){
                    contentUri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }else if("audio".equals(type)){
                    contentUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection="_id=?";
                final String[] selectionArgs=new String[]{split[1]};
                return getDataColumn(context,contentUri,selection,selectionArgs);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
//            if(isGooglePhotoUri(uri)){
//                return uri.getLastPathSegment();
//            }
            return getDataColumn(context,uri,null,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            return uri.getPath();
        }else{

        }
        return null;
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor=null;
        final String column="_data";
        final String[] projection={column};
        try{
            cursor=context.getContentResolver().query(uri,projection,selection,selectionArgs,null);
            if(cursor!=null&&cursor.moveToFirst()){
                final int index=cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return null;
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

}
