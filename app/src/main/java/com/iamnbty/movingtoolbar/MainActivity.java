package com.iamnbty.movingtoolbar;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NestedScrollView.OnScrollChangeListener {

    private NestedScrollView mNestedScrollView;

    private ImageView mImageView;
    private View mImageContainer;

    private View mToolbarContainer;
    private LinearLayout mContentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set content view
        setContentView(R.layout.activity_main);

        // view matching
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        mImageView = (ImageView) findViewById(R.id.image_view);
        mImageContainer = findViewById(R.id.image_container);
        mContentLayout = (LinearLayout) findViewById(R.id.content_layout);
        mToolbarContainer = findViewById(R.id.toolbar_container);

        // setup toolbar
        setupToolbar();

        // setup nested scroll view
        setupNestedScrollView();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(new DrawerArrowDrawable(this));
        setSupportActionBar(toolbar);
    }

    private void setupNestedScrollView() {
        mNestedScrollView.setOnScrollChangeListener(this);

        ViewTreeObserver viewTreeObserver = mNestedScrollView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    // get size
                    int toolbarLinearLayoutHeight = mToolbarContainer.getHeight();
                    int imageHeight = mImageView.getHeight();

                    // adjust image frame layout height
                    ViewGroup.LayoutParams layoutParams = mImageContainer.getLayoutParams();
                    if (layoutParams.height != imageHeight) {
                        layoutParams.height = imageHeight;
                        mImageContainer.setLayoutParams(layoutParams);
                    }

                    // adjust top margin of content linear layout
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mContentLayout.getLayoutParams();
                    if (marginLayoutParams.topMargin != toolbarLinearLayoutHeight + imageHeight) {
                        marginLayoutParams.topMargin = toolbarLinearLayoutHeight + imageHeight;
                        mContentLayout.setLayoutParams(marginLayoutParams);
                    }

                    // call onScrollChange to update initial properties.
                    onScrollChange(mNestedScrollView, 0, 0, 0, 0);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        // choose appropriate y
        float newY = Math.max(mImageView.getHeight(), scrollY);

        // translate image and toolbar
        ViewCompat.setTranslationY(mToolbarContainer, newY);
        ViewCompat.setTranslationY(mImageContainer, scrollY * 0.5f);
    }

}
