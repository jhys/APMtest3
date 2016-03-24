package com.ca.sampleapps.apmtest3;

import android.os.AsyncTask;
import android.widget.TextView;

import com.ca.integration.CaMDOIntegration;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by hiyju01 on 16/03/23.
 */


public class RESTCall extends AsyncTask<String, Void, String> {

    private TextView targetTextView;

    private String resultString;

    RESTCall(TextView tv){
        targetTextView = tv;
    }

    protected String doInBackground(String... args){
        resultString = null;

        HttpURLConnection httpConnection;

        try {
            // Get the url into a URL object
            URL url = new URL(args[0]);

            // Open the URL connection
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.connect();

            //CaMDOIntegration.startApplicationTransaction("AsyncTask", "AndroidApp");

            // Test the HTTP response code
            int rc = httpConnection.getResponseCode();
            if (rc == HttpURLConnection.HTTP_OK) {
                InputStream responseData = new BufferedInputStream(httpConnection.getInputStream());
                resultString = getResponseText(responseData);
            }
            else resultString = ("Error is returned.  Err code is : " + String.valueOf(rc));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // resultJSON should hold our resulting return JSONObject
        return resultString;
    }

    protected void onPostExecute(String result) {
        // This is invoked when the background thread has completed
        // Get a reference to the content TextView
        // so we can update the text and its colour
        targetTextView.setText(result);
        //CaMDOIntegration.stopApplicationTransaction("AsyncTask", "AndroidApp");

    }

    // Read the InputStream and get the JSON out as a string
    private String getResponseText(InputStream inStream) {
        // very nice trick from
        // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
        return new Scanner(inStream).useDelimiter("\\A").next();

    }
}

