package com.iamnbty.movingtoolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class NotifyScrollView extends ScrollView {

    private Callback callback;

    public NotifyScrollView(Context context) {
        super(context);
    }

    public NotifyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotifyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected void onScrollChanged(int left, int top, int oldLeft, int oldTop) {
        super.onScrollChanged(left, top, oldLeft, oldTop);

        if (callback != null)
            callback.onScrollChanged(left, top, oldLeft, oldTop);
    }

    public interface Callback {

        public void onScrollChanged(int left, int top, int oldLeft, int oldTop);

    }

}
