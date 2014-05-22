package com.example.CalcWithFragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.CalcWithFragments.fragments.About;
import com.example.CalcWithFragments.fragments.Calculator;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.Toast;

public class MyActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] fragmentTitles;
    private FrameLayout frame;
    private float lastTranslate = 0.0f;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mTitle = getTitle();
        mDrawerTitle = getResources().getString(R.string.menu);
        fragmentTitles = getResources().getStringArray(R.array.fragments);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        frame = (FrameLayout) findViewById(R.id.content_frame);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, fragmentTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListner());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer_light, R.string.drawer_open, R.string.drawer_close) {

            @SuppressLint("NewApi")
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float moveFactor = (mDrawerList.getWidth() * slideOffset);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    frame.setTranslationX(moveFactor);
                } else {
                    TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setDuration(0);
                    anim.setFillAfter(true);
                    frame.startAnimation(anim);

                    lastTranslate = moveFactor;
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        try {
            menu.findItem(R.id.bin).setVisible(!drawerOpen);
            menu.findItem(R.id.oct).setVisible(!drawerOpen);
            menu.findItem(R.id.dec).setVisible(!drawerOpen);
            menu.findItem(R.id.hex).setVisible(!drawerOpen);
        } catch (NullPointerException ex) {
            Log.i("findFragmentById", "MenuNullPointerException");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListner implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment fragment = new Calculator();
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0:
                getSupportActionBar().setSubtitle(getResources().getStringArray(R.array.fragments)[0]);
                fragment = new Calculator();
                break;
            case 1:
                getSupportActionBar().setSubtitle(getResources().getStringArray(R.array.fragments)[1]);
                fragment = new About();
                break;
            case 2:
                getSupportActionBar().setSubtitle(getResources().getStringArray(R.array.fragments)[2]);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                finish();
                                System.exit(0);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                Toast toast = new Toast(getApplicationContext());
                                ImageView view = new ImageView(getApplicationContext());
                                view.setImageResource(R.drawable.pyetrosyan);
                                toast.setView(view);
                                toast.show();
                                break;
                        }
                    }
                };
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        mDrawerList.setItemChecked(position, true);
        setTitle(fragmentTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
