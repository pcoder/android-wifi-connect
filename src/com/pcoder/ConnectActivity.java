package com.pcoder;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConnectActivity extends Activity {

    private Button btnLogin, btnSave;
    private EditText txtUsername, txtPassword;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSave = (Button) findViewById(R.id.btnSave);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(onClickListener);
        btnSave.setOnClickListener(onClickListener);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.btnSave:

                    break;
                case R.id.btnLogin:
                    try {
                        // Use simple HttpClient to post the username and variable to the desired service url
                        String url = "https://hotspotwifi.bouyguestelecom.fr/bspHome.do?action=doBspHomeVoucherSubmit&flowId=TemplateLogin";
                        HttpPost request = new HttpPost(url);
                        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/20100101 Firefox/12.0");
                        request.setHeader("Content-Type", "application/x-www-form-urlencoded");
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                        nameValuePairs.add(new BasicNameValuePair("CPURL", "http://www.google.com/"));
                        nameValuePairs.add(new BasicNameValuePair("accept", "Connexion"));
                        nameValuePairs
                                .add(new BasicNameValuePair("cgu", "on"));
                        nameValuePairs.add(new BasicNameValuePair("password", txtPassword.getText().toString().trim()));
                        nameValuePairs.add(new BasicNameValuePair("username", txtUsername.getText().toString().trim()));
                        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        //request.setEntity(new StringEntity(postBody, "utf-8"));

                        HttpClient client = new DefaultHttpClient();
                        HttpResponse response = client.execute(request);
                        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                        String line = "";
                        StringBuffer sb = new StringBuffer();
                        while ((line = rd.readLine()) != null) {
                            sb.append(line);
                        }
                        if(isNetworkAvailable())
                            Toast.makeText(getApplicationContext(), "C O N N E C T E D ... \n" + sb.toString(), 15000).show();
                        else
                            Toast.makeText(getApplicationContext(), sb.toString(), 15000).show();


                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    break;
            }
        }
    };

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
