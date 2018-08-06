package com.lzp.recycylerview.refresh;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;

import com.lzp.recycylerview.R;
import com.lzp.recycylerview.refresh.base.BaseLoadLayout;
import com.lzp.recycylerview.refresh.base.BaseRefreshLayout;
import com.lzp.recycylerview.refresh.base.LoadingLayout;
import com.lzp.recycylerview.refresh.extra.DefaultRefreshView;

/**
 * 原生的SwipeRefreshLayout改写
 * Created by Li Xiaopeng on 18/5/21.
 */

/**
 * The SwipeRefreshLayout should be used whenever the user can refresh the
 * contents of a view via a vertical swipe gesture. The activity that
 * instantiates this view should add an OnRefreshListener to be notified
 * whenever the swipe to refresh gesture is completed. The SwipeRefreshLayout
 * will notify the listener each and every time the gesture is completed again;
 * the listener is responsible for correctly determining when to actually
 * initiate a refresh of its content. If the listener determines there should
 * not be a refresh, it must call setRefreshing(false) to cancel any visual
 * indication of a refresh. If an activity wishes to show just the progress
 * animation, it should call setRefreshing(true). To disable the gesture and
 * progress animation, call setEnabled(false) on the view.
 * <p>
 * This layout should be made the parent of the view that will be refreshed as a
 * result of the gesture and can only support one direct child. This view will
 * also be made the target of the gesture and will be forced to match both the
 * width and the height supplied in this layout. The SwipeRefreshLayout does not
 * provide accessibility events; instead, a menu item must be provided to allow
 * refresh of the content wherever this gesture is used.
 * </p>
 */

public class MSwipeRefreshLayout extends ViewGroup implements NestedScrollingParent,
        NestedScrollingChild {

    private static final String TAG = "MSwipeRefreshLayout";

    private static final float DECELERATE_INTERPOLATION_FACTOR = 2f;//这个值越到，前期快的时间越久
    public static final int LOADING_TYPE_DEFAULT = 0;
    public static final int REFRESH_TYPE_DEFAULT = 0;
    private static final int INVALID_POINTER = -1;
    private static final float DRAG_RATE = .5f;//

    //在可滑动的控件中用于区别单击子控件和滑动操作的一个伐值。
    private int mTouchSlop;
    //先快后慢的动画插值器
    private final DecelerateInterpolator mDecelerateInterpolator;
    private float mTotalUnconsumed;//总消耗的长度

    //父布局和子空间的嵌套滚动处理helper
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private final NestedScrollingChildHelper mNestedScrollingChildHelper;

    private int refreshType = REFRESH_TYPE_DEFAULT;

    private BaseRefreshLayout reshreshView;

    private IRefreshListener mRefreshListener;

    private float moveOffset = 0;//偏移量

    private boolean freshing = false;

    private final int[] mParentScrollConsumed = new int[2];
    private final int[] mParentOffsetInWindow = new int[2];

    /**
     * the target of the gesture(官方解释) 个人理解为刷新的控件（listView，recyclerView...）
     */
    private View mTarget;

    protected boolean canRefresh;//是否可刷新，可加载更多
    protected boolean showRefresh;
    private boolean mNestedScrollInProgress;//
    private boolean mIsBeingDragged;//是否正在拖拽

    //当前按下的位置，上一次按下的位置
    private float mInitialMotionY, mLastMotionY;
    /**
     * 是否可以拖动
     */
    private boolean canDragged = false;

    private int mActivePointerId = INVALID_POINTER;//点击的Id

    public MSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public MSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //可滑动控件中用于区别单击子空间和滑动操作的一个伐值
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        //viewgroup出于性能考虑，默认会被设置成WILL_NOT_DROW，是不允许重写onDraw方法的，这个方法就是让其可以重写onDraw
        setWillNotDraw(false);
        //创建一个先快后慢的动画插值器
        mDecelerateInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MSwipeRefreshLayout);
        setEnabled(typedArray.getBoolean(R.styleable.MSwipeRefreshLayout_enabled, true));
        refreshType = typedArray.getInt(R.styleable.MSwipeRefreshLayout_refresh_type, REFRESH_TYPE_DEFAULT);
        typedArray.recycle();

        //创建刷新的View,拓展主要在这个方法里执行
        createRefreshView();

        //嵌套滚动的helper
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        //允许嵌套滚动
        setNestedScrollingEnabled(true);
    }

    private void createRefreshView() {
        if (refreshType == REFRESH_TYPE_DEFAULT){
            reshreshView = new DefaultRefreshView(getContext());
        }else{
            reshreshView = new DefaultRefreshView(getContext());
        }
        reshreshView.setRefreshLayoutInstance(this);
        addView(reshreshView);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reset();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            reset();
        }
    }

    /**
     * 清空所有动画等
     */
    private void reset() {

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        if (getChildCount()==0){
            return;
        }
        if (mTarget==null){
            ensureTarget();
        }
        if (mTarget==null){
            return;
        }

        View child = mTarget;
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        int childWidth = measuredWidth - getPaddingLeft() - getPaddingRight();
        int childHeight = measuredHeight - getPaddingTop() - getPaddingBottom();
        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);

        int headLoadViewMeasuredWidth = reshreshView.getMeasuredWidth();
        int headLoadViewMeasuredHeight = reshreshView.getMeasuredHeight();
        //水平居中显示
        reshreshView.layout(
                (measuredWidth / 2 - headLoadViewMeasuredWidth / 2),
                -headLoadViewMeasuredHeight,
                (measuredWidth / 2 + headLoadViewMeasuredWidth / 2),
                0);

        reshreshView.setTargetViewHeight(child.getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) return;
        mTarget.measure(MeasureSpec.makeMeasureSpec(
                getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(),
                        MeasureSpec.EXACTLY));
        reshreshView.measure(
                MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(reshreshView.getAnimateViewHeight(), MeasureSpec.EXACTLY));
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     *         scroll up. Override this if the child view is a custom view.
     */
    public boolean canChildScrollUp() {
        //第一个参数指定View,第二个指定的方向，小于0表示向上，大于0表示向下.下面是官方对第二个参数的注释
        //Negative to check scrolling up, positive to check scrolling down.
        return ViewCompat.canScrollVertically(mTarget, -1);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();
        /**
         * Android用一个32位的整型值表示一次touchEvent，低8位表示事件的具体动作，比如按下，抬起，滑动，还有多点触控
         * getAction ：触摸动作的原始32位信息，包括事件的动作，触控点信息
         * getActionMask：触摸动作（按下，抬起，滑动，多点按下，多点抬起）
         * getActionIndex：触控点的信息
         */
        int action = MotionEventCompat.getActionMasked(ev);
        if (canChildScrollUp() || !canRefresh || mNestedScrollInProgress) {
            //如果可以继续向上滑动，或者不可以刷新，或者可以嵌套滚动，不拦截
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                mIsBeingDragged = false;
                final float initialDownY = getMotionEventY(ev, mActivePointerId);
                if (initialDownY == -1) {
                    return false;
                }
                canDragged = true;
                if (isFreshing()) {
                    moveOffset = -getScrollY();
                } else {
                    moveOffset = 0;
                }
                mInitialMotionY = mLastMotionY = initialDownY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) {
                    Log.e(TAG, "onInterceptTouchEvent: Got ACTION_MOVE event but don't have an active pointer id.");
                    return false;
                }
                float y = getMotionEventY(ev, mActivePointerId);
                if (y == -1) {
                    return false;
                }
                float yDiff = y - mInitialMotionY;
                if (yDiff > mTouchSlop && !mIsBeingDragged) {//有偏移量
                    mInitialMotionY = mInitialMotionY + mTouchSlop;
                    mIsBeingDragged = true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                break;
        }
        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int pointerIndex = -1;
        if (!isEnabled() || canChildScrollUp()) {
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = event.getPointerId(0);
                mIsBeingDragged = false;
                canDragged = true;
                break;
            case MotionEvent.ACTION_MOVE:
                pointerIndex = event.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    Log.e(TAG, "onTouchEvent: Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                if (!canDragged) {
                    return false;
                }

                float y = event.getY(pointerIndex);
                moveOffset += ((y - mLastMotionY) * DRAG_RATE);
                mLastMotionY = y;
                if (mIsBeingDragged) {
                    return moveHeader(moveOffset);
                }
                break;
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                pointerIndex = MotionEventCompat.getActionIndex(event);
                if (pointerIndex < 0) {
                    Log.e(TAG, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
                mActivePointerId = MotionEventCompat.getPointerId(event, pointerIndex);

                mLastMotionY = MotionEventCompat.getY(event, pointerIndex);

                Log.e(TAG, "onTouchEvent ACTION_POINTER_DOWN " +
                        " pointerIndex = " + pointerIndex);
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                pointerIndex = event.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    Log.e(TAG, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
                mIsBeingDragged = false;
                canDragged = true;
                moveOffset = 0;
                finishMove();
                mActivePointerId = INVALID_POINTER;
                break;
        }
        return super.onTouchEvent(event);
    }

    private void finishMove() {
        if (-getScrollY() >= reshreshView.getMeasuredHeight()
                && mRefreshListener != null
                && !freshing) {
            freshing = true;
            mRefreshListener.onRefresh();
            reshreshView.onRefreshing();
        }

        if (showRefresh){
            resetHeader(freshing);
            showRefresh = freshing;
        }
    }

    private boolean moveHeader(float moveOffset) {
        scrollTo(0, moveOffset >= 0 ? (int) -moveOffset : 0);
        reshreshView.onMoving(moveOffset, freshing);
        if (mIheaderScroll != null) {
            mIheaderScroll.onHearderScroll(getScrollY());
        }
        return moveOffset >= 0;
    }

    private ValueAnimator resetHeaderAnimator;
    private void resetHeader(final boolean freshing) {
        if (resetHeaderAnimator != null && resetHeaderAnimator.isRunning()) {
            resetHeaderAnimator.cancel();
        }
        resetHeaderAnimator = ValueAnimator.ofInt(getScrollY(),
                freshing ? -reshreshView.getMeasuredHeight() : 0);
        resetHeaderAnimator.setDuration(400);
        resetHeaderAnimator.setInterpolator(mDecelerateInterpolator);
        resetHeaderAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scrollTo(0, (Integer) animation.getAnimatedValue());
                if (mIheaderScroll != null) {
                    mIheaderScroll.onHearderScroll(getScrollY());
                }
            }
        });

        resetHeaderAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!freshing) {
                    canDragged = false;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        resetHeaderAnimator.start();
    }

    private void forecResetHeader() {

        ValueAnimator animator =
                ValueAnimator.ofInt(getScrollY(), -reshreshView.getMeasuredHeight());
        animator.setDuration(400);
        animator.setInterpolator(mDecelerateInterpolator);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {

                reshreshView.onMoving(Math.abs((Integer) animation.getAnimatedValue()), freshing);

                scrollTo(0, (Integer) animation.getAnimatedValue());

                if (mIheaderScroll != null) {
                    mIheaderScroll.onHearderScroll(getScrollY());
                }
            }
        });
        animator.start();
    }

    /**
     * 获取event的Y方向的值
     *
     * @param ev
     * @param mActivePointerId
     * @return
     */
    private float getMotionEventY(MotionEvent ev, int mActivePointerId) {
        int pointerIndex = ev.findPointerIndex(mActivePointerId);
        if (pointerIndex < 0) {
            return -1;
        }
        return ev.getY();
    }

    /**
     * 确定要刷新的View
     */
    private void ensureTarget() {
        // Don't bother getting the parent height if the parent hasn't been laid
        // out yet.
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                //不是刷新的布局，所以确定为要实现刷新的控件
                if (!child.equals(reshreshView)) {
                    mTarget = child;
                    break;
                }
            }
        }
    }

    public void stopRefresh() {
        if (freshing) {
            freshing = false;
            reshreshView.onRefreshFinish();
            resetHeader(freshing);
        }
    }

    public void setCanRefresh(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }

    public void setRefresh(boolean isReFresh) {
        if (isReFresh) {
            freshing = true;
            forecResetHeader();
            if (mRefreshListener!=null){
                mRefreshListener.onRefresh();
            }
            reshreshView.onRefreshing();
        } else {
            stopRefresh();
        }

    }

    public boolean isFreshing() {
        return freshing;
    }

    public void setFreshing(boolean freshing) {
        this.freshing = freshing;
    }
    /**
     * 设置刷新
     *
     * @param listener
     */
    public void setOnRefreshListener(IRefreshListener listener) {
        mRefreshListener = listener;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean b) {
        // if this is a List < L or another view that doesn't support nested
        // scrolling, ignore this request so that the vertical scroll event
        // isn't stolen
        if ((android.os.Build.VERSION.SDK_INT < 21 && mTarget instanceof AbsListView)
                || (mTarget != null && !ViewCompat.isNestedScrollingEnabled(mTarget))) {
            // Nope.
        } else {
            super.requestDisallowInterceptTouchEvent(b);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    //---------- ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ NestedScrollingParent ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ ---------------
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return ((canRefresh && !freshing) )//如果可以刷新并且正在刷新
                && (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;//并且嵌套滚动值不是0，则开始嵌套滚动
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
        startNestedScroll(axes & ViewCompat.SCROLL_AXIS_VERTICAL);
        mTotalUnconsumed = 0;//总共没有被消耗的值设置为0
        mNestedScrollInProgress = true;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // If we are in the middle of consuming, a scroll, then we want to move the spinner back up
        // before allowing the list to scroll
        if (dy > 0 && mTotalUnconsumed > 0 && !canChildScrollUp()) {//向上滚动,当滚动到一半距离，慢慢放手缩回去是
            Log.e(TAG, "onNestedPreScroll: 开始滚动头部");
            if (dy > mTotalUnconsumed) {
                consumed[1] = dy - (int) mTotalUnconsumed;
                mTotalUnconsumed = 0;
            } else {
                mTotalUnconsumed -= dy;
                consumed[1] = dy;
            }
            moveHeader(mTotalUnconsumed * DRAG_RATE);
        }
        final int[] parentConsumed = mParentScrollConsumed;
        if (dispatchNestedPreScroll(dx - consumed[0], dy - consumed[1], parentConsumed, null)) {
            consumed[0] += parentConsumed[0];
            consumed[1] += parentConsumed[1];
        }
    }

    @Override
    public int getNestedScrollAxes() {
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    @Override
    public void onStopNestedScroll(View target) {
        mNestedScrollingParentHelper.onStopNestedScroll(target);
        mNestedScrollInProgress = false;
        // Finish the spinner for nested scrolling if we ever consumed any
        // unconsumed nested scroll
        if (mTotalUnconsumed > 0) {
            finishMove();
            mTotalUnconsumed = 0;
        }
        // Dispatch up our nested parent
        stopNestedScroll();
    }

    @Override
    public void onNestedScroll(final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed) {
        // Dispatch up to the nested parent first
        dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
                mParentOffsetInWindow);

        // This is a bit of a hack. Nested scrolling works from the bottom up, and as we are
        // sometimes between two nested scrolling views, we need a way to be able to know when any
        // nested scrolling parent has stopped handling events. We do that by using the
        // 'offset in window 'functionality to see if we have been moved from the event.
        // This is a decent indication of whether we should take over the event stream or not.
        final int dy = dyUnconsumed + mParentOffsetInWindow[1];
        if (dy < 0 && !canChildScrollUp()) {
            showRefresh = true;
            Log.e(TAG, "onNestedScroll: 到顶部了");
            mTotalUnconsumed += Math.abs(dy);
            moveHeader(mTotalUnconsumed * DRAG_RATE);
        }
    }

    //---------- ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ NestedScrollingParent ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ ---------------
    //---------- ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ NestedScrollingChild ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ ---------------
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mNestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mNestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mNestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY,
                                 boolean consumed) {
        return dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }
    //---------- ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ NestedScrollingChild ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ ---------------

    private IHeaderScroll mIheaderScroll = null;

    public void setIheaderScroll(IHeaderScroll headerScroll) {
        this.mIheaderScroll = headerScroll;
    }

    public interface IHeaderScroll {
        /**
         * swiperefreshLayout的ScrollY
         *
         * @param scrollY
         */
        void onHearderScroll(float scrollY);
    }
}
