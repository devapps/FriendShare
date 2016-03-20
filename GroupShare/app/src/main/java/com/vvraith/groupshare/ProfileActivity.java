package com.vvraith.groupshare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.vvraith.groupshare.R;
import com.vvraith.groupshare.utils.MyAppActivity;
import com.vvraith.groupshare.utils.RequestExecutor;
import com.vvraith.groupshare.utils.Sharable;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity implements MyAppActivity {
    private static final String TAG = "ProfileActivity";
    ProgressDialog progressDialog = null;
    private static final int REQUEST_SIGNUP = 0;
    String requestUrl = "/updateuser?";

    @Bind(R.id.input_virtualid)
    EditText _virtualidText;
    @Bind(R.id.input_firstname)
    EditText _firstnameText;
    @Bind(R.id.input_lastname)
    EditText _lastnameText;
    @Bind(R.id.fab_profile)
    FloatingActionButton _profileButton;

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
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(ProfileActivity.this,
                R.style.AppThemeLightDialog);
        mActivityTitle = getTitle().toString();
        _virtualidText.setText(Sharable.getVirtualId());
        _firstnameText.setText(Sharable.getFirstName());
        _lastnameText.setText(Sharable.getLastName());

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        _profileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
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
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    public void updateUser() {
        Log.d(TAG, "Update Registration");

        if (!validate()) {
            onFailure();
            return;
        }

        _profileButton.setEnabled(false);


        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Updating User Details...");
        progressDialog.show();

        //String mobileNo = "7045354928";
        String appusername ="ajayynotna";
        String virtualId = _virtualidText.getText().toString();
        String firstName = _firstnameText.getText().toString();
        String lastName = _lastnameText.getText().toString();
        //new RequestExecutor();

        // TODO: Implement your own authentication logic here.
        String data = null;
        Map<String, String> inputMap = new HashMap<String, String>();
        inputMap.put("appusername", appusername);
        inputMap.put("virtualid", virtualId);
        inputMap.put("firstname", firstName);
        inputMap.put("lastname", lastName);
        //new signin().execute();

        new RequestExecutor(inputMap,requestUrl,this,"PROFILE").execute();
    }
    @Override
    public void onStep1Success() {

    }
    @Override
    public void onStep2Success() {

    }
    @Override
    public void onSuccess() {
        _profileButton.setEnabled(true);

        progressDialog.dismiss();
        this.finish();
    }

    @Override
    public void onFailure() {

        progressDialog.dismiss();
        Toast.makeText(getBaseContext(), "Updating Profile failed", Toast.LENGTH_LONG).show();

        _profileButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String virtualid = _virtualidText.getText().toString();
        String firstname = _firstnameText.getText().toString();
        String lastname = _lastnameText.getText().toString();

        if (virtualid.isEmpty() || virtualid.length() < 6) {
            _virtualidText.setError("at least 6 alphanumeric characters");
            valid = false;
        } else {
            _virtualidText.setError(null);
        }

        if (firstname.isEmpty()) {
            _firstnameText.setError("cannot be empty");
            valid = false;
        } else {
            _firstnameText.setError(null);
        }

        if (lastname.isEmpty()) {
            _lastnameText.setError("cannot be empty");
            valid = false;
        } else {
            _lastnameText.setError(null);
        }

        return valid;
    }
}
