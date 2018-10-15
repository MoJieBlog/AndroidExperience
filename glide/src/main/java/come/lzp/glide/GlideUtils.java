package come.lzp.glide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;

import java.io.File;
import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * @author Li Xiaopeng
 * @date 18/9/28
 */
public class GlideUtils {

    public static final int GLIDE_NONE = -1;

    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 500; // 500 MB
    private static final int MEMORY_CACHE_PERCENT = 20 * 1024 * 1024; // 20 MB

    public static RequestOptions getOptions(int loadingRes,
                                            int errRes,
                                            int imgWidth,
                                            int imgHeight,
                                            boolean needCache,
                                            Object signeature) {
        RequestOptions options = new RequestOptions();
        if (loadingRes != GLIDE_NONE) {
            options.placeholder(loadingRes);
        }
        if (errRes != GLIDE_NONE) {
            options.error(errRes);
        }
        if (needCache) {
            options.skipMemoryCache(false);
            options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        } else {
            options.skipMemoryCache(true);
            options.diskCacheStrategy(DiskCacheStrategy.NONE);
        }

        if (imgHeight != GLIDE_NONE && imgWidth != GLIDE_NONE) {
            options.override(imgWidth, imgHeight);
        }

        if (signeature != null) {
            options.signature(new ObjectKey(signeature));
        }
        options.dontAnimate();

        return options;
    }

    public static void init(Context context) {
        Glide.init(getGlide(context));
    }

    public static void load(Context context, String imgUrl, ImageView imageView) {
        RequestOptions options = getOptions(GLIDE_NONE, GLIDE_NONE, GLIDE_NONE, GLIDE_NONE, true, null);
        Glide.with(context)
                .asBitmap()
                .load(imgUrl)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .apply(options)
                .into(imageView);
    }

    public static Glide getGlide(Context context) {

        GlideBuilder builder = new GlideBuilder();
        builder.setMemoryCache(new LruResourceCache(MEMORY_CACHE_PERCENT));
        //设置磁盘缓存
        InternalCacheDiskCacheFactory image_catch = new InternalCacheDiskCacheFactory(context, "glide_lib_cache", DISK_CACHE_SIZE);
        builder.setDiskCache(image_catch);//指定路径用
        builder.setBitmapPool(new LruBitmapPool(20 * 1024 * 1024));
        Glide glide = builder.build(context);

        Registry registry = glide.getRegistry();
        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(okHttpClient);
        registry.replace(GlideUrl.class, InputStream.class, factory);
        return glide;
    }

    /**
     * 删除内存缓存
     */
    public static void deleteMemory(Context context) {
        getGlide(context).clearMemory();
    }

    /**
     * 删除磁盘缓存文件夹的大小，需要在子线程中执行
     */
    @SuppressLint("StaticFieldLeak")
    public static void deleteDiskCache(final Context context, final String cacheDir) {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    File photoCacheDir = Glide.getPhotoCacheDir(context, cacheDir);
                    if (photoCacheDir.exists()) {
                        Log.e("6666", photoCacheDir.getAbsolutePath());
                        getGlide(context).clearDiskCache();
                    }
                    return null;
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算缓存文件夹的大小
     */
    public static long caculateCacheSize(Context context, String cacheDir) {
        long size = 0L;
        try {
            File photoCacheDir = Glide.getPhotoCacheDir(context, cacheDir);
            if (photoCacheDir.exists()) {
                size = dirSize(photoCacheDir);
            } else {
                size = 0L;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static long dirSize(@NonNull File dir) {
        if (dir.exists()) {
            long result = 0;
            File[] fileList = dir.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // Recursive call if it's a directory
                if (fileList[i].isDirectory()) {
                    result += dirSize(fileList[i]);
                } else {
                    // Sum the file size in bytes
                    result += fileList[i].length();
                }
            }
            return result; // return the file size
        }
        return 0;
    }

}
