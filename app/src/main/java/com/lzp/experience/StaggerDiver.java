package com.lzp.experience;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Li Xiaopeng
 * @date 2018/11/29
 */
public class StaggerDiver extends RecyclerView.ItemDecoration {
    private int hSpace = 10;
    private int vSpace = 10;

    public StaggerDiver(int hSpace, int vSpace) {
        this.hSpace = hSpace;
        this.vSpace = vSpace;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) parent.getLayoutManager();
        int spanCount = layoutManager.getSpanCount();

        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = layoutParams.getSpanIndex();
        if (spanIndex % spanCount == 0) {
            outRect.right =2;
            outRect.left = 50;
            outRect.top = vSpace/2;
        }else{
            outRect.right = 50;
            outRect.left = 2;
            outRect.top = vSpace/2;
        }
    }
}
