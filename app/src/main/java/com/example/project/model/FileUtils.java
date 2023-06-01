package com.example.project.model;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;

public class FileUtils {

    /**
     * Gets the actual file path from the URI
     */
    public static String getPath(Context context, Uri uri) {
        String filePath;
        // Check for SDK version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
            // DocumentProvider
            String docId = DocumentsContract.getDocumentId(uri);
            if (isExternalStorageDocument(uri)) {
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    filePath = Environment.getExternalStorageDirectory() + "/" + split[1];
                } else {
                    String fullPath = "/storage/" + type + "/" + split[1];
                    File file = new File(fullPath);
                    if (file.exists()) {
                        filePath = fullPath;
                    } else {
                        filePath = null;
                    }
                }
            } else if (isDownloadsDocument(uri)) {
                try {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    filePath = getColumnData(context, contentUri, null, null);
                } catch (NumberFormatException e) {
                    filePath = null;
                }
            } else if (isMediaDocument(uri)) {
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = "_id=?";
                String[] selectionArgs = new String[]{split[1]};
                filePath = getColumnData(context, contentUri, selection, selectionArgs);
            } else {
                filePath = null;
            }
        } else {
            // MediaStore (and general)
            String[] projection = {MediaStore.Audio.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            filePath = getColumnData(context, uri, null, null);
            if (cursor != null) {
                cursor.close();
            }
        }
        return filePath;
    }

    private static String getColumnData(Context context, Uri uri, String selection, String[] selectionArgs) {
        String filePath;
        String[] projection = {MediaStore.Audio.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        } else {
            filePath = null;
        }
        return filePath;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
