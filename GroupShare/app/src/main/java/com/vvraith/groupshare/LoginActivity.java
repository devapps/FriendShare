package com.vvraith.groupshare;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import android.app.ProgressDialog;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vvraith.groupshare.utils.RequestExecutor;
import com.vvraith.groupshare.utils.MyAppActivity;
import com.vvraith.groupshare.utils.Sharable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Bind;


public class LoginActivity extends AppCompatActivity implements MyAppActivity{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int LOGIN = 1;
    ProgressDialog progressDialog = null;
    String requestUrl = "/oauth/token?";

    @Bind(R.id.input_username) EditText _usernameText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppThemeDarkDialog);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SendOTPActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN) {
            if (resultCode == RESULT_OK) {

            }
        }
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onFailure();
            return;
        }

        _loginButton.setEnabled(false);


        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();
        //new RequestExecutor();

        // TODO: Implement your own authentication logic here.
        String data = null;
        Map<String, String> inputMap = new HashMap<String, String>();
        inputMap.put("grant_type", "password");
        inputMap.put("client_id", "restapp");
        inputMap.put("client_secret", "restapp");
        inputMap.put("username", username);
        inputMap.put("password", password);
        //new signin().execute();
        Sharable.setAppUserName(username);
        new RequestExecutor(inputMap,requestUrl,this,"LOGIN").execute();
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    @Override
    public void onStep1Success() {
        Map<String, String> myInputMap = new HashMap<>();
        myInputMap.put("appusername", Sharable.getAppUserName());
        myInputMap.put("access_token", Sharable.getToken());
        new RequestExecutor(myInputMap, "/api/listnotifications?", this, "NOTIFICATIONS").execute();
    }
    @Override
    public void onStep2Success() {
    }
    @Override
    public void onSuccess() {
        _loginButton.setEnabled(true);

        progressDialog.dismiss();
        Intent intent = new Intent(getApplicationContext(), NotificationsActivity.class);
        startActivityForResult(intent, LOGIN);
    }

    @Override
    public void onFailure() {

        progressDialog.dismiss();
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            _usernameText.setError("enter a valid username");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
