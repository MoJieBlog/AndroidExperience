package com.lzp.recycylerview.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Created by Li Xiaopeng on 18/5/21.
 */

public class MRefreshRecyclerView extends SwipeRefreshLayout {

    private boolean canLoadmore = true;
    public MRefreshRecyclerView(Context context) {
        super(context);
    }

    public MRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public boolean canLoadMore(){
        return canLoadmore;
    }
}
