package com.lzp.experience.recyclerView.layoutManger;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

/**
 * 自定义LayoutManager
 * 模拟实现竖直的layoutmanager
 * 这里采用二级缓存scrap和Recycle
 * 关于scrap和Recycle
 * scrap：这里缓存的View是接下来要使用的，里面绑定的数据不需要更改，可以直接使用。轻量级的缓存集合
 * Recycle：缓存的View为数据需要更新的,需要通过Adapter重新绑定，会走onBindViewHolder,onCreateViewHolder
 *
 * @author Li Xiaopeng
 * @date 2018/11/30
 */
public class TestLayoutManager extends RecyclerView.LayoutManager {

    int verticalScrollOffset = 0;//垂直方向的偏移量
    int totalHeight = 0;//总高度

    private SparseArray<Rect> allItemRect = new SparseArray<>();//用来保存全部item的Rect，计算回收时需要使用

    private Context context;

    public TestLayoutManager(Context context) {
        this.context = context;
    }

    /**
     * 单个item的layoutParams限制
     *
     * @return
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }


    /**
     * 对item进行布局
     *
     * @param recycler
     * @param state
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //super.onLayoutChildren(recycler, state);

        //跳过preLayout，preLayout主要用于支持动画
        //没有item直接跳过
        if (getItemCount() == 0||state.isPreLayout()) return;
        //先将所有的View保存到recycler缓存
        detachAndScrapAttachedViews(recycler);
        int offSet = 0;//每个item的偏移量
        totalHeight = 0;//总高度
        for (int i = 0; i < getItemCount(); i++) {
            View viewForPosition = recycler.getViewForPosition(i);
            addView(viewForPosition);
            measureChildWithMargins(viewForPosition, 0, 0);//不加这句计算宽高都是0
            int width = getDecoratedMeasuredWidth(viewForPosition);
            int height = getDecoratedMeasuredHeight(viewForPosition);
            totalHeight += height;
            Rect frame = allItemRect.get(i);
            if (frame == null) {
                frame = new Rect();
            }
            frame.set(0, offSet, width, offSet + height);
            allItemRect.put(i, frame);
            offSet += height;
        }
        totalHeight = Math.max(totalHeight, getVerticalSpace());//当空间的item不够一屏幕时，取总高度为一屏幕
        layoutItem(recycler, state);//填充View
    }

    /**
     * 获取可见的区域Rect
     *
     * @return
     */
    private Rect getVisibleArea() {
        Rect result = new Rect(getPaddingLeft(), verticalScrollOffset, getWidth() + getPaddingRight(), verticalScrollOffset + getVerticalSpace());
        return result;
    }

    /**
     * 布局item
     * @param recycler
     * @param state
     */
    private void layoutItem(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //跳过preLayout，preLayout主要用于支持动画
        //没有item直接跳过
        if (getItemCount() == 0||state.isPreLayout()) return;

        // 当前显示的区域
        Rect displayFrame = getVisibleArea();

        /**
         * 将滑出屏幕的Items回收到Recycle缓存中
         */
        for (int i = 0; i < getChildCount(); i++) {
            Rect rect = allItemRect.get(i);
            //如果Item没有在显示区域，就说明需要回收
            if (!Rect.intersects(displayFrame, rect)) {
                //回收掉滑出屏幕的View
                View child = getChildAt(i);
                removeAndRecycleView(child, recycler);

            } else if (Rect.intersects(displayFrame, rect)) {
                //重新显示需要出现在屏幕的子View
                View scrap = recycler.getViewForPosition(i);
                measureChildWithMargins(scrap, 0, 0);
                addView(scrap);

                Rect frame = allItemRect.get(i);
                //将这个item布局出来
                layoutDecorated(scrap,
                        frame.left,
                        frame.top - verticalScrollOffset,
                        frame.right,
                        frame.bottom - verticalScrollOffset);

            }
        }
    }


    /**
     * 滚动到指定位置：如果需要smoothScroll，则必须重写
     *
     * @param recyclerView
     * @param state
     * @param position
     */
    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        super.smoothScrollToPosition(recyclerView, state, position);
    }

    /**
     * 滚动到指定位置
     *
     * @param position
     */
    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
    }

    /**
     * 滚动状态改变
     *
     * @param state
     */
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
    }

    /**
     * 移除全部的View
     */
    @Override
    public void removeAllViews() {
        super.removeAllViews();
    }

    /**
     * 移除全部的View
     * @param recycler
     */
    @Override
    public void removeAndRecycleAllViews(@NonNull RecyclerView.Recycler recycler) {
        super.removeAndRecycleAllViews(recycler);
    }

    /**
     * 移除某个View
     * 回收和复用:当View不在屏幕中的任何位置时，remove掉，以备后续循环使用
     *
     * @param child
     * @param recycler
     */
    @Override
    public void removeAndRecycleView(@NonNull View child, @NonNull RecyclerView.Recycler recycler) {
        super.removeAndRecycleView(child, recycler);
    }


    /**
     * 保存到Scrap，并不显示View
     * 回收和复用:如果只是更改排序，不需要重新bind数据，则建议直接detach
     *
     * @param child
     * @param recycler
     */
    @Override
    public void detachAndScrapView(@NonNull View child, @NonNull RecyclerView.Recycler recycler) {
        super.detachAndScrapView(child, recycler);
    }

    /**
     * 回收和复用某个位置View
     *
     * @param index
     * @param recycler
     */
    @Override
    public void removeAndRecycleViewAt(int index, @NonNull RecyclerView.Recycler recycler) {
        super.removeAndRecycleViewAt(index, recycler);
    }

    /**
     * 处理水平滚动
     * @param dx
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        // offsetChildrenHorizontal(-dx);
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    /**
     * 处理竖直滚动
     * @param dy
     * @param recycler
     * @param state
     * @return
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        //先detach掉所有的子View
        detachAndScrapAttachedViews(recycler);

        //实际要滑动的距离
        int travel = dy;

        //如果滑动到最顶部
        if (verticalScrollOffset + dy < 0) {
            travel = -verticalScrollOffset;
        } else if (verticalScrollOffset + dy > totalHeight - getVerticalSpace()) {//如果滑动到最底部
            travel = totalHeight - getVerticalSpace() - verticalScrollOffset;
        }
        //将竖直方向的偏移量+travel
        verticalScrollOffset += travel;

        // 平移容器内的item
        offsetChildrenVertical(-travel);

        layoutItem(recycler, state);
        Log.d("--->", " childView count:" + getChildCount());
        return travel;
    }

    /**
     * 获取控件垂直方向可显示的空间高度
     * @return
     */
    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }

    @Override
    public void offsetChildrenVertical(int dy) {
        super.offsetChildrenVertical(dy);
    }

    @Override
    public void offsetChildrenHorizontal(int dx) {
        super.offsetChildrenHorizontal(dx);
    }
}
