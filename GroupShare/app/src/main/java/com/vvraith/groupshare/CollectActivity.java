package com.vvraith.groupshare;

import android.app.Activity;
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

import com.vvraith.groupshare.utils.MyAppActivity;
import com.vvraith.groupshare.utils.RequestExecutor;
import com.vvraith.groupshare.utils.Sharable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.npci.upi.security.pinactivitycomponent.PinActivityComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CollectActivity extends AppCompatActivity implements MyAppActivity {
    public static String TransactionId_upi;
    private String xmlPayloadString="";
    private String credAllowedString = "";
    private final String keyCode="NPCI";

    private static final String TAG = "CollectActivity";
    ProgressDialog progressDialog = null;
    private static final int REQUEST_SIGNUP = 0;
    String requestUrl = "/api/collectmoney/virtualid?";

    @Bind(R.id.input_fromname)
    EditText _fromnameText;
    @Bind(R.id.input_fromvirtualid)
    EditText _fromvirtualidText;
    @Bind(R.id.input_amount)
    EditText _amountText;
    @Bind(R.id.input_remarks)
    EditText _remarksText;
    @Bind(R.id.fab_collect)
    FloatingActionButton _collectButton;

    @Bind(R.id.navList)
    ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    String mActivityTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        app_init();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        TransactionId_upi = String.valueOf(new Random().nextInt(99999999));
        progressDialog = new ProgressDialog(CollectActivity.this,
                R.style.AppThemeDarkDialog);

        mActivityTitle = getTitle().toString();
        addDrawerItems();
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        _collectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                collectAmountUPI();
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

    public void app_init(){

        // Creating API Responses

        /* This will be obtained from the response of ListKeys API */
        xmlPayloadString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns2:RespListKeys xmlns:ns2=\"http://npci.org/upi/schema/\"><Head msgId=\"1GRDBknXpxwlntt6r3My\" orgId=\"NPCI\" ts=\"2016-03-11T00:07:51+05:30\" ver=\"1.0\"/><Resp reqMsgId=\"INFRAPSP2J7JFXYYER\" result=\"SUCCESS\"/><Txn id=\"INFRAPSP83W66W9K1U\" refId=\"testing\" refUrl=\"https://mahb.com\" ts=\"2016-03-11T00:00:02+05:30\" type=\"ListKeys\"/><keyList><key code=\"NPCI\" ki=\"20150822\" owner=\"NPCI\" type=\"PKI\"><keyValue xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4rIIEHkJ2TYgO/JUJQI/sxDgbDEAIuy9uTf4DItWeIMsG9AuilOj9R+dwAv8S6/9No/z0cwsw4UnsHQG1ALVIxFznLizMjaVJ7TJ+yTS9C9bYEFakRqH8b4jje7SC7rZ9/DtZGsaWaCaDTyuZ9dMHrgcmJjeklRKxl4YVmQJpzYLrK4zOpyY+lNPBqs+aiwJa53ZogcUGBhx/nIXfDDvVOtKzNb/08U7dZuXoiY0/McQ7xEiFcEtMpEJw5EB4o3RhE9j/IQOvc7l/BfD85+YQ5rJGk4HUb6GrQXHzfHvIOf53l1Yb0IX4v9q7HiAyOdggO+PVzXMSbrcFBrEjGZD7QIDAQAB</keyValue></key></keyList><Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/><SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/><Reference URI=\"\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><DigestValue>x3+xFPIiKmFX4c+3Pid2IckJhxoBHWh49lxcVEqeFI0=</DigestValue></Reference></SignedInfo><SignatureValue>UAKQFFAYw26+F7YfPd+1uOG3zFO+OWa9nJs3evvR1HkrN8ga9DPc5Xe5Isq55YQojSrksTTqX9FEetEBd8dlR5D5uEt726o3HGsGiBdF6dGpJzziFRCjc9YoruFEA7V36StC3vMSJRZqt8CnpRPssn9hlNFIbc1NeZkWekUCM/jrihBsYSm8h52uy6BLWb7evsqb5BdAwwLwPhRy066BnM92skYjdXgunwr7NDpu8kIcQkK232Xas9Y1nYndp6J0Hma+8jdCo8BTv9gH95ZwtI8euToq7f8xILniHnQdt+8IkKEpGOC0qcOQrDUyB3wdKxt8ogCy72jRkfzQMIE7jw==</SignatureValue><KeyInfo><KeyValue><RSAKeyValue><Modulus>01DqzBsJTyMHT2S9MK5AIyFXNU646kwiOK3uymXIy9EW0nRKNKRkeIRTlGwX4wEnymGtGgX5B/Ij1elkLN4VJ9GplDV+wf0Lp2i2q4E6uRiWIzsqq42MCQgv8Fq/IPqjqPbeP9yh/8YPmBiMehBmhQd3qzl77C03k6d0yBIO5q/zXneTK9uFBNEL5yNpukrLGBcf3b9VHsjXpEaQrxGSMHCgNWpQgXpEcBr5OJ0/XxWbgMCZMlkYe1d6gswjuCRZ/xxJwEfbSO5AsnPtyqxSIjyhgEi9REtYnzaWwOBN4JCqt0pML0ja23lUwVJuNwkwNGKBXvkGoXUln8Sf7PIv7w==</Modulus><Exponent>AQAB</Exponent></RSAKeyValue></KeyValue></KeyInfo></Signature></ns2:RespListKeys>";
       /* Log.i("xml_payload",xmlPayloadString);*/
        // xmlPayloadString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns2:RespListKeys xmlns:ns2=\"http://npci.org/upi/schema/\"><Head msgId=\"1GRDBknspOwrBRjZpXT3\" orgId=\"NPCI\" ts=\"2016-02-18T15:51:40+05:30\" ver=\"1.0\"/><Resp reqMsgId=\"MAHB14VWPQY4OI\" result=\"SUCCESS\"/><Txn id=\"MAHB2V5GR9EHHF\" note=\"payment\" refId=\"testing\" refUrl=\"https://mahb.com\" ts=\"2016-02-18T16:01:51+05:30\" type=\"ListKeys\"/><keyList><key code=\"NPCI\" ki=\"20150822\" owner=\"NPCI\" type=\"PKI\"><keyValue xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4rIIEHkJ2TYgO/JUJQI/sxDgbDEAIuy9uTf4DItWeIMsG9AuilOj9R+dwAv8S6/9No/z0cwsw4UnsHQG1ALVIxFznLizMjaVJ7TJ+yTS9C9bYEFakRqH8b4jje7SC7rZ9/DtZGsaWaCaDTyuZ9dMHrgcmJjeklRKxl4YVmQJpzYLrK4zOpyY+lNPBqs+aiwJa53ZogcUGBhx/nIXfDDvVOtKzNb/08U7dZuXoiY0/McQ7xEiFcEtMpEJw5EB4o3RhE9j/IQOvc7l/BfD85+YQ5rJGk4HUb6GrQXHzfHvIOf53l1Yb0IX4v9q7HiAyOdggO+PVzXMSbrcFBrEjGZD7QIDAQAB</keyValue></key></keyList><Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\"><SignedInfo><CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/><SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/><Reference URI=\"\"><Transforms><Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/></Transforms><DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><DigestValue>4wahq5ABCdy9IeqHiq6wTWIf+hqlha+VP3ieAYDk+eM=</DigestValue></Reference></SignedInfo><SignatureValue>tVgv11X9c3G+kofnn912X6PHps6hDM13BABtLQQAG76tu+nOhKnoHGAxInalEKRFif2ep01aDLEZyxl8liydmg0mSjAAb4B/EezoxgJbX3aq+8za/dbS0rqubejZrjwZu3ioMrpDwFCXIcftsSjCz+QIpdf8a9jmavzBaEOLj/Tpol0JUQzASiPxnLtZp6YzF2DLF5G2cCaDtUvLOkE5yixXxLRy5C/FmKBLdhwxcqc0V0xbr/epdweSP9YL2CVXKfyhJgCdPS0rHusc7MQF8o4KlzSGga+421AWSWkQ9epizV1GESdhutgmybfFaFZ3vnl6I3nuvt9vTWwm00FJnA==</SignatureValue><KeyInfo><KeyValue><RSAKeyValue><Modulus>01DqzBsJTyMHT2S9MK5AIyFXNU646kwiOK3uymXIy9EW0nRKNKRkeIRTlGwX4wEnymGtGgX5B/Ij1elkLN4VJ9GplDV+wf0Lp2i2q4E6uRiWIzsqq42MCQgv8Fq/IPqjqPbeP9yh/8YPmBiMehBmhQd3qzl77C03k6d0yBIO5q/zXneTK9uFBNEL5yNpukrLGBcf3b9VHsjXpEaQrxGSMHCgNWpQgXpEcBr5OJ0/XxWbgMCZMlkYe1d6gswjuCRZ/xxJwEfbSO5AsnPtyqxSIjyhgEi9REtYnzaWwOBN4JCqt0pML0ja23lUwVJuNwkwNGKBXvkGoXUln8Sf7PIv7w==</Modulus><Exponent>AQAB</Exponent></RSAKeyValue></KeyValue></KeyInfo></Signature></ns2:RespListKeys>";

        /* This will be obtained from the response of ListAccountResponse API */
       /* credAllowedString = "{\n" +
                "\t\"CredAllowed\": [{\n" +
                "\t\t\"type\": \"TPIN\",\n" +
                "\t\t\"subtype\": \"TPIN\",\n" +
                "\t\t\"dType\": \"ALPH | NUM\",\n" +
                "\t\t\"dLength\": 6\n" +
                "\t}]\n" +
                "}";*/

        credAllowedString = "{\n" +
                "\t\"CredAllowed\": [{\n" +
                "\t\t\"type\": \"PIN\",\n" +
                "\t\t\"subtype\": \"MPIN\",\n" +
                "\t\t\"dType\": \"ALPH | NUM\",\n" +
                "\t\t\"dLength\": 6\n" +
                //"\t}, {\n" +
                //"\t\t\"type\": \"OTP\",\n" +
                //"\t\t\"subtype\": \"OTP\",\n" +
                //"\t\t\"dType\": \"NUM\",\n" +
                //"\t\t\"dLength\": 6\n" +
                "\t}]\n" +
                "}";


    }
    @Override
    public void onStep1Success() {

    }
    @Override
    public void onStep2Success() {

    }

    @Override
    public void onSuccess() {
        _collectButton.setEnabled(true);

        progressDialog.dismiss();
        this.finish();
    }


    @Override
    public void onFailure() {

        progressDialog.dismiss();
        Toast.makeText(getBaseContext(), "Collect Transaction failed", Toast.LENGTH_LONG).show();

        _collectButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String fromname = _fromnameText.getText().toString();
        String fromvirtualid = _fromvirtualidText.getText().toString();
        String amount = _amountText.getText().toString();
        String remarks = _remarksText.getText().toString();
        if (fromname.isEmpty()) {
            _fromvirtualidText.setError("cannot be empty");
            valid = false;
        } else {
            _fromvirtualidText.setError(null);
        }
        if (fromvirtualid.isEmpty() || fromvirtualid.length() < 6) {
            _fromvirtualidText.setError("at least 6 alphanumeric characters");
            valid = false;
        } else {
            _fromvirtualidText.setError(null);
        }

        if (amount.isEmpty()) {
            _amountText.setError("cannot be empty");
            valid = false;
        } else {
            _amountText.setError(null);
        }

        if (remarks.isEmpty()) {
            _remarksText.setError("cannot be empty");
            valid = false;
        } else {
            _remarksText.setError(null);
        }

        return valid;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_collect, menu);
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

    void collectAmountUPI()
    {
        Log.d(TAG, "collectAmount");

        if (!validate()) {
            onFailure();
            return;
        }

        _collectButton.setEnabled(false);


        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Raising Collect Request...");
        progressDialog.show();

        Intent intent = new Intent(this, PinActivityComponent.class);
        // Create Keycode
        intent.putExtra("keyCode", keyCode);

        // Create xml payload
        if(xmlPayloadString.isEmpty()){
            Toast.makeText(this, "XML List Key API is not loaded.", Toast.LENGTH_LONG).show();
            return;
        }
        intent.putExtra("keyXmlPayload", xmlPayloadString);  // It will get the data from list keys API response

        // Create Controls
        if(credAllowedString.isEmpty()){
            Toast.makeText(this,"Required Credentials could not be loaded.",Toast.LENGTH_LONG).show();
            return;
        }
        intent.putExtra("controls", credAllowedString);

        // Create Configuration
        try {
            JSONObject configuration = new JSONObject();
            configuration.put("payerBankName", "Bank Of maharashtra");
            configuration.put("backgroundColor","#FFFFFF");
            Log.i("configuration", configuration.toString());
            intent.putExtra("configuration", configuration.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create Salt
        try {
            JSONObject salt = new JSONObject();
            salt.put("txnId", TransactionId_upi);
            Log.i("txnId: ",TransactionId_upi );
            salt.put("txnAmount", "100");
            salt.put("deviceId", "74235ae00124fab8");
            salt.put("appId", this.getApplicationContext().getPackageName());
            Log.i("salt", salt.toString());
            intent.putExtra("salt", salt.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create Pay Info
        JSONArray payInfoArray=new JSONArray();
        try {
            JSONObject jsonPayeeName = new JSONObject();
            jsonPayeeName.put("name", "payeeName");
            jsonPayeeName.put("value","something"+ "@infrapsp");
            payInfoArray.put(jsonPayeeName);

            JSONObject jsonNote = new JSONObject();
            jsonNote.put("name", "note");
            jsonNote.put("value", "Add Bank Account");
            payInfoArray.put(jsonNote);

            JSONObject jsonRefId = new JSONObject();
            jsonRefId.put("name", "refId");
            jsonRefId.put("value", TransactionId_upi);
            payInfoArray.put(jsonRefId);

            JSONObject jsonRefUrl = new JSONObject();
            jsonRefUrl.put("name", "refUrl");
            jsonRefUrl.put("value", "http://www.bankofmaharashtra.in/");
            payInfoArray.put(jsonRefUrl);

                    /*JSONObject jsonAccount = new JSONObject();
                    jsonAccount.put("name", "account");
                    jsonAccount.put("value", "122XXX423");
                    payInfoArray.put(jsonAccount);*/

            Log.i("payInfo", payInfoArray.toString());
            intent.putExtra("payInfo", payInfoArray.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Create Language Pref
        intent.putExtra("languagePref", "en_US");

        startActivityForResult(intent, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("Result Code:", String.valueOf(resultCode));
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            String errorMsgStr = data.getStringExtra("error");
            if (errorMsgStr != null && !errorMsgStr.isEmpty()) {
                Log.d("Error:", errorMsgStr);
                try {
                    JSONObject error = new JSONObject(errorMsgStr);
                    String errorCode = error.getString("errorCode");
                    String errorText = error.getString("errorText");
                    Toast.makeText(this.getApplicationContext(), errorCode + ":" + errorText, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            }
            HashMap<String, String> credListHashMap = (HashMap<String, String>) data.getSerializableExtra("credBlocks");
            for (String cred : credListHashMap.keySet()) { // This will return the list of field name e.g mpin,otp etc...
                try {
                    JSONObject credBlock = new JSONObject(credListHashMap.get(cred));
                    Log.i("enc_msg", credBlock.toString());
                    // adapter.add("Control name : "+cred);
                    // adapter.add("keyId : " + credBlock.getJSONObject("data").getString("code"));
                    // adapter.add("key index : " + credBlock.getJSONObject("data").getString("ki"));
                    // adapter.add("Encrypted Message from Common Library: " + credBlock.getJSONObject("data").getString("encryptedBase64String"));


                    System.out.println("Control name : " + cred);
                    System.out.println("keyId : " + credBlock.getJSONObject("data").getString("code"));
                    System.out.println("key index : " + credBlock.getJSONObject("data").getString("ki"));
                    System.out.println("Encrypted Message from Common Library: : " + credBlock.getJSONObject("data").getString("encryptedBase64String"));

                    /*appPref.getInstance().setString("cred_upi_insta", cred);
                    appPref.getInstance().setString("keyCode_upi_insta", credBlock.getJSONObject("data").getString("code"));
                    appPref.getInstance().setString("keyindex_upi_insta", credBlock.getJSONObject("data").getString("ki"));
                    appPref.getInstance().setString("credData_insta", credBlock.getJSONObject("data").getString("encryptedBase64String"));
*/
                    // new sendmoneyData().execute();


                    Log.i("enc_msg", credBlock.getJSONObject("data").getString("encryptedBase64String"));
                    //Log.i("enc_msg", credBlock.getString("message"));

                    String fromname = _fromnameText.getText().toString();
                    String fromvirtualid = _fromvirtualidText.getText().toString();
                    String amount = _amountText.getText().toString();
                    String remarks = _remarksText.getText().toString();
                    //new RequestExecutor();

                    // TODO: Implement your own authentication logic here.
                    Map<String, String> inputMap = new HashMap<String, String>();
                    inputMap.put("access_token", Sharable.getToken());
                    inputMap.put("transactionid", TransactionId_upi);
                    inputMap.put("appusername", Sharable.getAppUserName());
                    inputMap.put("virtualid", fromvirtualid);
                    inputMap.put("amount", amount);
                    inputMap.put("myname",fromname);
                    inputMap.put("remarks", remarks);
                    new RequestExecutor(inputMap,requestUrl,this,"Collect").execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
