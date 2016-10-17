package com.iamnbty.movingtoolbar.helper;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class MovingToolbarHelper implements NestedScrollView.OnScrollChangeListener {

    private NestedScrollView nestedScrollView;

    private ImageView imageView;
    private ViewGroup imageContainer;

    private ViewGroup toolbarContainer;

    private ViewGroup contentContainer;

    public void bind(@NonNull NestedScrollView nestedScrollView, @NonNull ImageView imageView, @NonNull ViewGroup imageContainer, @NonNull ViewGroup toolbarContainer, @NonNull ViewGroup contentContainer) {
        this.nestedScrollView = nestedScrollView;
        this.imageView = imageView;
        this.imageContainer = imageContainer;
        this.toolbarContainer = toolbarContainer;
        this.contentContainer = contentContainer;

        setup();
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        // choose appropriate y
        float newY = Math.max(imageView.getHeight(), scrollY);

        // translate image and toolbar
        ViewCompat.setTranslationY(toolbarContainer, newY);
        ViewCompat.setTranslationY(imageContainer, scrollY * 0.5f);
    }

    private void setup() {
        nestedScrollView.setOnScrollChangeListener(this);

        ViewTreeObserver viewTreeObserver = nestedScrollView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    // get size
                    int toolbarLinearLayoutHeight = toolbarContainer.getHeight();
                    int imageHeight = imageView.getHeight();

                    // adjust image frame layout height
                    ViewGroup.LayoutParams layoutParams = imageContainer.getLayoutParams();
                    if (layoutParams.height != imageHeight) {
                        layoutParams.height = imageHeight;
                        imageContainer.setLayoutParams(layoutParams);
                    }

                    // adjust top margin of content linear layout
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) contentContainer.getLayoutParams();
                    if (marginLayoutParams.topMargin != toolbarLinearLayoutHeight + imageHeight) {
                        marginLayoutParams.topMargin = toolbarLinearLayoutHeight + imageHeight;
                        contentContainer.setLayoutParams(marginLayoutParams);
                    }

                    // call onScrollChange to update initial properties.
                    onScrollChange(nestedScrollView, 0, 0, 0, 0);
                }
            });
        }
    }

}
