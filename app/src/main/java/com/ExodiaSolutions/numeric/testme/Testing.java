package com.ExodiaSolutions.numeric.testme;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Testing extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private TextView info;

    private boolean out_checker = false;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
   // ProgressBar pb;
    ProgressDialog pd;
    String name,email,photo;
GoogleApiClient mGoogleApiClient;
    private SharedPreferences mSettings;
    private SharedPreferences.Editor Editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(Testing.this);

        setContentView(R.layout.activity_testing);

        mSettings = this.getSharedPreferences("settings", 0);
        Editor = mSettings.edit();
        if (getIntent().getBooleanExtra("remember_not", false)) {
            Editor.putString("name", "");
            Editor.putString("photo", "");
            Editor.putString("email", "");
            Editor.putString("remember_me", "");
            Editor.apply();


            //Toast.makeText(login.this, "Log Out", Toast.LENGTH_SHORT).show();
        } else if (mSettings.getString("remember_me", "").equalsIgnoreCase("true")) {
            Intent intent = new Intent(Testing.this, Main2Activity.class);
            intent.putExtra("name", mSettings.getString("name",""));
            intent.putExtra("email", mSettings.getString("email",""));
            intent.putExtra("photo", mSettings.getString("photo",""));
            startActivity(intent);
            Testing.this.finish();
        }
        info = (TextView) findViewById(R.id.info);
   //     pb = (ProgressBar) findViewById(R.id.bar);
        pd= new ProgressDialog(this);
    //    pb.setVisibility(View.INVISIBLE);
        pd.setCancelable(false);
        pd.setMessage("Sign in");

//gogle sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton signInButton = (SignInButton) findViewById(R.id.g_sign_in_button);
        signInButton.setSize(SignInButton.SIZE_ICON_ONLY);
        signInButton.setScopes(gso.getScopeArray());
        findViewById(R.id.g_sign_in_button).setOnClickListener(this);
       // findViewById(R.id.sign_out_and_disconnect).setOnClickListener(this);


      //  out_checker = getIntent().getBooleanExtra("out_checker",false);

         //   googleSignOut();
          //  Toast.makeText(this, "ahgagl;ajgl", Toast.LENGTH_SHORT).show();


    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view) {

                googleSignIn();

           /* case R.id.sign_out_and_disconnect:
                googleSignOut();
                break;*/


    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    private void googleSignOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_CANCELED){
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount acct = result.getSignInAccount();
            //TODO: uncomment below section
            pd.show();
            BackgroundWorker bw = new BackgroundWorker(Testing.this,acct.getDisplayName(),acct.getEmail(),""+acct.getPhotoUrl());
            bw.execute();
            name=acct.getDisplayName();
            email=acct.getEmail();
            photo=""+acct.getPhotoUrl();
            //Toast.makeText(this, "Name: "+name+"\nEmail: "+email+"\nPhoto: "+photo, Toast.LENGTH_SHORT).show();


        }
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.

            GoogleSignInAccount acct = result.getSignInAccount();
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }

    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.g_sign_in_button).setVisibility(View.GONE);
           // findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);


        } else {
           // mStatusTextView.setText(R.string.signed_out);

         //   findViewById(R.id.g_sign_in_button).setVisibility(View.VISIBLE);
           // findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
            Toast.makeText(this, "sign out", Toast.LENGTH_SHORT).show();
        }
    }

 /*   @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }*/
 private boolean isNetworkAvailable() {
     ConnectivityManager connectivityManager
             = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
     NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
     return activeNetworkInfo != null && activeNetworkInfo.isConnected();
 }

    public class BackgroundWorker extends AsyncTask<String, Void, String> {

        Context context;
        String name,email,photo;
        AlertDialog alertdialog;

        BackgroundWorker(Context context,String name,String email,String photo) {
            this.name = name;
            this.email=email;
            this.photo=photo;
            this.context = context;
        }


        @Override
        protected String doInBackground(String... params) {


            String Login_url = "http://test.numericinfosystem.in/android/register.php";

                try {

                    URL url = new URL(Login_url);
                    HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection();
                    httpurlconnection.setRequestMethod("POST");
                    httpurlconnection.setDoOutput(true);
                    httpurlconnection.setDoInput(true);

                    OutputStream outputstream = httpurlconnection.getOutputStream();
                    BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));

                    String post_data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                            + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")+ "&"
                            + URLEncoder.encode("photo", "UTF-8") + "=" + URLEncoder.encode(photo, "UTF-8");
                    bufferedwriter.write(post_data);
                    bufferedwriter.flush();
                    bufferedwriter.close();

                    InputStream inputstream = httpurlconnection.getInputStream();
                    BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedreader.readLine()) != null) {
                        result += line;
                    }
                    bufferedreader.close();
                    inputstream.close();
                    httpurlconnection.disconnect();

                    return result;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return "null ghjgj";
        }

        @Override
        protected void onPreExecute() {
            //pb.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String result) {
           // Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
           // pb.setVisibility(View.INVISIBLE);
            pd.hide();
        if(result.equalsIgnoreCase("1")){
            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Testing.this, Main2Activity.class);
            intent.putExtra("name", name);
            intent.putExtra("email", email);
            intent.putExtra("photo", photo);
            Editor.putString("name", name);
            Editor.putString("email", email);
            Editor.putString("remember_me","true");
            Editor.putString("photo", photo);
            Editor.apply();
            startActivity(intent);
            Testing.this.finish();
        }
        else{
            Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show();
        }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

}