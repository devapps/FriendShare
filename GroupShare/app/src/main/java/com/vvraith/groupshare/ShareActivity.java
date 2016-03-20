package com.vvraith.groupshare;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShareActivity extends AppCompatActivity {

    @Bind(R.id.navList)
    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    ActionBarDrawerToggle mDrawerToggle;
    String mActivityTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void addDrawerItems() {
        String[] menuArray = { "Profile", "Change Password", "Notifications", "Friends", "Pay" ,"Collect","Add Share","HouseShare","TripShare","Logout"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivityForResult(intent, 0);
                        break;
                    case 1:
                        intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                        startActivityForResult(intent, 1);
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(), NotificationsActivity.class);
                        startActivityForResult(intent, 2);
                        break;
                    case 3:
                        intent = new Intent(getApplicationContext(), FriendsActivity.class);
                        startActivityForResult(intent, 3);
                        break;
                    case 4:
                        intent = new Intent(getApplicationContext(), PayActivity.class);
                        startActivityForResult(intent, 4);
                        break;
                    case 5:
                        intent = new Intent(getApplicationContext(), CollectActivity.class);
                        startActivityForResult(intent, 5);
                        break;
                    case 6:
                        intent = new Intent(getApplicationContext(), ShareActivity.class);
                        startActivityForResult(intent, 6);
                        break;
                    case 7:
                        intent = new Intent(getApplicationContext(), ShareActivity.class);
                        startActivityForResult(intent, 7);
                        break;
                    case 8:
                        intent = new Intent(getApplicationContext(), ShareActivity.class);
                        startActivityForResult(intent, 8);
                        break;
                    case 9:
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivityForResult(intent, 9);
                        break;
                }
                mDrawerLayout.closeDrawers();
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
