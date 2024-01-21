package ru.nsu.fit.g20203.sinyukov.googlebooksapp;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.CaseMap;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import okhttp3.OkHttpClient;

public class VolumeImageLoader {

    private static final String TAG = "VolumeImageLoader";

    private static final int connectTimeout = 10;
    private static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(connectTimeout, TimeUnit.SECONDS)
            .build();

    public static void loadImage(ImageView imageView, Volume.VolumeInfo.ImageLinks imageLinks,
                                 Function<Volume.VolumeInfo.ImageLinks, String> imageUrlProducer,
                                 int size) {
        if (null != imageLinks) {
            final String imageUrl = imageUrlProducer.apply(imageLinks);
            if (null != imageUrl) {
                new Picasso.Builder(imageView.getContext())
                        .downloader(new OkHttp3Downloader(httpClient))
                        .build()
                        .load(imageUrl)
                        .resize(size,size)
                        .centerCrop()
                        .placeholder(resizeDrawable(imageView.getContext(), size / 2, R.drawable.placeholder))
                        .error(resizeDrawable(imageView.getContext(), size / 2, R.drawable.error))
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d(TAG, "Image successfully loaded: " + imageUrl);
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.w(TAG,"Error downloading image: " + imageUrl, e);
                            }
                        });
                return;
            }
        }
        Picasso.get()
                .load(R.drawable.placeholder)
                .resize(size, size)
                .centerCrop()
                .into(imageView);
    }

    private static Drawable resizeDrawable(Context context, int size, @DrawableRes Integer drawableRes) {
        final Drawable dr = getDrawable(context, drawableRes);
        final Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return new BitmapDrawable(context.getResources(),
                Bitmap.createScaledBitmap(bitmap, size, size, true));
    }
}
