package com.lzp.experience.viewpager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzp.base.utils.UIUtils;

/**
 * @author Li Xiaopeng
 * @date 18/10/15
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private int[] mImages;

    public ViewPagerAdapter(Context context, int[] images) {
        this.mContext = context;
        this.mImages = images;
    }

    @Override
    public int getCount() {
        return mImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setBackgroundColor(Color.parseColor("#e4e4e4"));
        imageView.setLayoutParams(new ViewGroup.LayoutParams(UIUtils.dpToPx(mContext,200),
                UIUtils.dpToPx(mContext,200)));
        imageView.setImageResource(mImages[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}