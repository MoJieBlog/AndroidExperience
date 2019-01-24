package com.lzp.experience.recyclerView.itemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lxp.utils.LogUtils;
import com.lzp.base.utils.UIUtils;

import java.util.List;

/**
 * @author Li Xiaopeng
 * @date 2019/1/24
 */
public class StickerTitleItemDecoration<T extends StickerModelHelper> extends RecyclerView.ItemDecoration {

    private static final String TAG = "StickerTitleItemDecorat";
    private List<T> list;
    private Context context;

    private Paint titlePaint;//title
    private Paint bgPaint;//背景
    private Paint diverPaint;//分隔线

    private int titleHeight;

    private int diverHeight = 3;
    private int titleLeft;

    public StickerTitleItemDecoration(Context context) {
        this.context = context;

        init();
    }

    private void init() {
        bgPaint = new Paint();
        bgPaint.setColor(Color.GREEN);

        titlePaint = new Paint();
        titlePaint.setAntiAlias(true);
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(UIUtils.dpToPx(context, 15));

        diverPaint = new Paint();
        diverPaint.setColor(Color.parseColor("#e60012"));

        titleHeight = UIUtils.dpToPx(context, 50);
        titleLeft = UIUtils.dpToPx(context, 15);
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null) {
            throw new IllegalArgumentException("layoutManager must not be null.");
        }
        int position = layoutManager.getPosition(view);
        if (position == 0) {
            //第一个必须显示title
            outRect.set(0, titleHeight, 0, diverHeight);
        } else if (list.get(position).getCompareContent().equals(list.get(position - 1).getCompareContent())) {
            //title一样的
            outRect.set(0, 0, 0, diverHeight);
        } else {
            //title不一样
            outRect.set(0, titleHeight, 0, diverHeight);
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager == null) {
                throw new IllegalArgumentException("layoutManager must not be null.");
            }
            //画普通分割线
            c.drawRect(0, child.getBottom(), parent.getRight(), child.getBottom() + diverHeight, diverPaint);
            int position = layoutManager.getPosition(child);
            if (position == 0 || !list.get(position).getCompareContent().equals(list.get(position - 1).getCompareContent())) {
                //画title背景

                c.drawRect(0, child.getTop() - titleHeight, parent.getRight(), child.getTop(), bgPaint);
                //画title内容
                String title = list.get(position).getCompareContent();
                Rect rect = new Rect();
                titlePaint.getTextBounds(title, 0, 1, rect);
                c.drawText(title, titleLeft, child.getTop() - (titleHeight / 2 - rect.height() / 2), titlePaint);
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null) {
            throw new IllegalArgumentException("layoutManager must not be null.");
        }
        if (layoutManager instanceof LinearLayoutManager) {
            int position = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            //
            View firstItemDecorationView = layoutManager.findViewByPosition(position);
            View secondItemDecorationView = layoutManager.findViewByPosition(position + 1);
            if (secondItemDecorationView.getTop() - firstItemDecorationView.getTop() > firstItemDecorationView.getHeight() * 2) {
                c.translate(0,firstItemDecorationView.getTop());
            }

            c.drawRect(0,0,parent.getRight(),titleHeight,bgPaint);

            String title = list.get(position).getCompareContent();
            Rect rect = new Rect();
            titlePaint.getTextBounds(title, 0, 1, rect);
            c.drawText(title, titleLeft, (titleHeight / 2 + rect.height() / 2), titlePaint);
        }
    }
}
