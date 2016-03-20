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

public class SendOTPActivity extends AppCompatActivity implements MyAppActivity {
    private static final String TAG = "SendOTPActivity";
    ProgressDialog progressDialog = null;
    private static final int REQUEST_SIGNUP = 0;
    String requestUrl = "/generateotp?";

    @Bind(R.id.input_mobileno)
    EditText _mobilenoText;
    @Bind(R.id.fab_sendotp)
    FloatingActionButton _sendOTPButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(SendOTPActivity.this,
                R.style.AppThemeLightDialog);

        _sendOTPButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendOTP();
            }
        });
    }

    public void sendOTP() {
        Log.d(TAG, "SendOTP");

        if (!validate()) {
            onFailure();
            return;
        }

        _sendOTPButton.setEnabled(false);


        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Sending OTP...");
        progressDialog.show();

        String mobileNo = _mobilenoText.getText().toString();
        //new RequestExecutor();

        // TODO: Implement your own authentication logic here.
        String data = null;
        Map<String, String> inputMap = new HashMap<String, String>();
        inputMap.put("mobileno", mobileNo);
        Sharable.setMobileNo(mobileNo);
        //new signin().execute();

        new RequestExecutor(inputMap,requestUrl,this,"SENDOTP").execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_ot, menu);
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
                setResult(RESULT_OK);
                this.finish();
            }
        }
    }

    @Override
    public void onStep1Success() {

    }
    @Override
    public void onStep2Success() {

    }

    @Override
    public void onSuccess() {
        _sendOTPButton.setEnabled(true);

        progressDialog.dismiss();
        Intent intent = new Intent(getApplicationContext(), VerifyOTPActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);

    }


    @Override
    public void onFailure() {

        progressDialog.dismiss();
        Toast.makeText(getBaseContext(), "Sending OTP failed", Toast.LENGTH_LONG).show();

        _sendOTPButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String mobileno = _mobilenoText.getText().toString();


        if (mobileno.isEmpty() || mobileno.length() != 10) {
            _mobilenoText.setError("enter valid 10 digit mobile number");
            valid = false;
        } else {
            _mobilenoText.setError(null);
        }

        return valid;
    }
}
