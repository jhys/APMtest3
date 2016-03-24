package com.ca.sampleapps.apmtest3;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ca.integration.CaMDOIntegration;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView tv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            btn = (Button)findViewById(R.id.button);
            tv = (TextView)findViewById(R.id.textView);

            CaMDOIntegration.startApplicationTransaction("callRest", "AndroidApp");

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    tv.setText("start App Transaction is called");
                    CaMDOIntegration.startApplicationTransaction("callRest", "AndroidApp");

                    RESTCall rc = new RESTCall(tv);
                    rc.execute("http://192.168.124.101:8080/my-first-jersey-service/apis/hello");

                    for(long i=0; i<100000; i++){
                        ArrayList<String> al = new ArrayList<>();
                        al.add(String.valueOf(i) + "is processed");
                    }
                    CaMDOIntegration.stopApplicationTransaction("callRest", "AndroidApp");
                    tv.setText("stop App Transaction is called");

                }
            });
    }




}

