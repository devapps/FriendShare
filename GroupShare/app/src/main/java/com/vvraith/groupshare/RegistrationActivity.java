package com.vvraith.groupshare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;
import android.widget.Toast;

import com.vvraith.groupshare.utils.MyAppActivity;
import com.vvraith.groupshare.utils.RequestExecutor;
import com.vvraith.groupshare.utils.Sharable;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements MyAppActivity {
    private static final String TAG = "RegistrationActivity";
    ProgressDialog progressDialog = null;
    private static final int REQUEST_SIGNUP = 0;
    String requestUrl = "/createuser?";

    @Bind(R.id.input_appusername)
    EditText _appusernameText;
    @Bind(R.id.input_virtualid)
    EditText _virtualidText;
    @Bind(R.id.input_firstname)
    EditText _firstnameText;
    @Bind(R.id.input_lastname)
    EditText _lastnameText;
    @Bind(R.id.input_apppin)
    EditText _apppinText;
    @Bind(R.id.input_confirmapppin)
    EditText _confirmapppinText;
    @Bind(R.id.fab_register)
    FloatingActionButton _registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(RegistrationActivity.this,
                R.style.AppThemeLightDialog);

        _registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    public void createUser() {
        Log.d(TAG, "Registration");

        if (!validate()) {
            onFailure();
            return;
        }

        _registerButton.setEnabled(false);


        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        String mobileNo = Sharable.getMobileNo();
        String appusername = _appusernameText.getText().toString();
        String virtualId = _virtualidText.getText().toString();
        String firstName = _firstnameText.getText().toString();
        String lastName = _lastnameText.getText().toString();
        String appPin = _apppinText.getText().toString();
        //new RequestExecutor();

        // TODO: Implement your own authentication logic here.
        String data = null;
        Map<String, String> inputMap = new HashMap<String, String>();
        inputMap.put("mobileno", mobileNo);
        inputMap.put("virtualid", virtualId);
        inputMap.put("firstname", firstName);
        inputMap.put("lastname", lastName);
        inputMap.put("appusername", appusername);
        inputMap.put("apppin", appPin);
        //new signin().execute();

        new RequestExecutor(inputMap,requestUrl,this,"REGISTRATION").execute();
    }
    @Override
    public void onStep1Success() {

    }
    @Override
    public void onStep2Success() {

    }
    @Override
    public void onSuccess() {
        _registerButton.setEnabled(true);

        progressDialog.dismiss();
        setResult(RESULT_OK);
        this.finish();
    }

    @Override
    public void onFailure() {

        progressDialog.dismiss();
        Toast.makeText(getBaseContext(), "Registration failed", Toast.LENGTH_LONG).show();

        _registerButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String appusername = _appusernameText.getText().toString();
        String virtualid = _virtualidText.getText().toString();
        String firstname = _firstnameText.getText().toString();
        String lastname = _lastnameText.getText().toString();
        String apppin = _apppinText.getText().toString();
        String confirmapppin = _confirmapppinText.getText().toString();


        if (appusername.isEmpty()) {
            _appusernameText.setError("enter a valid username");
            valid = false;
        } else {
            _appusernameText.setError(null);
        }

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

        if (apppin.isEmpty() || apppin.length() < 4 || apppin.length() > 10) {
            _apppinText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _apppinText.setError(null);
        }

        if (confirmapppin.isEmpty() || !confirmapppin.equals(apppin)) {
            _confirmapppinText.setError("does not match password");
            valid = false;
        } else {
            _confirmapppinText.setError(null);
        }

        return valid;
    }
}
