package com.iamnbty.movingtoolbar;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.iamnbty.movingtoolbar.databinding.ActivityMainBinding;
import com.iamnbty.movingtoolbar.helper.MovingToolbarHelper;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MovingToolbarHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set content view
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // set support action bar
        binding.toolbar.setNavigationIcon(new DrawerArrowDrawable(this));
        setSupportActionBar(binding.toolbar);

        // set moving toolbar
        helper = new MovingToolbarHelper();
        helper.bind(
                binding.nestedScrollView,
                binding.imageView,
                binding.imageContainer,
                binding.toolbarContainer,
                binding.contentContainer);
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

}
