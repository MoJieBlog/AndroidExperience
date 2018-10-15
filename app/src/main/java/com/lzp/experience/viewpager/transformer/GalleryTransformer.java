package com.lzp.experience.viewpager.transformer;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author Li Xiaopeng
 * @date 18/10/15
 */
public class GalleryTransformer implements ViewPager.PageTransformer {

    private Context context;

    private static final float ALPHA = 0.6f;
    private static final float SCALE = 0.8f;

    public GalleryTransformer(Context context) {
        this.context = context;
    }

    /**
     * 官方解释如下：
     *
     * @param page     Apply the transformation to this page
     * @param position Position of page relative to the current front-and-center
     *                 position of the pager. 0 is front and center. 1 is one full
     *                 page position to the right, and -1 is one page position to the left.
     *                 个人解释：
     *                 page就是页面
     *                 假如page = 0,则page有两个方法：左滑和右划
     *                 position，-1：左边的一页，0：当前，1：右边
     *                 （0，-1]:左滑到出现左边的一个
     *                 0：页面完全显示
     *                 （0，1]：右划到出现右面的一个
     */
    @Override
    public void transformPage(View page, float position) {
        if (position < -1 || position > 1) {//不跟当前显示page相邻，也不是当前page
            page.setScaleX(SCALE);
            page.setScaleY(SCALE);
            page.setAlpha(ALPHA);
        }else{
            //deltScroll的取值是0，1，因为约定了最小为SCALE，所以取这两个数的较大值
            float deltScroll = 1 - Math.abs(position);
            page.setAlpha(ALPHA+ALPHA*deltScroll);
            //缩放效果
            float scale=Math.max(SCALE,deltScroll);
            page.setScaleX(scale);
            page.setScaleY(scale);
        }

    }
}
