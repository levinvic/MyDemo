package com.example.administrator.mydemo.Utils_Tools;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.administrator.mydemo.BuildConfig;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


// 取得檔案路徑
public class FileSaveUtil {
    private static final String TAG = FileSaveUtil.class.getName();

    private String storagePath = null;
    private String defaultFolder = "smxxth";
    private Context context = null;
    private String packageFilesDirectory = null;

    private File externalStorageDirectory;


    public FileSaveUtil() {

    }

    public FileSaveUtil(Context context) {
        this.context = context;
    }

    public static FileSaveUtil with() {
        return new FileSaveUtil();
    }

    // 如需建立內部資料夾需傳入Context
    public static FileSaveUtil with(Context context) {
        return new FileSaveUtil(context);
    }

    // kai
    public String getPath() {
        externalStorageDirectory = Environment.getExternalStorageDirectory();
        return getPath(null);
    }

    // kai
    public String getPath(Context context) {
        if (storagePath == null) {
            storagePath = externalStorageDirectory.getAbsolutePath() + "/" + Environment.DIRECTORY_DCIM + "/" + "SmeetH";
            File file = new File(storagePath);
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    storagePath = getPathInPackage(context, true);
                }
            }
        }
        return storagePath;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getPath(final Context context, final Uri uri) {
//        if (storagePath == null) {
//            DebugClass.getInstance().setDebugLog("進getPath null");
//            // /storage/emulated/0
//            DebugClass.getInstance().setDebugLog("絕對路徑", Environment.getExternalStorageDirectory().getAbsolutePath());
//            storagePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
//                    "/" + Environment.DIRECTORY_DCIM + "/" + defaultFolder;
//            DebugClass.getInstance().setDebugLog("storagePath", storagePath);
//            File file = new File(storagePath);
//            if (!file.exists()) {
//                if (!file.mkdirs()) {
//                    storagePath = getPathInPackage(context, true);
//                }
//            }
//        }
//        return storagePath;

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                //  handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {

                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    // 取得路徑，並加入詢問權限
    @SuppressLint({"SetWorldWritable", "SetWorldReadable"})
    public String getPathInPackage(Context context, boolean grantPermissions) {
        if (context == null || packageFilesDirectory != null) {
            return packageFilesDirectory;
        }

        // 手機不存在 SDCard, 需要使用 data/data/name.of.package/files 目錄
        String path = context.getFilesDir() + "/" + defaultFolder;
        File file = new File(path);
        if (!file.exists()) {
            if (BuildConfig.DEBUG) Log.d(TAG, "建立資料夾失敗");
            return null;
        }

        if (grantPermissions) {
            // 設置隱藏目錄權限
            if (file.setExecutable(true, false)) {
                if (BuildConfig.DEBUG) Log.d(TAG, "Package folder is executable");
            }

            if (file.setReadable(true, false)) {
                if (BuildConfig.DEBUG) Log.d(TAG, "Package folder is readable");
            }

            if (file.setWritable(true, false)) {
                if (BuildConfig.DEBUG) Log.d(TAG, "Package folder is writable");
            }
        }
        packageFilesDirectory = path;
        return packageFilesDirectory;
    }

    // 自訂預設資料夾名稱
    public FileSaveUtil setDefaultFolder(String defaultFolder) {
        this.defaultFolder = defaultFolder;
        return this;
    }

    // kai
    public String saveBitmap(Bitmap bmp) {
        String path = getPath();
        String filename = path + "/" + "smxxth" + ".jpg";
        return saveBitmap(bmp, filename);
    }

    // kai
    public String saveBitmap(Bitmap bmp, String filename) {
        try {
            FileOutputStream fileout = new FileOutputStream(filename);
            BufferedOutputStream bufferOutStream = new BufferedOutputStream(fileout);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bufferOutStream);
            bufferOutStream.flush();
            bufferOutStream.close();
        } catch (IOException e) {
            if (BuildConfig.DEBUG) Log.d(TAG, "Err when saving bitmap...");
            e.printStackTrace();
            return null;
        }
        if (BuildConfig.DEBUG) Log.d(TAG, "Bitmap " + filename + " saved!");
        return filename;
    }

}
