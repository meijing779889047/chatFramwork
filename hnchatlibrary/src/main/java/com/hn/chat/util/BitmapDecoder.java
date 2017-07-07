package com.hn.chat.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapDecoder {
    public static Bitmap decode(InputStream is) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        // RGB_565
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        /**
         * 在4.4上，如果之前is标记被移动过，会导致解码失败
         */
        try {
            if (is.markSupported()) {
                is.reset();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return BitmapFactory.decodeStream(is, null, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

        return null;
    }




    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static Bitmap checkInBitmap(Bitmap bitmap,
                                        BitmapFactory.Options options, String path) {
        boolean honeycomb = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
        if (honeycomb && bitmap != options.inBitmap && options.inBitmap != null) {
            options.inBitmap.recycle();
            options.inBitmap = null;
        }

        if (bitmap == null) {
            try {
                bitmap = BitmapFactory.decodeFile(path, options);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static int[] decodeBound(File file) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            int[] bound = decodeBound(is);
            return bound;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new int[]{0, 0};
    }

    public static int[] decodeBound(InputStream is) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);

        return new int[]{options.outWidth, options.outHeight};
    }






    public static Bitmap decodeSampled(Resources res, int resId, int sampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        // RGB_565
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        // sample size
        options.inSampleSize = sampleSize;

        try {
            return BitmapFactory.decodeResource(res, resId, options);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String compressBitmap(String oldPath, Context context) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        int sampleSize = 0;
        int widthMax = 1080;
        int heightMax =720;
        if (option.outWidth > option.outHeight) {
            if (option.outWidth <= widthMax) {
                return oldPath;
            }
            sampleSize = (int) ((option.outWidth * 1f / widthMax) + 0.5f);
        } else {
            if (option.outHeight <= heightMax) {
                return oldPath;
            }
            sampleSize = option.outHeight / heightMax;
        }

        option.inSampleSize = sampleSize;
        option.inPreferredConfig = Bitmap.Config.RGB_565;
        option.inJustDecodeBounds = false;
        Bitmap compressedBitmap = BitmapFactory.decodeFile(oldPath, option);
        return storeBitmap(context, compressedBitmap,oldPath);
    }

    public static String storeBitmap(Context context, Bitmap photo, String oldPath) {
        try {

            String path;
            if(TextUtils.isEmpty(oldPath)) {
                if (Environment.isExternalStorageEmulated()) {
                    path = context.getExternalCacheDir().getPath();
                } else {
                    path = context.getCacheDir().getPath();
                }
            }else{

                int index=oldPath.lastIndexOf("/");
                 path=oldPath.substring(0,index);
            }
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }

            File myCaptureFile = new File(path, System.currentTimeMillis() + ".jpg");
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            photo.compress(Bitmap.CompressFormat.JPEG, 60, bos);
            bos.flush();
            bos.close();
            if(new File(oldPath).exists()){
                FileUtil.deleteFile(oldPath);
            }
            return myCaptureFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
